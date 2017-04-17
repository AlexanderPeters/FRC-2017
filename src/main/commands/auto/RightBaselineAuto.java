package main.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import main.commands.drivetrain.TimedDrive;
import main.commands.gearmech.GearUp;

public class RightBaselineAuto extends CommandGroup implements Constants{
	public RightBaseLineAuto() {
		addSequential(new GearUp());
		addSequential(new TimedDrive(-0.75, 1.285));
	}

}
