package main.subsystems;

import com.ctre.CANTalon.TalonControlMode;
import Util.DriveHelper;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import main.Constants;
import main.HardwareAdapter;
import main.commands.drivetrain.Drive;

public class DriveTrain extends Subsystem implements Constants, HardwareAdapter {
	private static RobotDrive driveTrain = new RobotDrive(leftDriveMaster, rightDriveMaster);
	private DriveHelper helper = new DriveHelper(7.5);
	
	public DriveTrain() {
		setTalonDefaults();
	}	

	public void driveVelocity(double throttle, double heading) {
			setBrakeMode(true);
			driveTrain.arcadeDrive(helper.handleOverPower(throttle), helper.handleOverPower(heading));
	}

	private void reverseTalons(boolean output) {//Actually Works ?
		leftDriveMaster.reverseOutput(output);
		rightDriveMaster.reverseOutput(output);
	}

	private void setBrakeMode(Boolean brake) {
		leftDriveMaster.enableBrakeMode(brake);
		leftDriveSlave1.enableBrakeMode(brake);
		rightDriveMaster.enableBrakeMode(brake);
		rightDriveSlave1.enableBrakeMode(brake);
	}

	private void setCtrlMode(TalonControlMode mode) {
		leftDriveMaster.changeControlMode(mode);
		leftDriveSlave1.changeControlMode(SLAVE_MODE);
		leftDriveSlave1.set(leftDriveMaster.getDeviceID());
	
		rightDriveMaster.changeControlMode(mode);
		rightDriveSlave1.changeControlMode(SLAVE_MODE);
		rightDriveSlave1.set(rightDriveMaster.getDeviceID());	
	}

	private void setVoltageDefaults() {
		leftDriveMaster.configNominalOutputVoltage(+0f, -0f);
		rightDriveMaster.configNominalOutputVoltage(+0f, -0f);
		leftDriveMaster.configPeakOutputVoltage(+12f, -12f);
		rightDriveMaster.configPeakOutputVoltage(+12f, -12f);
	}

	private void setTalonDefaults() {
		setVoltageDefaults();
		reverseTalons(true);//Changing this didn't do anything, mathematically negated in drive command
		setBrakeMode(true);
		setCtrlMode(DEFAULT_CTRL_MODE);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Drive());
		
	}
		
}
