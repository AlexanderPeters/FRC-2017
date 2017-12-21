package main.commands.gearmech;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeComboUp extends CommandGroup {
	public IntakeComboUp() {
		addParallel(new GearUp());
		addSequential(new IntakeOff());
	}
}
