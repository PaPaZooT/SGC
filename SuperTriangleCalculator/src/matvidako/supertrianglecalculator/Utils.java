package matvidako.supertrianglecalculator;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {

	public static void showKeyboard(View view, Context context){
		InputMethodManager inputMethodManager=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}
	
	public static void hideKeyboard(View view, Context context){
		InputMethodManager inputMethodManager=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
	}
}
