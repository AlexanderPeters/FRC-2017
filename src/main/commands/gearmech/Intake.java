package main.commands.gearmech;

import edu.wpi.first.wpilibj.command.Command;
import main.Constants;
import main.Robot;

public class Intake extends Command implements Constants {
	public Intake() {
        requires(Robot.gm);
    }
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
			Robot.gm.spin(gmIntakeMotorForward);
		}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}