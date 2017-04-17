package main.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import main.Constants;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurnToAngleBangBang;
import main.commands.drivetrain.TurnToAngleBangBang;
import main.commands.gearmech.GearUp;

public class rightBaseline extends CommandGroup implements Constants {
	public rightBaseline() {
		addSequential(new GearUp());
		addSequential(new TimedDrive(-0.75, 3.5));
		addSequential(new TimedTurnToAngleBangBang(-45, kToleranceDegreesDefault, 1.1));
		addSequential(new TimedDrive(-0.75, 4.0));
		addSequential(new TimedTurnToAngleBangBang(47, kToleranceDegreesDefault, 1.1));

	}
}
