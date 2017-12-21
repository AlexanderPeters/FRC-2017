package main.commands.gearmech;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ExhaustComboUp extends CommandGroup {
	public ExhaustComboUp() {
		addParallel(new GearUp());
		addSequential(new IntakeOff());
	}

}
