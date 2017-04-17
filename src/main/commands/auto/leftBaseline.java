package main.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import main.Constants;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TurnToAngleBangBang;
import main.commands.gearmech.GearUp;

public class leftBaseline extends CommandGroup implements Constants {
	public leftBaseline() {
		addSequential(new GearUp());
		addSequential(new TimedDrive(-0.75, 5.0));
	}
}
