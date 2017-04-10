package main.subsystems;

import com.ctre.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import main.Constants;
import main.HardwareAdapter;

public class FlyWheel extends Subsystem implements Constants, HardwareAdapter{
	public void speed() {
		flyWheel.changeControlMode(VELOCITY);
		flyWheel.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		flyWheel.setAllowableClosedLoopErr(flyWheelAllowableError);
		flyWheel.configEncoderCodesPerRev(codesPerRev);//Subject to change
		flyWheel.reverseSensor(false);
		flyWheel.configNominalOutputVoltage(+0.0f, -0.0f);
		flyWheel.configPeakOutputVoltage(+12.0f, -12.0f);
		flyWheel.setPID(flyWheelKP, flyWheelKI, flyWheelKD);
		flyWheel.set(flyWheelTargetVel);
		System.out.println("throttle " +  flyWheel.getOutputVoltage() + " encDirection" + flyWheel.getSpeed());
	}
	
	public double getSpeed() {
		return flyWheel.getSpeed();
	}
	
	public double getEncSpeed() {
		return flyWheel.getEncVelocity();
	}
	
	public double getError() {
		return flyWheel.getError();
	}

	public void speed(Double speed) {
		flyWheel.changeControlMode(PERCENT_VBUS_MODE);
		flyWheel.set(speed);
	}

	@Override
	protected void initDefaultCommand() {
		//ToDo
	}
}
