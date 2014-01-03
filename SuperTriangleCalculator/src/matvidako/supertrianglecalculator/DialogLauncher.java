package matvidako.supertrianglecalculator;

import android.app.AlertDialog;
import android.content.Context;

public class DialogLauncher {

	public static void launchHelpDialog(Context context, String title,
			String message) {
		new AlertDialog.Builder(context)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(context.getString(android.R.string.ok), null)
				.show();
	}

	public static void launchExplainDialog(Context context) {
		String msg = context.getString(R.string.noExplanation);
		if (!Explain.isEmpty())
			msg = Explain.getExplanation();
		new AlertDialog.Builder(context)
				.setMessage(msg)
				.setTitle(R.string.explanation)
				.setPositiveButton(context.getString(android.R.string.ok), null)
				.show();
	}

}
