package main;

import lib.joystick.XboxController;
import main.commands.climber.WinchForward;
import main.commands.climber.WinchOff;
import main.commands.gearmech.Exhaust;
import main.commands.gearmech.ExhaustComboDown;
import main.commands.gearmech.ExhaustComboUp;
import main.commands.gearmech.IntakeComboDown;
import main.commands.gearmech.IntakeComboUp;
import main.commands.gearmech.IntakeOff;
import main.commands.pnuematics.ShiftDown;
import main.commands.pnuematics.ShiftUp;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements Constants, HardwareAdapter {
	
	public OI() {
		check();
	}
	
	public static XboxController getXbox (){
		return xbox;
	}

	public void check(){
		xbox.leftBumper.whenPressed(new ShiftDown());
		xbox.leftBumper.whenReleased(new ShiftUp());
		xbox.rightBumper.whileHeld(new IntakeComboDown());
		xbox.rightBumper.whenReleased(new IntakeComboUp());
		xbox.y.whileHeld(new ExhaustComboDown());
		xbox.y.whenReleased(new ExhaustComboUp());
		xbox.x.whileHeld(new WinchForward(true));
		xbox.b.whileHeld(new WinchForward(false));
		xbox.b.whenReleased(new WinchOff());
		xbox.a.whileHeld(new Exhaust());
		//xbox.a.whenReleased();
		
	}
}

