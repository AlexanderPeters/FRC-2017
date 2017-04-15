package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import main.Constants;
import main.Robot;

public class TurnToGoal extends Command implements Constants {
	private double heading;
	private int count = 0;
	private boolean done = false;

	TurnToGoal() {
		Robot.dt.resetGyro();
	}
	
	protected void initialize() {
		heading = Robot.comms.getBearing();
		if(heading >= -180 && heading <= 180) Robot.dt.TurnToAngle(heading); 
		else {
    		System.out.println("Turn To Target Called With ILLEGAL ANGLE !!!!");
    		done = true;
		}
			
    	Robot.dt.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(count == 0) {
    		Robot.dt.resetGyro();
    		Robot.dt.resetCounter();
    	}
    	Robot.dt.setPIDCanRun(true);
    	done = Robot.dt.turnToAngle();
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
    	Robot.dt.resetGyro();
    	count = 0;
    	done = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}
