package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Constants;
import main.Robot;

public class TimedTurnToAngleBangBang extends TimedCommand implements Constants {
	private boolean done = false;
	private double heading, tolerance;
	
	public TimedTurnToAngleBangBang(double heading, double tolerance, double time) {
		super(time);
		Robot.dt.resetGyro();
		this.heading = heading;
		this.tolerance = tolerance;
	}


    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.setPIDCanRun(true);
    	done = Robot.dt.turnToAngleBangBang(heading, tolerance);
    }

   // Called once after isFinished returns true
    protected void end() {
    	Robot.dt.setPIDCanRun(false);
    	Robot.dt.resetGyro();
    	done = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}