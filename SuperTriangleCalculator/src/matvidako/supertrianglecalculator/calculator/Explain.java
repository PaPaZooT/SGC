package matvidako.supertrianglecalculator.calculator;

import java.util.HashMap;

import matvidako.supertrianglecalculator.R;
import android.content.Context;

public class Explain {

	private static StringBuilder explanation = new StringBuilder();
	private static Context context;
	
	private static String calculated;
	private static String using;
	private static String and;
	 
	private static HashMap<String, String> translation = new HashMap<String, String>();
	 
	public static void setup(Context c){
		context = c;
		calculated = context.getString(R.string.calculated) + " ";
		using = context.getString(R.string.using) + " ";
		and = " " + context.getString(R.string.and) + " ";
		fillTranslations();
	}

	public static void clear(){
		explanation = new StringBuilder();
	}
	
	public static void log(String target, String... rest){
		explanation.append(calculated);
		explanation.append(translate(target));
		explanation.append(" ");
		explanation.append(using);
		for(int i = 0; i < rest.length; i++){
			explanation.append(translate(rest[i]));
			if(i < rest.length - 1) 
				explanation.append(and);
		}
		explanation.append("\n");
	}
	
	public static String getExplanation(){
		return explanation.toString();
	}
	
	public static boolean isEmpty(){
		return explanation.toString().equals("");
	}

	public static void logAmbiquous() {
		explanation.append(context.getString(R.string.ambiquous));
		explanation.append("\n");
	}
	
	private static String translate(String s){
		if(translation.containsKey(s)) return translation.get(s);
		return s;
	}
	
	private static void fillTranslations() {
		translation.put("side0", context.getString(R.string.a));
		translation.put("side1", context.getString(R.string.b));
		translation.put("side2", context.getString(R.string.c));
		translation.put("angle0", context.getString(R.string.alpha));
		translation.put("angle1", context.getString(R.string.beta));
		translation.put("angle2", context.getString(R.string.gamma));
		translation.put("height0", context.getString(R.string.ha));
		translation.put("height1", context.getString(R.string.hb));
		translation.put("height2", context.getString(R.string.hc));
	}

}
