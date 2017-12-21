package main.commands.gearmech;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeComboDown extends CommandGroup {
	public IntakeComboDown() {
		addParallel(new GearDown());
		addSequential(new Intake());
	}
}
