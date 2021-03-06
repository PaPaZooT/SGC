package matvidako.supertrianglecalculator.shapes;

import java.util.ArrayList;

import matvidako.supertrianglecalculator.R;
import matvidako.supertrianglecalculator.calculator.Explain;
import matvidako.supertrianglecalculator.general.UtilsMath;
import android.content.Context;

//TODO equilateral triangle checks...
public class Triangle extends Shape{

	public static interface Properties{
		public static final String a = "a", b = "b", c = "c";
		public static final String ha = "ha", hb = "hb", hc = "hc";
		public static final String alpha = "α", beta = "β", gamma = "γ";
		public static final String r = "r", R = "R";
		public static final String P = "P", A = "A";
	}
	
	// -1 means not set
	private double[] sides = new double[]{-1, -1, -1};
	private double[] heights = new double[]{-1, -1, -1};
	private double[] angles = new double[]{-1, -1, -1}; // in deg, rad would break isSet checks
	private double A = -1, P = -1;
	private double r = -1, rCircumscribed = -1;
	private final double sumAngles = 180;

	public Triangle(ArrayList<ShapeProperty> properties, boolean inDegrees){
		super(inDegrees);
		setProperties(properties);
		if (!inDegrees) {
			for (int i = 0; i < 3; i++) {
				if (isSet(angles[i]))
					angles[i] = Math.toDegrees(angles[i]);
			}
		}
	}

	@Override
	public void setProperties(ArrayList<ShapeProperty> properties) {
		this.properties.addAll(properties);
		sides[0] = -1;
		sides[1] = -1;
		sides[2] = -1;
		angles[0] = -1;
		angles[1] = -1;
		angles[2] = -1;
		heights[0] = -1;
		heights[1] = -1;
		heights[2] = -1;
		r = -1;
		rCircumscribed = -1;
		P = -1;
		A = -1;
		/*
		sides[0] = getPropertyValue(Properties.a);
		sides[1] = getPropertyValue(Properties.b);
		sides[2] = getPropertyValue(Properties.c);
		angles[0] = getPropertyValue(Properties.alpha);
		angles[1] = getPropertyValue(Properties.beta);
		angles[2] = getPropertyValue(Properties.gamma);
		heights[0] = getPropertyValue(Properties.ha);
		heights[1] = getPropertyValue(Properties.hb);
		heights[2] = getPropertyValue(Properties.hc);
		r = getPropertyValue(Properties.r);
		rCircumscribed = getPropertyValue(Properties.R);
		P = getPropertyValue(Properties.P);
		A = getPropertyValue(Properties.A);*/
		
		for(ShapeProperty p : properties){
			String name= p.getName();
			if(name.equals(Properties.a)){
				sides[0] = p.getValue();
			} else if(name.equals(Properties.b)){
				sides[1] = p.getValue();
			} else if(name.equals(Properties.b)){
				sides[1] = p.getValue();
			} else if(name.equals(Properties.c)){
				sides[2] = p.getValue();
			} else if(name.equals(Properties.ha)){
				heights[0] = p.getValue();
			} else if(name.equals(Properties.hb)){
				heights[1] = p.getValue();
			} else if(name.equals(Properties.hc)){
				heights[2] = p.getValue();
			} else if(name.equals(Properties.alpha)){
				angles[0] = p.getValue();
			} else if(name.equals(Properties.beta)){
				angles[1] = p.getValue();
			} else if(name.equals(Properties.gamma)){
				angles[2] = p.getValue();
			} else if(name.equals(Properties.r)){
				r = p.getValue();
			} else if(name.equals(Properties.R)){
				rCircumscribed = p.getValue();
			} else if(name.equals(Properties.A)){
				A = p.getValue();
			} else if(name.equals(Properties.P)){
				P = p.getValue();
			}
		}
	}
	
	/*
	private double getPropertyValue(String name){
		int index = properties.indexOf(name);
		if(index > -1){
			return properties.get(index).getValue();
		}
		return -1;
	}*/
	
