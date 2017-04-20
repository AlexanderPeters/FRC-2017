package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Constants;
import main.Robot;

public class TimedDriveToGoal extends TimedCommand implements Constants {
	private double distance;
	private int count = 0;
	private boolean done = false;
	
	public TimedDriveToGoal(double time) {
		super(time);
		Robot.dt.resetSensors();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		double range = Robot.comms.getRange();
		if(range >= 0 && Robot.comms.getTargetFound()) {
			distance = ((range*Math.cos(cameraAngle * Math.PI/180)) - desiredDistanceToGoal);
			Robot.dt.DriveDistance(distance);
		}
		else {
			System.out.println("Drive To Target Called With ILLEGAL RANGE !!!! or target not found");
			done = true;
		}
		
		Robot.dt.resetSensors();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(count == 0) {
    		Robot.dt.resetSensors();
    		Robot.dt.resetCounter();
    	}
    	Robot.dt.setPIDCanRun(true);
    	done = Robot.dt.driveDistance();
    	count++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return done;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.dt.setPIDCanRun(false);
    	Robot.dt.resetPIDControllers();
    	Robot.dt.resetSensors();
    	count = 0;
    	done = false;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}
