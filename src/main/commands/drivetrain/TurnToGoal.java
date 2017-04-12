package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import main.Constants;
import main.Robot;

public class TurnToGoal extends Command implements Constants {
	private double heading;
	private boolean bigAngle;
	private boolean done;
	protected void initialize() {
		done = false;
		heading = Robot.comms.getBearing();
		bigAngle = (Math.abs(heading) > Robot.sdb.switchAngle() ? true : false);
		Robot.dt.TurnToAngle(heading); 
    	Robot.dt.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(heading >= -180 && heading <= 180) {
    		System.out.println("Turning To" + heading);
    		if(bigAngle)
    			done = Robot.dt.turnToBigAngle();
    		else
    			done = Robot.dt.turnToSmallAngle();
    	}
    	else {
    		System.out.println("Turn To Target Called With ILLEGAL ANGLE !!!!");
    		done = true;
    	}
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
