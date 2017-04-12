package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import main.Constants;
import main.Robot;

public class TurnToAngle extends Command implements Constants {
	private boolean done;
	private double heading;
	private boolean bigAngle;
	
	public TurnToAngle() {
		this.heading = Robot.sdb.getHeading();
		Robot.dt.TurnToAngle();
	}
	public TurnToAngle(double heading) {
		this.heading = heading;
		Robot.dt.TurnToAngle(heading);
	}
	public TurnToAngle(double heading, double tolerance) {
		this.heading = heading;
		Robot.dt.TurnToAngle(heading, tolerance);
	}
	public TurnToAngle(double heading, double tolerance, double maxV) {
		this.heading = heading;
		Robot.dt.TurnToAngle(heading, tolerance, maxV);
	}
	public TurnToAngle(double heading, double tolerance, double maxV, double KP, double KI, double KD) {
		this.heading = heading;
		Robot.dt.TurnToAngle(heading, tolerance, maxV, KP, KI, KD);
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	done = false;
    	bigAngle = (Math.abs(heading) > Robot.sdb.switchAngle() ? true : false);
    	Robot.dt.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(bigAngle) 
    		done = Robot.dt.turnToBigAngle();
    	else
    		done = Robot.dt.turnToSmallAngle();
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
