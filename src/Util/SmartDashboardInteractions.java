package Util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardInteractions {
	private boolean bigAngle;
	private double heading;
	private double Ttolerance;
	private int switchAngle;
	private double TKP, TKI, TKD, TmaxV;
	private double distance;
	private double Dtolerance;
	private double DKP, DKI, DKD, DmaxV;

	public SmartDashboardInteractions() {

	}

	@SuppressWarnings("deprecation")
	public void update() {
		this.heading = SmartDashboard.getDouble("Angle Target", 0.0);
		this.switchAngle = SmartDashboard.getInt("Turn In Place Controller Switch Angle", 45);
		bigAngle = (Math.abs(heading) > switchAngle ? true : false);

		this.TKP = SmartDashboard.getDouble((bigAngle ? "Turning KP Big Angle" : "Turning KP Small Angle"), 0.0);
		this.TKI = SmartDashboard.getDouble((bigAngle ? "Turning KI Big Angle" : "Turning KI Small Angle"), 0.0);
		this.TKD = SmartDashboard.getDouble((bigAngle ? "Turning KD Big Angle" : "Turning KD Small Angle"), 0.0);
		this.TmaxV = SmartDashboard
				.getDouble((bigAngle ? "Turning MaxVoltage Big Angle" : "Turning MaxVoltage Small Angle"), 0.0);
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

	public double getTurningMaxV() {
		return TmaxV;
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

	public double getDistanceMaxC() {
		return DmaxV;
	}

}
