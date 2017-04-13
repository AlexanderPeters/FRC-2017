package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

/**
 *
 */
public class TimedDrive extends TimedCommand {
	
	private double throttle, heading;
	
	public TimedDrive(double throttle, double heading, double time) {
    	super(time);
    	this.throttle = throttle;
    	this.heading = heading;
    	requires(Robot.dt);
    }
	
    public TimedDrive(double throttle, double time) {
    	super(time);
    	this.throttle = throttle;
    	this.heading = 0.0;
    	requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }           

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.dt.getPIDCanRun())
    		if(heading == 0.0)
    			Robot.dt.driveStraight(throttle);
    		else
    			Robot.dt.driveVelocity(throttle, heading);
    	//Robot.dt.driveStraight(speed);//OI.getXbox().getSmoothedAltX());
    	//System.out.println(OI.getXbox().getMainX());
    }
    // Make this return true when this Command no longer needs to run execute()

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}