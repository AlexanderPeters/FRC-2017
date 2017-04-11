package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import main.Constants;
import main.OI;
import main.Robot;

public class TurnToAngle extends Command implements Constants {
	private boolean done;
	public TurnToAngle() {
		Robot.dt.TurnToAngle();
	}
	public TurnToAngle(double heading) {
		Robot.dt.TurnToAngle(heading);
	}
	public TurnToAngle(double heading, double tolerance) {
		Robot.dt.TurnToAngle(heading, tolerance);
	}
	public TurnToAngle(double heading, double tolerance, double maxV) {
		Robot.dt.TurnToAngle(heading, tolerance, maxV);
	}
	public TurnToAngle(double heading, double tolerance, double maxV, double KP, double KI, double KD) {
		Robot.dt.TurnToAngle(heading, tolerance, maxV, KP, KI, KD);
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	done = false;
    	Robot.dt.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.sdb.isBigAngle()) 
    		done = Robot.dt.turnToSmallAngle();
    	else if(!Robot.sdb.isBigAngle()) 
    		done = Robot.dt.turnToBigAngle();
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
