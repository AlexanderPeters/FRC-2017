package main.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import main.Constants;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurnToAngleBangBang;
import main.commands.drivetrain.TurnToAngle;
import main.commands.drivetrain.TurnToAngleBangBang;
import main.commands.gearmech.GearDown;
import main.commands.gearmech.GearUp;
import main.commands.shooter.FlyWheelForTime;
import main.commands.stirrer.StirForTime;

public class centerGearWithShootRed extends CommandGroup implements Constants {
	public centerGearWithShootRed() {
		addSequential(new GearUp());
		addSequential(new TimedDrive(-0.75, 1.285));
		addSequential(new WaitCommand(0.35));//0.35
		addSequential(new GearDown());
		addSequential(new WaitCommand(0.5));//0.5
		addParallel(new TimedDrive(0.75, 0.78));
		addParallel(new FlyWheelForTime(0.8, 10.0));
		addSequential(new GearUp());
		addSequential(new TurnToAngleBangBang(155, kToleranceDegreesDefault));//155
		//addSequential(new TurnToAngle(102));
		/*addSequential(new TimedDrive(0.6, 1));
		addSequential(new TimedDrive(-0.5, kMinVoltageTurnBigAngle, 0.9));
		addSequential(new TimedDrive(-0.5, 2.6));*/
		//addSequential(new WaitCommand(0.35));
		addSequential(new WaitCommand(0.1));
		addSequential(new StirForTime(stirrerMotorForward, 5.5));
	}

}
