package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import main.Robot;

public class DriveDistance extends Command {
	private boolean done = false, testing = false;
	private int count = 0;
	private double distance = -10000, tolerance = -10000, maxV = -10000, KP = -10000, KI = -10000, KD = -10000;
	private double start;
	
	public DriveDistance(boolean testing) {
		Robot.dt.resetSensors();
		this.testing = testing;
	}
	
	public DriveDistance(double distance) {
    	Robot.dt.resetSensors();
    	this.distance = distance;
	}
	public DriveDistance(double distance, double tolerance) {
    	Robot.dt.resetSensors();
    	this.distance = distance;
    	this.tolerance = tolerance;
	}
	public DriveDistance(double distance, double tolerance, double maxV) {
    	Robot.dt.resetSensors();
    	this.distance = distance;
    	this.tolerance = tolerance;
    	this.maxV = maxV;
    }
	public DriveDistance(double distance, double tolerance, double maxV, double KP, double KI, double KD) {
    	Robot.dt.resetSensors();
    	this.distance = distance;
    	this.tolerance = tolerance;
    	this.maxV = maxV;
    	this.KP = KP;
    	this.KI = KI;
    	this.KD = KD;
    }
	
	// Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.resetSensors();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(count == 0) {
    		start = System.currentTimeMillis();
    		Robot.dt.resetSensors();
    		Robot.dt.resetCounter();
    		if(distance == -10000)
    			Robot.dt.DriveDistance();
    		else if(tolerance == -10000)
    			Robot.dt.DriveDistance(distance);
    		else if(maxV == -10000)
    			Robot.dt.DriveDistance(distance, tolerance);
    		else if(KP == -10000)
    			Robot.dt.DriveDistance(distance, tolerance, maxV);
    		else
    			Robot.dt.DriveDistance(distance, tolerance, maxV, KP, KI, KD);
    	}
    	Robot.dt.setPIDCanRun(true);
   		done = Robot.dt.driveDistance();
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
    	Robot.dt.resetSensors();
    	count = 0;
    	done = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();    	
    }
}
