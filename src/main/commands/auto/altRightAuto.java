package main.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import main.Constants;
import main.commands.drivetrain.DriveDistance;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TurnToAngle;
import main.commands.gearmech.GearDown;
import main.commands.gearmech.GearUp;

public class altRightAuto extends CommandGroup implements Constants {
	public altRightAuto() {
		addSequential(new GearUp());
		addSequential(new DriveDistance(-5.0));
		addSequential(new WaitCommand(0.5));
		addSequential(new TurnToAngle(-60));
		addSequential(new WaitCommand(0.5));
		addSequential(new DriveDistance(-5.8));
		addSequential(new GearDown());
		addSequential(new TimedDrive(0.55, 2));
		addSequential(new GearUp());
	}

}
