package main.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import main.Constants;
import main.HardwareAdapter;
import main.commands.gearmech.IntakeOff;

public class GearMech extends Subsystem implements Constants, HardwareAdapter {
	public GearMech() {
		
	}
	public void spin(double speed){
		gearIntakeMotor.set(speed);
	}
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new IntakeOff());
	}

}
