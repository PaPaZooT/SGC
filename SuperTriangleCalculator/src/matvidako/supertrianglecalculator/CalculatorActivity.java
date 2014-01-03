package matvidako.supertrianglecalculator;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

@SuppressLint("UseSparseArrays")
public class CalculatorActivity extends Activity {

	// HashMap<Integer, EditText> etMap = new HashMap<Integer, EditText>();
	boolean inDegrees = true;

	//private ArrayList<ShapeProperty> propertiesInDefault = new ArrayList<ShapeProperty>();
	//private ArrayList<ShapeProperty> propertiesInKnown = new ArrayList<ShapeProperty>();
	//private ArrayList<ShapeProperty> propertiesInCalculated = new ArrayList<ShapeProperty>();
	//private ArrayList<ShapeProperty> propertiesInCantCalculate = new ArrayList<ShapeProperty>();
	
	private GridView gridDefault, gridKnown, gridCalculated, gridCantCalculate;
	private View headerKnown, headerCalculated, headerCantCalculate;
	GridItemAdapter adapterDefault, adapterKnown, adapterCalculated, adapterCantCalculate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);
		setupViews();
		setupAdapters();
		Explain.setup(this);
		resetGridViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	private void setupViews() {
		gridDefault = (GridView) findViewById(R.id.calculator_grid_default);
		gridKnown = (GridView) findViewById(R.id.calculator_grid_known);
		gridCalculated = (GridView) findViewById(R.id.calculator_grid_calculated);
		gridCantCalculate = (GridView) findViewById(R.id.calculator_grid_couldnt_calculate);
		headerKnown = findViewById(R.id.calculator_header_known);
		headerCalculated = findViewById(R.id.calculator_header_calculated);
		headerCantCalculate = findViewById(R.id.calculator_header_couldnt_calculate);
	}
	
	private class MovePropertyChangeListener implements OnPropertyChangeListener{

		private GridItemAdapter from;
		private GridItemAdapter to;
		
		public MovePropertyChangeListener(GridItemAdapter from, GridItemAdapter to){
			this.from = from;
			this.to = to;
		}
		
		@Override
		public void propertyChanged(ShapeProperty property) {
			from.removeItem(property);
			to.addItem(property);
			updateGridsVisibility();
		}
	}
	
	private void setupAdapters(){
		adapterDefault = new GridItemAdapter(this);
		gridDefault.setAdapter(adapterDefault);
		gridDefault.setOnItemClickListener(adapterDefault);
		
		adapterKnown = new GridItemAdapter(this);
		gridKnown.setAdapter(adapterKnown);
		gridKnown.setOnItemClickListener(adapterKnown);
		
		adapterCalculated = new GridItemAdapter(this);
		gridCalculated.setAdapter(adapterCalculated);
		gridCalculated.setOnItemClickListener(adapterCalculated);
		
		adapterCantCalculate = new GridItemAdapter(this);
		gridCantCalculate.setAdapter(adapterCantCalculate);
		gridCantCalculate.setOnItemClickListener(adapterCantCalculate);
		
		adapterDefault.setOnPropertyChangeListener(new MovePropertyChangeListener(adapterDefault, adapterKnown));
		adapterCantCalculate.setOnPropertyChangeListener(new MovePropertyChangeListener(adapterCantCalculate, adapterKnown));
	}

	private void resetGridViews() {
		ArrayList<ShapeProperty> triangleProperties = new ArrayList<ShapeProperty>();
		triangleProperties.add(new ShapeProperty(Triangle.Properties.a));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.b));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.c));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.ha));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.hb));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.hc));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.alpha));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.beta));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.gamma));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.r));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.R));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.P));
		triangleProperties.add(new ShapeProperty(Triangle.Properties.A));

		
		adapterDefault.clearItems();
		adapterDefault.addItems(triangleProperties);
		adapterCalculated.clearItems();
		adapterCantCalculate.clearItems();
		adapterKnown.clearItems();

		updateGridsVisibility();
	}
	
	private void updateGridsVisibility() {
		if (adapterDefault.isEmpty()) {
			gridDefault.setVisibility(View.GONE);
		} else {
			gridDefault.setVisibility(View.VISIBLE);
		}
		if (adapterCalculated.isEmpty()) {
			gridCalculated.setVisibility(View.GONE);
			headerCalculated.setVisibility(View.GONE);
		} else {
			gridCalculated.setVisibility(View.VISIBLE);
			headerCalculated.setVisibility(View.VISIBLE);
		}
		if (adapterKnown.isEmpty()) {
			gridKnown.setVisibility(View.GONE);
			headerKnown.setVisibility(View.GONE);
		} else {
			gridKnown.setVisibility(View.VISIBLE);
			headerKnown.setVisibility(View.VISIBLE);
		}
		if (adapterCantCalculate.isEmpty()) {
			gridCantCalculate.setVisibility(View.GONE);
			headerCantCalculate.setVisibility(View.GONE);
		} else {
			gridCantCalculate.setVisibility(View.VISIBLE);
			headerCantCalculate.setVisibility(View.VISIBLE);
		}
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
			Toast.makeText(this, getString(R.string.setToDegrees),
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_radians:
			inDegrees = false;
			Toast.makeText(this, getString(R.string.setToRadians),
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return true;
	}

	private void launchHelpDialog() {
		new AlertDialog.Builder(this).setTitle(getString(R.string.help))
				.setMessage(getString(R.string.helpText))
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	private void launchExplainDialog() {
		String msg = getString(R.string.noExplanation);
		if (!Explain.isEmpty())
			msg = Explain.getExplanation();
		new AlertDialog.Builder(this).setMessage(msg)
				.setTitle(R.string.explanation)
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	private void calculate() {
		Explain.clear();
		Triangle triangle = new Triangle(adapterKnown.getItems(), inDegrees, this);
		triangle.calculateAll();

		if (triangle.isValid()) {
			ArrayList<ShapeProperty> properties = triangle.getProperties();
			adapterDefault.clearItems();
			adapterCantCalculate.clearItems();
			for (ShapeProperty p : properties) {
				if (!p.isValid()) {
					adapterCantCalculate.updateItem(p);
				} else {
					if (!adapterKnown.contains(p)) {
						adapterCalculated.updateItem(p);
					}
				}
			}
			updateGridsVisibility();
		}
		else
			Toast.makeText(this, getString(R.string.invalidTriangle),
					Toast.LENGTH_LONG).show();
	}

	private void clear() {
		resetGridViews();
	}

}
