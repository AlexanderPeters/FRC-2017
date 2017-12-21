package main.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import main.Constants;
import main.commands.drivetrain.TimedDrive;
import main.commands.gearmech.ExhaustComboDown;
import main.commands.gearmech.ExhaustComboUp;


public class centerGearOnly extends CommandGroup implements Constants {
	public centerGearOnly() {
		addSequential(new TimedDrive(-0.5, 4.85));
		addSequential(new WaitCommand(0.35));
		addSequential(new ExhaustComboDown());
		addSequential(new WaitCommand(0.35));
		addSequential(new TimedDrive(0.75, 0.78));
		addSequential(new ExhaustComboUp());
	}

}
