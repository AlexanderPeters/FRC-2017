package main.commands.gearmech;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ExhaustComboDown extends CommandGroup {
	public ExhaustComboDown() {
		addParallel(new GearDown());
		addSequential(new Exhaust());
	}

}
