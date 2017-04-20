package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Constants;
import main.Robot;

public class TimedTurnToGoal extends TimedCommand implements Constants {
	private double heading = -10000;
	private int count = 0;
	private boolean done = false;

	TimedTurnToGoal(double time) {
		super(time);
		Robot.dt.resetGyro();
	}
	
	protected void initialize() {
		double headingTemp = Robot.comms.getBearing();
		if(headingTemp >= -180 && headingTemp <= 180 && Robot.comms.getTargetFound()) 
			heading = headingTemp;
		else {
    		System.out.println("Turn To Target Called With ILLEGAL ANGLE !!!! or target not found");
    		done = true;
		}
			
    	Robot.dt.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(count == 0) {
    		Robot.dt.resetGyro();
    		//Robot.dt.resetCounter();
    	}
    	//Robot.dt.setPIDCanRun(true);
    	if(heading != -10000)
    		done = Robot.dt.turnToAngleBangBang(heading, kToleranceDegreesDefault); //Robot.dt.turnToAngle();
    	count++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.dt.setPIDCanRun(false);
    	//Robot.dt.resetPIDControllers();
    	Robot.dt.resetGyro();
    	count = 0;
    	heading = -10000;
    	done = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

}