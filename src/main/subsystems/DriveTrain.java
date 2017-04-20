package main.subsystems;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;//NavX import
import Util.DriveHelper;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import main.Constants;
import main.HardwareAdapter;
import main.Robot;
import main.commands.drivetrain.Drive;
import main.commands.pnuematics.ShiftDown;

public class DriveTrain extends Subsystem implements Constants, HardwareAdapter {
	//Most of this should be static but, I'm to lazy to fix it.
	private static boolean highGearState = false;
	private static AHRS NavX;
	private DriveHelper helper = new DriveHelper(7.5);
	private static RobotDrive driveTrain = new RobotDrive(leftDriveMaster, rightDriveMaster);
	private double smallTurnControllerRate, bigTurnControllerRate, distanceControllerRate;
	private PIDController smallTurnController;
	private PIDController bigTurnController;
	private PIDController distanceController;
	private double turningPIDTarget = 0.0;
	private double turningPIDTolerance = 0.0;
	private double distancePIDTarget = 0.0;
	private double distancePIDTolerance = 0.0;
	private double lastAngularTime = 0;
	private double lastAngle = 0;
	private double lastDistanceTime = 0;
	private double lastDistance = 0;
	private double counter = 1;
	private boolean pidCanRun;
	private double smallTurnMinV, bigTurnMinV, distanceMivV;
	
