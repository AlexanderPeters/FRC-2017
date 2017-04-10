package main.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import main.Constants;
import main.HardwareAdapter;
import main.commands.driveAlerts.AlertDriver;
import main.commands.driveAlerts.AlertLightOff;

public class OtherSensors extends Subsystem implements Constants, HardwareAdapter {
	private boolean gearSwitchLastState;
	private boolean gearSwitchCurrentState;
	private InternalButton alertDriverButton = new InternalButton();
	
	public OtherSensors() {
		gearSwitchLastState = gearSwitch.get();
	}
	
	public void check() {
		gearSwitchCheck();
	}
	
	private void gearSwitchCheck() {
		gearSwitchCurrentState = gearSwitch.get();
		//System.out.println(gearSwitchCurrentState);
		alertDriverButton.setPressed(gearSwitchCurrentState != gearSwitchLastState);
		alertDriverButton.whenPressed(new AlertDriver());
		gearSwitchLastState = gearSwitchCurrentState;
	}
	
	
	@Override
	protected void initDefaultCommand() {
	}

}
