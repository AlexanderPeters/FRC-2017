package main;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DigitalOutput;
import lib.joystick.AdvJoystick;
import lib.joystick.XboxController;

public interface HardwareAdapter extends Constants {
	//OI
	public static XboxController xbox = new XboxController(Xbox_Port);
	
	//DriveTrain
	//Verified for Competition Bot 1 2/13/17
	public static CANTalon leftDriveMaster = new CANTalon(LEFT_Drive_Master);
	public static CANTalon leftDriveSlave1 = new CANTalon(LEFT_Drive_Slave1);
	//public static CANTalon leftDriveSlave2 = new CANTalon(LEFT_Drive_Slave2);
	public static CANTalon rightDriveMaster = new CANTalon(RIGHT_Drive_Master);
	public static CANTalon rightDriveSlave1 = new CANTalon(RIGHT_Drive_Slave1);
	//public static CANTalon rightDriveSlave2 = new CANTalon(RIGHT_Drive_Slave2);
	
	//Shooter
	
	//Climber
	public static Spark climberMotor = new Spark(Climber_Motor);
	
	
	public static Spark gearIntakeMotor = new Spark(Gear_Intake_Motor);
	
	//Other Pnuematics
	public static DoubleSolenoid shifter = new DoubleSolenoid(PCM_Port, SHIFTER_EXT, SHIFTER_RET);
	public static Compressor comp = new Compressor(PCM_Port);
	public static DoubleSolenoid gearMech = new DoubleSolenoid(PCM_Port, GEAR_EXT, GEAR_RET);

	
	
	

}
