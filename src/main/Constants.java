package main;

import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


//This is an interface that allows all constants stored here to be visible to other classes
public interface Constants {
	/*************
	 * VARIABLES *
	 *************/
	// ROBOT VARIABLES
	
	public final double gmIntakeMotorForward = -1.0;
	public final double gmIntakeMotorReverse = 1.0;
	public final double gmIntakeMotorOff = 0.0;
	public final double climberMotorForwardFast = 1;
	public final double climberMotorForwardSlow = 0.8;
	
	public final double driveThrottle = 1.0;
	public final double turnThrottle = 1.0;

	
	// JOYSTICK DEADBAND'S
	public final double throttleDeadband = 0.02;
	public final double headingDeadband = 0.02;
	
	/*************
	 * CONSTANTS *
	 *************/
	
	// DEFAULT TALON MODES
	public final TalonControlMode DEFAULT_CTRL_MODE = TalonControlMode.PercentVbus;
	public final boolean DEFAULT_BRAKE_MODE = true;
	// TALON CONTROL MODES
	public final TalonControlMode VELOCITY = TalonControlMode.Speed;
	public final TalonControlMode PERCENT_VBUS_MODE = TalonControlMode.PercentVbus;
	public final TalonControlMode POSITION = TalonControlMode.Position;
	public final TalonControlMode VOLTAGE_MODE = TalonControlMode.Voltage;
	public final TalonControlMode SLAVE_MODE = TalonControlMode.Follower;
	public final TalonControlMode DISABLED = TalonControlMode.Disabled;
	// TALON BRAKE MODES
	public final boolean BRAKE_MODE = true;
	public final boolean COAST_MODE = false;
	// PNEUMATIC STATES
	public final DoubleSolenoid.Value EXT = Value.kForward;
	public final DoubleSolenoid.Value RET = Value.kReverse;
	public final DoubleSolenoid.Value OFF = Value.kOff;
		
	/****************
	 * DEVICE PORTS *
	 ****************/
	// JOYSTICKS (USB)
	public final int Xbox_Port = 0;
	
	// TALON SRX'S (CAN BUS)
	public final int LEFT_Drive_Master = 2;
	public final int LEFT_Drive_Slave1 = 3;
	//public final int LEFT_Drive_Slave2 = 4;
	public final int RIGHT_Drive_Master = 5;
	public final int RIGHT_Drive_Slave1 = 6;
	//public final int RIGHT_Drive_Slave2 = 7;
	// OTHER MOTOR CONTROLLERS (PWM)
	public final int Gear_Intake_Motor = 9;
	public final int Climber_Motor = 3;
	// PNEUMATICS (PCM)
	public final int PCM_Port = 1;

	public final int GEAR_EXT = 2;//Currently in by default
	public final int GEAR_RET = 5;
	public final int SHIFTER_EXT = 6;//(isCompRobot? 6:3);
	public final int SHIFTER_RET = 3;//(isCompRobot? 3:6);
	
		
}