	public DriveTrain() {
		setTalonDefaults();
		pidCanRun = false;
		try {
	          /* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
	          /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
	          /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
	          NavX = new AHRS(SPI.Port.kMXP); 
	      } catch (RuntimeException ex ) {
	          DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
	      }
		resetSensors();//Must happen after NavX is instantiated!
		
		smallTurnController = new PIDController(turnInPlaceKPSmallAngle, turnInPlaceKISmallAngle, turnInPlaceKDSmallAngle, NavX, new PIDOutput() {
			public void pidWrite(double d) {
				smallTurnControllerRate = d;
			}
		});
		
		bigTurnController = new PIDController(turnInPlaceKPBigAngle, turnInPlaceKIBigAngle, turnInPlaceKDBigAngle, NavX, new PIDOutput() {
			public void pidWrite(double d) {
				bigTurnControllerRate = d;
			}
		});
		
		distanceController = new PIDController(displacementKP, displacementKI, displacementKD, new PIDSource() {
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			public double pidGet() {
				return (Robot.dt.getDistanceAvg());
			}

			public void setPIDSourceType(PIDSourceType pidSource) {
				m_sourceType = pidSource;
			}

			public PIDSourceType getPIDSourceType() {
				return m_sourceType;
			}
		}, new PIDOutput() {public void pidWrite(double d) {
			distanceControllerRate = d;
		}
	});
		
		
	}

	public void driveVelocity(double throttle, double heading) {
		if (Robot.gameState == Robot.GameState.Teleop || Robot.gameState == Robot.GameState.Autonomous) {
			setBrakeMode(true);
			driveTrain.arcadeDrive(helper.handleOverPower(throttle), helper.handleOverPower(heading));
			//Added println to make tuning pid's faster
			//System.out.println("Drive Voltage Left, " + leftDriveMaster.getOutputVoltage() + " | Drive Voltage Right, " + rightDriveMaster.getOutputVoltage());
			//System.out.println("Turning Setpoint, " + turningPIDTarget + " | DistanceSetpoint, " + distancePIDTarget);
		}
		updateRobotState();
	}
	
	public void DriveDistance() {
		double distance = Robot.sdb.getDistance();
		DriveDistance(distance);
	}

	public void DriveDistance(double distance) {//feet
		double tolerance = Robot.sdb.getDistancetolerance();
		DriveDistance(distance, tolerance);

	}

	public void DriveDistance(double distance, double tolerance) {// feet, feet
		double maxV = Robot.sdb.getDistanceMaxV();
		DriveDistance(distance, tolerance, maxV);

	}

	public void DriveDistance(double distance, double tolerance, double maxV) {
		double KP = Robot.sdb.getDistanceKP();
		double KI = Robot.sdb.getDistanceKI();
		double KD = Robot.sdb.getDistanceKD();
		DriveDistance(distance, tolerance, maxV, KP, KI, KD);

	}

	public void DriveDistance(double distance, double tolerance, double maxV, double KP, double KI, double KD) {
		driveDistanceSetPID(KP, KI, KD, maxV);
		this.distancePIDTarget = distance;
		this.distancePIDTolerance = tolerance;
		this.distanceMivV = Robot.sdb.getDistanceMinV();
	}

	private void driveDistanceSetPID(double p, double i, double d, double maxV) {
		distanceController.setPID(p, i, d);
		distanceController.setOutputRange(-maxV / 10, maxV / 10);
	}
	
	public boolean driveDistance() {
		double velocity = 1000;
		if (highGearState)
			new ShiftDown();
		setBrakeMode(false);
		setCtrlMode(PERCENT_VBUS_MODE);
		
		// setVoltageDefaultsPID();

		distanceController.setInputRange(-20.0, +20.0);
		distanceController.setAbsoluteTolerance(distancePIDTolerance);
		distanceController.setContinuous(true);
		distanceController.setSetpoint(distancePIDTarget);
		distanceController.enable();
		putGyroErrorToSmartDashboard(NavX.getYaw());
		putEncoderErrorToSmartDashboard(distancePIDTarget - this.getDistanceAvg());
		// System.out.println("r" + distanceControllerRate);
		this.driveVelocity(distanceControllerRate + (distanceMivV*Math.signum(distanceControllerRate))/10, 0.0);//Drive Straight Code messed up (Make this cascading pid)
		if(counter %5 == 0) {
			double deltaTime = (System.currentTimeMillis() - lastDistanceTime)/1000;
			double deltaDistance =  this.getDistanceAvg() - lastDistance;
			velocity = deltaDistance/deltaTime;
			System.out.println(velocity);
			lastDistance = this.getDistanceAvg();
			lastDistanceTime  = System.currentTimeMillis();
		}
		
		updateRobotState();
		counter++;
		return Math.abs(distancePIDTarget - this.getDistanceTraveledRight()) <= distancePIDTolerance && velocity < 0.05; 
	}
	
	public void TurnToAngle() {
		double heading = Robot.sdb.getHeading();
		TurnToAngle(heading);
	}

	public void TurnToAngle(double heading) {
		double tolerance = Robot.sdb.getTurningTolerance();

		TurnToAngle(heading, tolerance);
	}

	public void TurnToAngle(double heading, double tolerance) {
		double maxV;
		if(Math.abs(heading) > Robot.sdb.switchAngle()) maxV = Robot.sdb.getTurningBigMaxV();
		else maxV = Robot.sdb.getTurningSmallMaxV();
		
		TurnToAngle(heading, tolerance, maxV);
	}

	public void TurnToAngle(double heading, double tolerance, double maxV) {
		double KP = Robot.sdb.getTurningKP(Math.abs(heading) > Robot.sdb.switchAngle());
		double KI = Robot.sdb.getTurningKI(Math.abs(heading) > Robot.sdb.switchAngle());
		double KD = Robot.sdb.getTurningKD(Math.abs(heading) > Robot.sdb.switchAngle());

		TurnToAngle(heading, tolerance, maxV, KP, KI, KD);
	}

	public void TurnToAngle(double heading, double tolerance, double maxV, double KP, double KI, double KD) {
		if (Math.abs(heading) > Robot.sdb.switchAngle())
			turnToBigAngleSetPID(KP, KI, KD, maxV);
		else
			turnToSmallAngleSetPID(KP, KI, KD, maxV);
		this.turningPIDTarget = heading;
		this.turningPIDTolerance = tolerance;
		this.smallTurnMinV = Robot.sdb.getTurningSmallMinV();
		this.bigTurnMinV = Robot.sdb.getTurningBigMinV();
	}
	
	public boolean turnToAngle() {
		boolean bigAngle = Math.abs(turningPIDTarget) > Robot.sdb.switchAngle();
		boolean done;
		if (bigAngle)
			done = Robot.dt.turnToBigAngle();
		else
			done = Robot.dt.turnToSmallAngle();
		return done;
	}

	private void turnToBigAngleSetPID(double p, double i, double d, double maxV) {
		bigTurnController.setPID(p, i, d);
		double min = Robot.sdb.getTurningBigMinV();
		bigTurnController.setOutputRange(-(maxV - min) / 10, (maxV - min) / 10);
	}

	private boolean turnToBigAngle() {
		double deltaTime;
		double deltaAngle;
		double angVelocity = 1000;
		if (highGearState) 
			new ShiftDown();
		setBrakeMode(false);
		setCtrlMode(PERCENT_VBUS_MODE);
		if(counter %2 == 1) {
			lastAngle = NavX.getYaw();
			lastAngularTime  = System.currentTimeMillis();
		}				
		bigTurnController.setInputRange(-180.0f,  180.0f);
		bigTurnController.setAbsoluteTolerance(turningPIDTolerance);
		bigTurnController.setContinuous(true);
		bigTurnController.enable();
		bigTurnController.setSetpoint(turningPIDTarget);
		putGyroErrorToSmartDashboard(turningPIDTarget - NavX.getYaw());
		putEncoderErrorToSmartDashboard(0);
		this.driveVelocity(0.0, bigTurnControllerRate + (bigTurnMinV*Math.signum(bigTurnControllerRate))/10);
		if(counter %6 == 0) {
			deltaTime = (System.currentTimeMillis() - lastAngularTime);
			deltaAngle = NavX.getYaw() - lastAngle;
			angVelocity = deltaAngle/deltaTime;
		}
		counter++;
		updateRobotState();
		System.out.println(bigTurnController.getP() + " " + bigTurnController.getI() + " " + bigTurnController.getD());
		return Math.abs(turningPIDTarget - Robot.dt.getGyro().getYaw()) <= turningPIDTolerance && angVelocity < 0.5;
	}
	
	private void turnToSmallAngleSetPID(double p, double i, double d, double maxV) {
		smallTurnController.setPID(p, i, d);
		double min = Robot.sdb.getTurningSmallMinV();
		smallTurnController.setOutputRange(-(maxV - min) / 10, (maxV - min) / 10);	
	}
			
	private boolean turnToSmallAngle() {
		double deltaTime;
		double deltaAngle;
		double angVelocity = 1000;
		if(highGearState)
			new ShiftDown();
		setBrakeMode(false);
		setCtrlMode(PERCENT_VBUS_MODE);
		if(counter %2 == 1) {
			lastAngle = NavX.getYaw();
			lastAngularTime  = System.currentTimeMillis();
		}
		smallTurnController.setInputRange(-Robot.sdb.switchAngle(),  Robot.sdb.switchAngle());
		smallTurnController.setAbsoluteTolerance(turningPIDTolerance);
		smallTurnController.setContinuous(true);
		smallTurnController.enable();
		smallTurnController.setSetpoint(turningPIDTarget);
		putGyroErrorToSmartDashboard(turningPIDTarget - NavX.getYaw());
		putEncoderErrorToSmartDashboard(0);
		this.driveVelocity(0.0, smallTurnControllerRate + (smallTurnMinV*Math.signum(smallTurnControllerRate))/10);
		if(counter %6 == 0) {
			deltaTime = (System.currentTimeMillis() - lastAngularTime);
			deltaAngle = NavX.getYaw() - lastAngle;
			angVelocity = deltaAngle/deltaTime;
		}
		counter++;
		updateRobotState();
		return Math.abs(turningPIDTarget - Robot.dt.getGyro().getYaw()) <= turningPIDTolerance && angVelocity < 0.5;
	}
	
	/*public boolean turnToAngleBangBang(double target, double tolerance) {
		double error = (target - NavX.getYaw())/180;
		double sign = Math.signum(error);
		double calcV = (otherMinVoltage*sign + (-Math.pow(Math.E, (1.85/Math.E + (1-Math.abs(error)))) + 2)*sign);
		this.driveVelocity(0.0, calcV/12);
		return Math.abs(target - NavX.getYaw()) <= tolerance;
	}*/
	
	public boolean turnToAngleBangBang(double target, double tolerance) {
		if(highGearState)
			new ShiftDown();
		setBrakeMode(false);
		double error = target - NavX.getYaw();
		double percentError = (error/target) * 100;
		
		if(percentError > 50)
			this.driveVelocity(0.0, Math.signum(error));
		else if(percentError > 33)
			this.driveVelocity(0.0, Math.signum(error) * 0.5);
		else
			this.driveVelocity(0.0, Math.signum(error) * 0.33);
		return Math.abs(error) <= tolerance;
		
	}
			
	public void driveStraight(double throttle) {
		if(highGearState)
			new ShiftDown();
		setBrakeMode(false);
		setCtrlMode(PERCENT_VBUS_MODE);
					
		smallTurnController.setInputRange(-Robot.sdb.switchAngle(),  Robot.sdb.switchAngle());
		smallTurnController.setAbsoluteTolerance(straightLineTolerance);
		smallTurnController.setContinuous(true);
		smallTurnController.enable();
		smallTurnController.setSetpoint(0.0);
		putGyroErrorToSmartDashboard(NavX.getYaw());
		putEncoderErrorToSmartDashboard(0);
		this.driveVelocity(helper.handleOverPower(throttle), smallTurnControllerRate + (smallTurnMinV*Math.signum(smallTurnControllerRate))/10);
		updateRobotState();
	}
	
	public void resetPIDControllers() {
		resetDistanceController();
		resetSmallAngleController();
		resetBigAngleController();
	}
	
	public void resetDistanceController() {
		distanceController.reset();
	}
	
	public void resetSmallAngleController() {
		smallTurnController.reset();
	}
	
	public void resetBigAngleController() {
		bigTurnController.reset();
	}
	
	public void resetCounter() {
		counter = 1;
	}
		
	public void setPIDCanRun(boolean canRun) {
		pidCanRun = canRun;
	}
	
	public boolean getPIDCanRun() {
		return pidCanRun;
	}
	
	public double getDistanceAvg() {
		return (-getDistanceTraveledLeft() + getDistanceTraveledRight())/2;
	}
	
	public void changeGearing(){
		highGearState = !highGearState;
	}
	
	public AHRS getGyro(){
		return NavX;
	}
	
	public int convertToEncoderTicks(double displacement) {//ft
		return (int) (((displacement / (wheelSize*Math.PI)) * conversionFactor));
	}
	public double getDistanceTraveledLeft() {//Feet
		return wheelSize*Math.PI*(getLeftEncoderPosition()/conversionFactor);
	}
	
	public double getDistanceTraveledRight() {//Feet
		//Removed - value and changed with reverseSensor() so that pid has correct feedback
		//System.out.println("r" +wheelSize*Math.PI*(getRightEncoderPosition()/conversionFactor));
		return wheelSize*Math.PI*(getRightEncoderPosition()/conversionFactor);
	}
	
	public double getLeftVelocity() {
		return leftDriveMaster.getEncVelocity() / wheelEncoderMult;
	}
	
	public double getRightVelocity() {
		return rightDriveMaster.getEncVelocity() / wheelEncoderMult;
	}
	
	public void resetGyro() {
		NavX.reset();
		NavX.zeroYaw();
	}
	public void resetEncoders() {
		leftDriveMaster.setEncPosition(0);//I'm gay
		rightDriveMaster.setEncPosition(0);//I'm gay
		leftDriveMaster.setPosition(0);
		rightDriveMaster.setPosition(0);
	}
	public void resetSensors() {
		resetGyro();
		resetEncoders();
	}
	
	/*******************
	 * SUPPORT METHODS *
	 *******************/
	private void updateRobotState() {
		if(rightDriveMaster.getOutputVoltage() > -0.1 && rightDriveMaster.getOutputVoltage() < 0.1 
				&& leftDriveMaster.getOutputVoltage() > -0.1 && leftDriveMaster.getOutputVoltage() < 0.1 
				&& Robot.robotState != Robot.RobotState.Climbing)	
			Robot.robotState = Robot.RobotState.Neither;
	}
	
	private void putGyroErrorToSmartDashboard(double num) {
		SmartDashboard.putDouble("Gyro", num);
	}
	private void putEncoderErrorToSmartDashboard(double num) {
		SmartDashboard.putDouble("Encoder", num);
	}
	private double getLeftEncoderPosition() {
		return leftDriveMaster.getEncPosition();
	}
	
	private double getRightEncoderPosition() {
		return rightDriveMaster.getEncPosition();
	}
	
	/**
	 * Reverses the output of the Talon SRX's
	 * 
	 * @param output - Whether the output should be reversed.
	 */
	private void reverseTalons(boolean output) {//Actually Works ?
		leftDriveMaster.reverseOutput(output);
		rightDriveMaster.reverseOutput(output);
	}

	/**
	 * Sets the Talon SRX's brake mode
	 * 
	 * @param brake - Sets the brake mode (Uses default brake modes)
	 */
	private void setBrakeMode(Boolean brake) {
		leftDriveMaster.enableBrakeMode(brake);
		leftDriveSlave1.enableBrakeMode(brake);
		//leftDriveSlave2.enableBrakeMode(brake);
		rightDriveMaster.enableBrakeMode(brake);
		rightDriveSlave1.enableBrakeMode(brake);
		//rightDriveSlave2.enableBrakeMode(brake);
	}

	/**
	 * Sets the Talon SRX's control mode
	 * 
	 * @param mode - Sets the control mode (Uses default control modes)
	 */
	private void setCtrlMode(TalonControlMode mode) {
		leftDriveMaster.changeControlMode(mode);
		leftDriveSlave1.changeControlMode(SLAVE_MODE);
		leftDriveSlave1.set(leftDriveMaster.getDeviceID());
		//leftDriveSlave2.changeControlMode(SLAVE_MODE);
		//leftDriveSlave2.set(leftDriveMaster.getDeviceID());
		
		rightDriveMaster.changeControlMode(mode);
		rightDriveSlave1.changeControlMode(SLAVE_MODE);
		rightDriveSlave1.set(rightDriveMaster.getDeviceID());
		//rightDriveSlave2.changeControlMode(SLAVE_MODE);
		//rightDriveSlave2.set(rightDriveMaster.getDeviceID());
		
	}
	
	/**
	 * Set's the Talon SRX's feedback device
	 * 
	 */
	private void setFeedBackDefaults() {
		leftDriveMaster.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightDriveMaster.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		leftDriveMaster.configEncoderCodesPerRev(codesPerRev);
		rightDriveMaster.configEncoderCodesPerRev(codesPerRev);
		leftDriveMaster.reverseSensor(true);//Check this later//was true
		rightDriveMaster.reverseSensor(true);//Check this later//was true
	}
	
	/**
	 * Sets the Talon SRX's voltage defaults (Serves to help keep the drivetrain consistent)
	 */
	private void setVoltageDefaults() {
		leftDriveMaster.configNominalOutputVoltage(+0f, -0f);
		rightDriveMaster.configNominalOutputVoltage(+0f, -0f);
		leftDriveMaster.configPeakOutputVoltage(+12f, -12f);
		rightDriveMaster.configPeakOutputVoltage(+12f, -12f);
	}
	
	/*private void setVoltageDefaultsPID() {
		leftDriveMaster.configNominalOutputVoltage(+0f, -0f);
		rightDriveMaster.configNominalOutputVoltage(+0f, -0f);
		leftDriveMaster.configPeakOutputVoltage(+6f, -6f);
		rightDriveMaster.configPeakOutputVoltage(+6f, -6f);
	}*/
	
	/**
	 * Sets the Talon SRX's voltage ramp rate (Smooth's acceleration (units in volts/sec))
	 */
	private void setRampRate(double ramp) {
		leftDriveMaster.setVoltageCompensationRampRate(ramp);
		rightDriveMaster.setVoltageCompensationRampRate(ramp);
	}

	/**
	 * Sets the Talon SRX's defaults (reversing, brake and control modes)
	 */
	private void setTalonDefaults() {
		setFeedBackDefaults();
		setVoltageDefaults();
		//setRampRate(12);//0-12v in 1 of a second //COMMENTED TO SEE IF THIS PREVENTS PID FROM FUNCTIONING
		reverseTalons(true);//Changing this didn't do anything, mathematically negated in drive command
		setBrakeMode(true);
		setCtrlMode(DEFAULT_CTRL_MODE);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Drive());
		
	}
		
}