	@Override
	public boolean isValid() {
		return sidesValid() && anglesValid();
	}

	private boolean sidesValid() {
		return (!allSet(sides[0], sides[1], sides[2]) || (sides[0] + sides[1] > sides[2]
				&& sides[0] + sides[2] > sides[1] && sides[1] + sides[2] > sides[0]));
	}

	private boolean anglesValid() {
		return angles[0] + angles[1] < sumAngles &&
				angles[0] + angles[2] < sumAngles &&
				angles[1] + angles[2] < sumAngles &&
				(!allSet(angles[0], angles[1], angles[2]) || UtilsMath
				.equalDouble(angles[0] + angles[1] + angles[2], sumAngles));
	}

	@Override
	public void calculateAll() {
		boolean anyCalc = false;
		do {
			anyCalc = false;
			anyCalc = anyCalc || calculateSides();
			anyCalc = anyCalc || calculateAngles();
			anyCalc = anyCalc || calculateHeights();
			anyCalc = anyCalc || calculatePerimeter();
			anyCalc = anyCalc || calculateArea();
			anyCalc = anyCalc || calculater();
			anyCalc = anyCalc || calculateR();
		} while (anyCalc);
		packProperties();
	}

	private void packProperties(){
		properties.clear();
		properties.add(new ShapeProperty(Properties.a, sides[0]));
		properties.add(new ShapeProperty(Properties.b, sides[1]));
		properties.add(new ShapeProperty(Properties.c, sides[2]));
		
		properties.add(new ShapeProperty(Properties.ha, heights[0]));
		properties.add(new ShapeProperty(Properties.hb, heights[1]));
		properties.add(new ShapeProperty(Properties.hc, heights[2]));
		
		properties.add(new ShapeProperty(Properties.alpha, angles[0]));
		properties.add(new ShapeProperty(Properties.beta, angles[1]));
		properties.add(new ShapeProperty(Properties.gamma, angles[2]));
		
		properties.add(new ShapeProperty(Properties.A, A));
		properties.add(new ShapeProperty(Properties.P, P));
		properties.add(new ShapeProperty(Properties.r, r));
		properties.add(new ShapeProperty(Properties.R, rCircumscribed));
	}
	
	private boolean calculateSides() {
		boolean anyCalc = false;
		for (int i = 0; i < 3; i++) {
			if (!isSet(sides[i])) {
				sides[i] = sideFromAreaAndHeight(i);
				if (isSet(sides[i])) {
					Explain.log("side" + i, context.getString(R.string.area), "height" + i);
					anyCalc = true;
					continue;
				}
				sides[i] = sideFromPerimeter(i);
				if (isSet(sides[i])) {
					Explain.log("side" + i, context.getString(R.string.perimeter));
					anyCalc = true;
					continue;
				}
				sides[i] = sideFromSineLaw(i);
				if (isSet(sides[i])) {
					Explain.log("side" + i, context.getString(R.string.sine_law));
					anyCalc = true;
					continue;
				}
				sides[i] = sideFromCosineLaw(i);
				if (isSet(sides[i])) {
					Explain.log("side" + i, context.getString(R.string.cosine_law));
					anyCalc = true;
					continue;
				}
				sides[i] = sideFromRAndAngle(i);
				if (isSet(sides[i])) {
					Explain.log("side" + i, context.getString(R.string.Rlong), "angle" + i);
					anyCalc = true;
					continue;
				}
			}
		}
		return anyCalc;
	}

	private double sideFromAreaAndHeight(int i) {
		if (allSet(A, heights[i])) {
			return 2 * A / heights[i];
		}
		return -1;
	}

	private double sideFromPerimeter(int i) {
		if (!isSet(P))
			return -1;
		double a = -1, b = -1;
		if (i == 0) {
			a = sides[1];
			b = sides[2];
		} else if (i == 1) {
			a = sides[0];
			b = sides[2];
		} else if (i == 2) {
			a = sides[0];
			b = sides[1];
		}
		if (!allSet(a, b))
			return -1;
		return P - a - b;
	}

