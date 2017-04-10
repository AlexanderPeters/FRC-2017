package controllers;

import lib.Loop;
import main.Constants;
import main.Robot;

public class UDPController implements Loop, Constants {
	
	@Override
	public void onStart() {
		//no-op
	}

	@Override
	public void onLoop() {
		Robot.comms.poke();		
	}

	@Override
	public void onStop() {
		//no-op
	}
}
