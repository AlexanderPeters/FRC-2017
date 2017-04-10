package controllers;

import lib.Loop;
import main.Constants;
import main.Robot;

public class SensorChecker implements Loop, Constants {
	
	@Override
	public void onStart() {
		//no-op
	}

	@Override
	public void onLoop() {
		Robot.sensors.check();		
	}

	@Override
	public void onStop() {
		//no-op
	}
}
