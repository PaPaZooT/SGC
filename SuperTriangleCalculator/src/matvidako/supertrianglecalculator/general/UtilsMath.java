package matvidako.supertrianglecalculator.general;

public class UtilsMath {
	static double epsilon = 0.001;
	
	public static boolean equalDouble(double a, double b){
		return Math.abs(a-b) < epsilon;
	}
	
}
