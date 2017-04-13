package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import main.Robot;

/**
 *
 */
public class DriveVelocity extends Command {
	double throttle;
	double bearing;
	
    public DriveVelocity(double throttle, double bearing) {
       requires(Robot.dt);
       this.throttle = throttle;
       this.bearing = bearing;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.resetSensors();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.dt.getPIDCanRun())
    		Robot.dt.driveVelocity(throttle, bearing);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