	private double sideFromCosineLaw(int i) {
		double a = -1, b = -1;
		double k = angles[i];
		if (i == 0) {
			a = sides[1];
			b = sides[2];
		} else if (i == 1) {
			a = sides[0];
			b = sides[2];
		} else if (i == 2) {
			a = sides[0];
			b = sides[1];
		}
		if (!allSet(a, b, k))
			return -1;
		return Math.sqrt(a * a + b * b - 2 * a * b
				* Math.cos(Math.toRadians(k)));
	}

	private double sideFromSineLaw(int i) {
		if (!isSet(angles[i]))
			return -1;
		for (int j = 0; j < 3; j++) {
			if (j == i)
				continue;
			if (!allSet(sides[j], angles[j]))
				continue;
			return sides[j] * Math.sin(Math.toRadians(angles[i]))
					/ Math.sin(Math.toRadians(angles[j]));
		}
		return -1;
	}

	private double sideFromRAndAngle(int i) {
		if (!allSet(angles[i], rCircumscribed))
			return -1;
		return 2 * rCircumscribed * Math.sin(Math.toRadians(angles[i]));
	}

	private boolean calculateAngles() {
		boolean anyCalc = false;
		for (int i = 0; i < 3; i++) {
			if (!isSet(angles[i])) {
				angles[i] = angleFromSum(i);
				if (isSet(angles[i])) {
					Explain.log("angle" + i, context.getString(R.string.sum_of_angles));
					anyCalc = true;
					continue;
				}
				angles[i] = angleFromCosineLaw(i);
				if (isSet(angles[i])) {
					Explain.log("angle" + i, context.getString(R.string.cosine_law));
					anyCalc = true;
					continue;
				}
				angles[i] = angleFromSineLaw(i);
				if (isSet(angles[i])) {
					Explain.log("angle" + i, context.getString(R.string.sine_law));
					anyCalc = true;
					continue;
				}
				angles[i] = angleFromRAndSide(i);
				if (isSet(angles[i])) {
					Explain.log("angle" + i, context.getString(R.string.Rlong), "side " + i);
					anyCalc = true;
					continue;
				}
			}
		}
		return anyCalc;
	}

	private double angleFromSum(int i) {
		double a = -1, b = -1;
		if (i == 0) {
			a = angles[1];
			b = angles[2];
		} else if (i == 1) {
			a = angles[0];
			b = angles[2];
		} else if (i == 2) {
			a = angles[0];
			b = angles[1];
		}
		if (!allSet(a, b))
			return -1;
		return sumAngles - a - b;
	}

	private double angleFromCosineLaw(int i) {
		double a = sides[i];
		double b = -1, c = -1;
		if (i == 0) {
			b = sides[1];
			c = sides[2];
		} else if (i == 1) {
			b = sides[0];
			c = sides[2];
		} else if (i == 2) {
			b = sides[0];
			c = sides[1];
		}
		if (!allSet(a, b, c))
			return -1;
		return Math.toDegrees(Math.acos((b * b + c * c - a * a) / (2 * b * c)));
	}

	private double angleFromSineLaw(int i) {
		if (!isSet(sides[i]))
			return -1;
		for (int j = 0; j < 3; j++) {
			if (j == i)
				continue;
			if (!allSet(sides[j], angles[j]))
				continue;
			double sin = Math.sin(Math.toRadians(angles[j]));
			if ((sides[j] > (sin * sides[i])) && sides[j] < sides[i])
				Explain.logAmbiquous();
			return Math.toDegrees(Math.asin((sides[i] * sin) / sides[j]));
		}
		return -1;
	}

	private double angleFromRAndSide(int i) {
		if (!allSet(rCircumscribed, sides[i]))
			return -1;
		return Math.toDegrees(Math.asin(sides[i] / (2.0 * rCircumscribed)));
	}

