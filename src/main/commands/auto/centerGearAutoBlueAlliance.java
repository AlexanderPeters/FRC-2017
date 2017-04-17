package main.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import main.Constants;
import main.commands.drivetrain.TimedDrive;
import main.commands.gearmech.GearDown;
import main.commands.gearmech.GearUp;
import main.commands.shooter.FlyWheelForTime;
import main.commands.stirrer.StirForTime;

public class centerGearAutoBlueAlliance extends CommandGroup implements Constants {
	public centerGearAutoBlueAlliance() {
		addSequential(new GearUp());
		addSequential(new TimedDrive(-0.75, 1.285));
		addSequential(new WaitCommand(0.35));//0.35
		addSequential(new GearDown());
		addSequential(new WaitCommand(0.5));//0.5
		addParallel(new TimedDrive(0.75, 0.78));
		addSequential(new FlyWheelForTime(1.0, 6.5));
		addSequential(new GearUp());
		addSequential(new TimedDrive(0.0, -kMinVoltageTurnBigAngle, 0.74));
		//addSequential(new TurnToAngle(102));
		/*addSequential(new TimedDrive(0.6, 1));
		addSequential(new TimedDrive(-0.5, kMinVoltageTurnBigAngle, 0.9));
		addSequential(new TimedDrive(-0.5, 2.6));*/
		//addSequential(new WaitCommand(0.35));
		addSequential(new WaitCommand(0.26));
		addSequential(new StirForTime(stirrerMotorForward, 5.5));
		
	}

}
