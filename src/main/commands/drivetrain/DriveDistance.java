package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import main.Robot;

public class DriveDistance extends Command {
	private boolean done;
	
	public DriveDistance() {
		Robot.dt.DriveDistance();
	}
	public DriveDistance(double distance) {
		Robot.dt.DriveDistance(distance);
	}
	public DriveDistance(double distance, double tolerance) {
		Robot.dt.DriveDistance(distance, tolerance);
	}
	public DriveDistance(double distance, double tolerance, double maxV) {
		Robot.dt.DriveDistance(distance, tolerance, maxV);
	}
	public DriveDistance(double distance, double tolerance, double maxV, double KP, double KI, double KD) {
		Robot.dt.DriveDistance(distance, tolerance, maxV, KP, KI, KD);
	}
	
	// Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.resetSensors();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.setPIDCanRun(true);
   		done = Robot.dt.driveDistance();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.dt.setPIDCanRun(false);   	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    }
}
