package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import main.Constants;
import main.Robot;

public class DriveToGoal extends Command implements Constants {
	private boolean done;
	private double distance;

	// Called just before this Command runs the first time
	protected void initialize() {
		done = false;
		double range = Robot.comms.getRange();
		if(range >= 0) distance = ((range*Math.cos(cameraAngle * Math.PI/180)) - desiredDistanceToGoal);
		else {
			System.out.println("Drive To Target Called With ILLEGAL RANGE !!!!");
			done = true;
		}

		Robot.dt.DriveDistance(distance);
		Robot.dt.resetSensors();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.println("Driving To" + distance);
		done = Robot.dt.driveDistance();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return done;
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}

}
