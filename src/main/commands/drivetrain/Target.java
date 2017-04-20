package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import main.Constants;

public class Target extends CommandGroup implements Constants{
	
	public Target() {
		addSequential(new TimedTurnToGoal(1.5));
		addSequential(new TimedDriveToGoal(1.5));
		addSequential(new TimedTurnToGoal(0.65));
		addSequential(new TimedDriveToGoal(0.65));
	}
}