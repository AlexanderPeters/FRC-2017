package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import main.Robot;

public class TestMinV extends TimedCommand {
	private int counter = 0;
	private double voltage;
	
	public TestMinV() {
		super(1.0);
	}
	
	@SuppressWarnings("deprecation")
	protected void execute() {
		if (counter == 0)
			voltage = SmartDashboard.getDouble("Testing MinV for BangBang");
		Robot.dt.driveVelocity(0.0, voltage/12);
		counter++;
	}
	
	protected void end() {
		counter = 0;
	}
	
	protected void interupted() {
		end();
	}

}
