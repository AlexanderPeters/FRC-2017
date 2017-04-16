package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

/**
 *
 */
public class TimedDrive extends TimedCommand {
	private int count = 0;
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
    	Robot.dt.resetGyro();
    }           

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*if(count == 0)
    		Robot.dt.resetGyro();
    	if(!Robot.dt.getPIDCanRun())
    		if(heading == 0.0)
    			Robot.dt.driveVelocity(throttle, 0.0);//Need to make gyro corrections actaully work
    		else*/
    	Robot.dt.driveVelocity(throttle, heading);
    	//count++;
    	//Robot.dt.driveStraight(speed);//OI.getXbox().getSmoothedAltX());
    	//System.out.println(OI.getXbox().getMainX());
    }
    // Make this return true when this Command no longer needs to run execute()

    // Called once after isFinished returns true
    protected void end() {
    	/*Robot.dt.resetPIDControllers();
    	Robot.dt.resetSensors();
    	count = 0;*/
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}