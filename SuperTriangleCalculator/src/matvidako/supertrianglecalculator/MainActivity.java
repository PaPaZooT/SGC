package matvidako.supertrianglecalculator;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
 
@SuppressLint("UseSparseArrays")
public class MainActivity extends Activity{
	
	HashMap<Integer, EditText> etMap = new HashMap<Integer, EditText>();
	boolean inDegrees = true;
	 
	@Override  
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Holo_Light); 
		setContentView(R.layout.activity_main);
		setupEditTexts(); 
		Explain.setup(this); 
	}   
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	} 
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_calculate:
			calculate();
			break;
		case R.id.action_clear:
			clear();
			break;
		case R.id.action_explain:
			launchExplainDialog();
			break;
	    case R.id.action_help:
	    	launchHelpDialog();
	    	break;   
	    case R.id.action_degrees:
	    	inDegrees = true;
	    	Toast.makeText(this, getString(R.string.setToDegrees), Toast.LENGTH_SHORT).show();
	    	break;
	    case R.id.action_radians:
	    	inDegrees = false;
	    	Toast.makeText(this, getString(R.string.setToRadians), Toast.LENGTH_SHORT).show();
	    	break;
	    default: 
	    	break;
	    }
	    return true;
	  } 
	
	private void launchHelpDialog(){
    	new AlertDialog.Builder(this)
    	.setTitle(getString(R.string.help))
    	.setMessage(getString(R.string.helpText))
    	.setPositiveButton("ok", new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {}
    	})
    	.show();
	}

	private void launchExplainDialog() {
		String msg = getString(R.string.noExplanation);
		if(!Explain.isEmpty()) msg = Explain.getExplanation();
    	new AlertDialog.Builder(this)
    	.setMessage(msg)
    	.setTitle(R.string.explanation)
    	.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
    	})
    	.show();
	}

	private void calculate(){
		Explain.clear();
		Triangle triangle = 
				new Triangle(
						getEditTextStringById(R.id.et_a), 
						getEditTextStringById(R.id.et_b), 
						getEditTextStringById(R.id.et_c),
						getEditTextStringById(R.id.et_ha),
						getEditTextStringById(R.id.et_hb),
						getEditTextStringById(R.id.et_hc),
						getEditTextStringById(R.id.et_alpha),
						getEditTextStringById(R.id.et_beta),
						getEditTextStringById(R.id.et_gamma),
						getEditTextStringById(R.id.et_area),
						getEditTextStringById(R.id.et_perimiter),
						getEditTextStringById(R.id.et_r),
						getEditTextStringById(R.id.et_R),
						inDegrees,
						this);
		
		triangle.calculateAll();
		
		if(triangle.isValid())
			setTriangleData(triangle);
		else
			Toast.makeText(this, getString(R.string.invalidTriangle),Toast.LENGTH_LONG).show();
	}
	
	private void setTriangleData(Triangle triangle) {
		setEditTextStringById(R.id.et_a, triangle.getA());
		setEditTextStringById(R.id.et_b, triangle.getB());
		setEditTextStringById(R.id.et_c, triangle.getC());
		
		setEditTextStringById(R.id.et_area, triangle.getArea());
		setEditTextStringById(R.id.et_perimiter, triangle.getPerimeter());
		
		setEditTextStringById(R.id.et_ha, triangle.getHa());
		setEditTextStringById(R.id.et_hb, triangle.getHb());
		setEditTextStringById(R.id.et_hc, triangle.getHc());
		
		setEditTextStringById(R.id.et_alpha, triangle.getAlpha());
		setEditTextStringById(R.id.et_beta, triangle.getBeta());
		setEditTextStringById(R.id.et_gamma, triangle.getGamma());
		
		setEditTextStringById(R.id.et_r, triangle.getr());
		setEditTextStringById(R.id.et_R, triangle.getR());
	}

	private String getEditTextStringById(int id){
		return etMap.get(id).getText().toString();
	}
	
	private void setEditTextStringById(int id, String text){
		etMap.get(id).setText(text);
	} 
	
	private void clear(){
		for(int k : etMap.keySet()) etMap.get(k).setText("");
	}
	
	private void setupEditTexts() {
		EditText et;
		et = (EditText) findViewById(R.id.et_a);
		etMap.put(et.getId(), et);
		et = (EditText) findViewById(R.id.et_b);
		etMap.put(et.getId(), et);
		et = (EditText) findViewById(R.id.et_c);
		etMap.put(et.getId(), et);
		et = (EditText) findViewById(R.id.et_ha);
		etMap.put(et.getId(), et); 
		et = (EditText) findViewById(R.id.et_hb);
		etMap.put(et.getId(), et);
		et = (EditText) findViewById(R.id.et_hc);
		etMap.put(et.getId(), et);
		et = (EditText) findViewById(R.id.et_alpha);
		etMap.put(et.getId(), et);
		et = (EditText) findViewById(R.id.et_beta);
		etMap.put(et.getId(), et);
		et = (EditText) findViewById(R.id.et_gamma);
		etMap.put(et.getId(), et);
		et = (EditText) findViewById(R.id.et_area);
		etMap.put(et.getId(), et);
		et = (EditText) findViewById(R.id.et_perimiter);
		etMap.put(et.getId(), et);	
		et = (EditText) findViewById(R.id.et_r);
		etMap.put(et.getId(), et);	
		et = (EditText) findViewById(R.id.et_R);
		etMap.put(et.getId(), et);	
	}

}
