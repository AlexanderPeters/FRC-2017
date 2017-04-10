package main.commands.driveAlerts;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class AlertLightOnForTime extends TimedCommand {
	public AlertLightOnForTime(double time) {
		super(time);
		requires(Robot.da);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.da.setAlertLightState(true);
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.da.setAlertLightState(false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
