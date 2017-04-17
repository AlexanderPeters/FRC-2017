package main.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import main.Constants;
import main.commands.drivetrain.DriveDistance;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TurnToAngle;
import main.commands.gearmech.GearDown;
import main.commands.gearmech.GearUp;

public class LeftGearAuto extends CommandGroup implements Constants{
	public LeftGearAuto() {
		addSequential(new GearUp());
		addSequential(new DriveDistance(-5.88));
		addSequential(new WaitCommand(0.25));
		addSequential(new TurnToAngle(57));
		addSequential(new WaitCommand(0.5));
		addSequential(new TimedDrive(-0.75, 1.25));
		addSequential(new TimedDrive(-0.35, 0.5));
		addSequential(new WaitCommand(0.35));
		addSequential(new GearDown());
		addSequential(new WaitCommand(0.5));
		addSequential(new TimedDrive(0.75, 1));
		addSequential(new GearUp());
		addSequential(new TurnToAngle(-57));
		addSequential(new TimedDrive(-0.75, 2.6));
		s
	}

}
