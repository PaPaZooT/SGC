package matvidako.supertrianglecalculator;

public class Utility {
	static double epsilon = 0.001;
	
	public static boolean equalDouble(double a, double b){
		return Math.abs(a-b) < epsilon;
	}
	
}
