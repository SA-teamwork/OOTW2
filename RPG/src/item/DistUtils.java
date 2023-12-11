package item;

public class DistUtils {

	/**
	 * calc the distance between des and obj
	 */
	public static double dist(double desx, double desy, double objx, double objy) {
		double distx = desx - objx;
		double disty = desy - objy;
		double dist = Math.pow((Math.pow(distx, 2) + Math.pow(disty, 2)), 0.5);
		return dist;
	}

}
