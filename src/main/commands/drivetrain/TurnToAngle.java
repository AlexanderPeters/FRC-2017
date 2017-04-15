package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import main.Constants;
import main.Robot;

public class TurnToAngle extends Command implements Constants {
	private boolean done = false, testing = false;
	private int count = 0;
	private double heading = -10000, tolerance = -10000, maxV = -10000, KP = -10000, KI = -10000, KD = -10000;
	private double start;
	
	public TurnToAngle(boolean testing) {
		Robot.dt.resetGyro();
		this.testing = testing;
	}
	public TurnToAngle(double heading) {
		Robot.dt.resetGyro();
		this.heading = heading;
	}
	public TurnToAngle(double heading, double tolerance) {
		Robot.dt.resetGyro();
		this.heading = heading;
		this.tolerance = tolerance;
	}
	public TurnToAngle(double heading, double tolerance, double maxV) {
		Robot.dt.resetGyro();
		this.heading = heading;
		this.tolerance = tolerance;
		this.maxV = maxV;
	}
	public TurnToAngle(double heading, double tolerance, double maxV, double KP, double KI, double KD) {
		Robot.dt.resetGyro();
		this.heading = heading;
		this.tolerance = tolerance;
		this.maxV = maxV;
		this.KP = KP;
		this.KI = KI;
		this.KD = KD;
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(count == 0) {
    		Robot.dt.resetGyro();
    		Robot.dt.resetCounter();
    		start = System.currentTimeMillis();
    		if(heading == -10000)
    			Robot.dt.TurnToAngle();
    		else if(tolerance == -10000)
    			Robot.dt.TurnToAngle(heading);
    		else if(maxV == -10000)
    			Robot.dt.TurnToAngle(heading, tolerance);
    		else if(KP == -10000)
    			Robot.dt.TurnToAngle(heading, tolerance, maxV);
    		else
    			Robot.dt.TurnToAngle(heading, tolerance, maxV, KP, KI, KD);
    	}
    	Robot.dt.setPIDCanRun(true);
    	done = Robot.dt.turnToAngle();
    	count++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(testing)
    		return (System.currentTimeMillis() - start)/1000 > 10;
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
