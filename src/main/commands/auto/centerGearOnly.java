package main.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import main.Constants;
import main.commands.drivetrain.TimedDrive;
import main.commands.gearmech.GearDown;
import main.commands.gearmech.GearUp;

public class centerGearOnly extends CommandGroup implements Constants {
	public centerGearOnly() {
		addSequential(new GearUp());
		addSequential(new TimedDrive(-0.75, 1.285));
		addSequential(new WaitCommand(0.35));//0.35
		addSequential(new GearDown());
		addSequential(new WaitCommand(0.5));//0.5
		addSequential(new TimedDrive(0.75, 0.78));
	}

}
