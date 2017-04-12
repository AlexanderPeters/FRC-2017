package main.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import main.Constants;
import main.HardwareAdapter;
import main.commands.driveAlerts.AlertDriver;
import main.commands.driveAlerts.AlertLightOff;

public class OtherSensors extends Subsystem implements Constants, HardwareAdapter {
	private boolean gearSwitchLastState;
	private boolean gearSwitchCurrentState;
	private Command flashLights = new AlertDriver();
	//private InternalButton alertDriverButton = new InternalButton(); //Note to Self: Internal buttons crash robot code!
	
	public OtherSensors() {
		gearSwitchLastState = gearSwitch.get();
	}
	
	public void check() {
		gearSwitchCheck();
	}
	
	private void gearSwitchCheck() {
		gearSwitchCurrentState = gearSwitch.get();
		//System.out.println(gearSwitchCurrentState);
		if(gearSwitchCurrentState != gearSwitchLastState) flashLights.start();
		//alertDriverButton.setPressed(gearSwitchCurrentState != gearSwitchLastState);
		//alertDriverButton.whenPressed(new AlertDriver());
		gearSwitchLastState = gearSwitchCurrentState;
	}
	
	
	@Override
	protected void initDefaultCommand() {
	}

}
