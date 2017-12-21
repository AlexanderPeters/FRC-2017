package main.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import main.Constants;
import main.commands.drivetrain.TimedDrive;

public class baseline extends CommandGroup implements Constants {
	public baseline() {
		addSequential(new TimedDrive(-0.5, 5.0));
	}
}
