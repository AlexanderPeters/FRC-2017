package main.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import main.Constants;
import main.HardwareAdapter;

public class Pneumatics extends Subsystem implements Constants, HardwareAdapter {
	private boolean down = false;

	public Pneumatics() {
		comp.setClosedLoopControl(true);
		shifter.set(EXT);
		shifter.set(OFF);
		gearMech.set(EXT);
		gearMech.set(OFF);
	}
	
	public void shift(DoubleSolenoid.Value v) {
		shifter.set(v);
	}
	
	public void shiftGearMech(DoubleSolenoid.Value v) {
		down = !down;
		gearMech.set(v);
	}
	
	public boolean getDown() {
		return down;
	}
	
	public void shiftGearMech() {
		if(down)
			gearMech.set(RET);
		else
			gearMech.set(EXT);
		gearMech.set(OFF);
	}
	
	public void toggleComp() {
		if (comp.enabled())
			comp.stop();
		else
			comp.start();
	}
	
	public void turnCompOff() {
		if (comp.enabled())
			comp.stop();
	}

	public void initDefaultCommand() {
		setDefaultCommand(null);
	}
}
