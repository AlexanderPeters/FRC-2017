package Util;

import java.lang.reflect.Field;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardInteractions {
	private boolean lastBigAngle;
	private double lastHeading, lastTtolerance, lastTKP, lastTKI, lastTKD, lastTSmallMaxV, lastTSmallMinV, lastTBigMaxV, lastTBigMinV,
					lastDistance, lastDTolerance, lastDKP, lastDKI, lastDKD, lastDmaxV;
	private int lastSwitchAngle;
	
	private boolean bigAngle;
	private double heading, Ttolerance;
	private int switchAngle;
	private double TKP, TKI, TKD, TSmallMaxV, TSmallMinV, TBigMaxV, TBigMinV;
	private double distance;
	private double Dtolerance;
	private double DKP, DKI, DKD, DmaxV;

	public SmartDashboardInteractions() {
		update();
		lastBigAngle = bigAngle;
		lastHeading = heading;
		lastTtolerance = Ttolerance;
		lastTKP = TKP;
		lastTKI = TKI;
		lastTKD = TKD;
		lastTSmallMaxV = TSmallMaxV;
		lastTSmallMinV = TSmallMinV;
		lastTBigMaxV = TBigMaxV;
		lastTBigMinV = TBigMinV;
		lastDistance = distance;
		lastDTolerance = Dtolerance;
		lastDKP = DKP;
		lastDKI = DKI;
		lastDKD = DKD;
		lastDmaxV = DmaxV;
		lastSwitchAngle = switchAngle;
	}

	@SuppressWarnings("deprecation")
	public void update() {
		this.heading = SmartDashboard.getDouble("Angle Target", 0.0);
		this.switchAngle = SmartDashboard.getInt("Turn In Place Controller Switch Angle", 45);
		bigAngle = (Math.abs(heading) > switchAngle ? true : false);

		this.TKP = SmartDashboard.getDouble((bigAngle ? "Turning KP Big Angle" : "Turning KP Small Angle"), 0.0);
		this.TKI = SmartDashboard.getDouble((bigAngle ? "Turning KI Big Angle" : "Turning KI Small Angle"), 0.0);
		this.TKD = SmartDashboard.getDouble((bigAngle ? "Turning KD Big Angle" : "Turning KD Small Angle"), 0.0);
		this.TSmallMaxV = SmartDashboard.getDouble("Turning MaxVoltage Small Angle", 0.0);
		this.TSmallMinV = SmartDashboard.getDouble("Turning MinVoltage Small Angle", 0.0);
		this.TBigMaxV = SmartDashboard.getDouble("Turning MaxVoltage Big Angle", 0.0);
		this.TBigMinV = SmartDashboard.getDouble("Turning MinVoltage Big Angle", 0.0);
		this.Ttolerance = SmartDashboard.getDouble("Turning Tolerance", 0.0);
		this.distance = -SmartDashboard.getDouble("Distance To Drive To", 0.0);
		this.DKP = SmartDashboard.getDouble("Distance KP", 0.0);
		this.DKI = SmartDashboard.getDouble("Distance KI", 0.0);
		this.DKD = SmartDashboard.getDouble("Distance KD", 0.0);
		this.Dtolerance = SmartDashboard.getDouble("Distance Tolerance", 0.0);
		this.DmaxV = SmartDashboard.getDouble("Distance MaxVoltage", 0.0);

	}

	public double getHeading() {
		return heading;
	}

	public double getTurningTolerance() {
		return Ttolerance;
	}

	public int switchAngle() {
		return switchAngle;
	}

	public boolean isBigAngle() {
		return bigAngle;
	}

	public double getTurningKP() {
		return TKP;

	}

	public double getTurningKI() {
		return TKI;
	}

	public double getTurningKD() {
		return TKD;
	}

	public double getTurningSmallMaxV() {
		return TSmallMaxV;
	}
	public double getTurningSmallMinV() {
		return TSmallMinV;
	}
	
	public double getTurningBigMaxV() {
		return TBigMaxV;
	}
	
	public double getTurningBigMinV() {
		return TBigMinV;
	}

	public double getDistance() {
		return distance;
	}

	public double getDistancetolerance() {
		return Dtolerance;
	}

	public double getDistanceKP() {
		return DKP;
	}

	public double getDistanceKI() {
		return DKI;
	}

	public double getDistanceKD() {
		return DKD;
	}

	public double getDistanceMaxV() {
		return DmaxV;
	}
	
	public boolean haveAnyTurnVarsChanged() {
		boolean returnBool;
		update();
		if(lastBigAngle != bigAngle ||
			lastHeading != heading ||
			lastTtolerance != Ttolerance ||
			lastTKP != TKP ||
			lastTKI != TKI ||
			lastTKD != TKD ||
			lastTSmallMaxV != TSmallMaxV ||
			lastTSmallMinV != TSmallMinV ||
			lastTBigMaxV != TBigMaxV ||
			lastTBigMinV != TBigMinV ||
			lastSwitchAngle != switchAngle
		) returnBool = true;
		
		else returnBool = false;
			
		lastBigAngle = bigAngle;
		lastHeading = heading;
		lastTtolerance = Ttolerance;
		lastTKP = TKP;
		lastTKI = TKI;
		lastTKD = TKD;
		lastTSmallMaxV = TSmallMaxV;
		lastTSmallMinV = TSmallMinV;
		lastTBigMaxV = TBigMaxV;
		lastTBigMinV = TBigMinV;
		lastSwitchAngle = switchAngle;
		
		return returnBool;

	}
	
	public boolean haveAnyDistanceVarsChanged() {
		boolean returnBool;
		update();
		if(lastDistance != distance ||
			lastDTolerance != Dtolerance ||
			lastDKP != DKP ||
			lastDKI != DKI ||
			lastDKD != DKD ||
			lastDmaxV != DmaxV
		) returnBool = true;
		
		else returnBool = false;
		
		lastDistance = distance;
		lastDTolerance = Dtolerance;
		lastDKP = DKP;
		lastDKI = DKI;
		lastDKD = DKD;
		lastDmaxV = DmaxV;
		
		return returnBool;
		
	}
	
	/*public boolean haveAnyVarsChanged() {
	    try {
	    	Class<?> objClass = this.getClass();
	    	
	        for(Field field : objClass.getFields()) {
	            String name = field.getName();
	            Object value = field.get(this);
	            
	            for(Field fieldNew : objClass.getFields()) {
	            	String nameNew = fieldNew.getName();
	            	Object valueNew = fieldNew.get(this);
	            	if(nameNew.equals(name) && valueNew != value)
	            		return true;
	            }
	        }
	        return false;
	    } catch(Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}*/

}
