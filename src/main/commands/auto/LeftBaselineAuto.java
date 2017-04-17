package main.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import main.commands.drivetrain.TimedDrive;
import main.commands.gearmech.GearUp;

public class LeftBaselineAuto extends CommandGroup implements Constants {
	public LeftBaselineAuto() {
		addSequential(new GearUp());
		addSequential(new TimedDrive(-0.75, 1.285));
	}

}