	private boolean calculateHeights() {
		boolean anyCalc = false;
		for (int i = 0; i < 3; i++) {
			if (!isSet(heights[i])) {
				double res = heightFromAreaAndSide(i);
				if (isSet(res)) {
					Explain.log("height" + i, context.getString(R.string.area), "side" + i);
					heights[i] = res;
					anyCalc = true;
				}
			}
		}
		return anyCalc;
	}

	private double heightFromAreaAndSide(int i) {
		if (allSet(A, sides[i])) {
			return 2 * A / sides[i];
		}
		return -1;
	}

	private boolean calculatePerimeter() {
		if (isSet(P))
			return false;
		boolean anyCalc = false;
		P = perimiterFromSides();
		if (isSet(P)) {
			Explain.log(context.getString(R.string.perimeter), context.getString(R.string.sides));
			return true;
		}
		P = perimiterFromAreaAndr();
		if (isSet(P)) {
			Explain.log(context.getString(R.string.perimeter), context.getString(R.string.area), context.getString(R.string.rlong));
			return true;
		}
		return anyCalc;
	}

	private double perimiterFromSides() {
		if (!allSet(sides[0], sides[1], sides[2]))
			return -1;
		return sides[0] + sides[1] + sides[2];
	}

	private double perimiterFromAreaAndr() {
		if (!allSet(r, A))
			return -1;
		return 2 * A / r;
	}

	private boolean calculateArea() {
		if (isSet(A))
			return false;
		boolean anyCalc = false;
		A = areaHeron();
		if (isSet(A)) {
			Explain.log(context.getString(R.string.area), context.getString(R.string.heron_formula));
			return true;
		}
		A = areaHeight();
		if (isSet(A)) {
			Explain.log(context.getString(R.string.area), context.getString(R.string.side), context.getString(R.string.height));
			return true;
		}
		return anyCalc;
	}

	private double semiPerimiter() {
		if (!allSet(sides[0], sides[1], sides[2]))
			return -1;
		return perimiterFromSides() / 2;
	}

	private double areaHeron() {
		double s = semiPerimiter();
		if (!isSet(s))
			return -1;
		return Math.sqrt(s * (s - sides[0]) * (s - sides[1]) * (s - sides[2]));
	}

	private double areaHeight() {
		for (int i = 0; i < 3; i++) {
			if (allSet(sides[i], heights[i]))
				return sides[i] * heights[i] / 2;
		}
		return -1;
	}

	private boolean calculater() {
		if (isSet(r))
			return false;
		boolean anyCalc = false;
		r = rFromAreaAndSemiperimiter();
		if (isSet(r)) {
			Explain.log(context.getString(R.string.rlong), context.getString(R.string.area), context.getString(R.string.semiperimeter));
			return true;
		}
		return anyCalc;
	}

	private double rFromAreaAndSemiperimiter() {
		double s = semiPerimiter();
		if (!allSet(s, A))
			return -1;
		return A / s;
	}

	private boolean calculateR() {
		if (isSet(rCircumscribed))
			return false;
		boolean anyCalc = false;
		rCircumscribed = RFromAreaAndSides();
		if (isSet(rCircumscribed)) {
			Explain.log(context.getString(R.string.rlong), context.getString(R.string.area), context.getString(R.string.sides));
			return true;
		}
		rCircumscribed = RFromSideAndAngle();
		if (isSet(rCircumscribed)) {
			Explain.log(context.getString(R.string.Rlong), context.getString(R.string.side), context.getString(R.string.angle));
			return true;
		}
		return anyCalc;
	}

	private double RFromAreaAndSides() {
		if (!allSet(sides[0], sides[1], sides[2], A))
			return -1;
		return sides[0] * sides[1] * sides[2] / A / 4;
	}

	private double RFromSideAndAngle() {
		for (int i = 0; i < 3; i++) {
			if (allSet(sides[i], angles[i]))
				return sides[i] / angles[i] / 2;
		}
		return -1;
	}

	private boolean isSet(double n) {
		return n > -0.1;
	}

	private boolean allSet(double... n) {
		for (int i = 0; i < n.length; i++) {
			if (!isSet(n[i]))
				return false;
		}
		return true;
	}

}
