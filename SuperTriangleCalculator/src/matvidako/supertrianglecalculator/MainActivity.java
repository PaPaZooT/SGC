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
public class MainActivity extends Activity {

	// HashMap<Integer, EditText> etMap = new HashMap<Integer, EditText>();
	boolean inDegrees = true;

	private ArrayList<ShapeProperty> propertiesInDefault = new ArrayList<ShapeProperty>();
	private ArrayList<ShapeProperty> propertiesInKnown = new ArrayList<ShapeProperty>();
	private ArrayList<ShapeProperty> propertiesInCalculated = new ArrayList<ShapeProperty>();
	private ArrayList<ShapeProperty> propertiesInCantCalculate = new ArrayList<ShapeProperty>();

	private GridView gridDefault, gridKnown, gridCalculated, gridCantCalculate;
	private View headerKnown, headerCalculated, headerCantCalculate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);
		setupViews();
		// setupEditTexts();
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

		propertiesInDefault.clear();
		propertiesInDefault.addAll(triangleProperties);

		propertiesInCalculated.clear();
		propertiesInCantCalculate.clear();
		propertiesInKnown.clear();

		GridItemAdapter adapter = new GridItemAdapter(this, propertiesInDefault);
		adapter.setOnPropertyChangeListener(new OnPropertyChangeListener() {

			@Override
			public void propertyChanged(ShapeProperty property) {
				addKnownProperty(property);
			}
		});
		gridDefault.setAdapter(adapter);
		gridDefault.setOnItemClickListener(adapter);

		adapter = new GridItemAdapter(this, propertiesInCalculated);
		gridCalculated.setOnItemClickListener(adapter);
		gridCalculated.setAdapter(adapter);

		adapter = new GridItemAdapter(this, propertiesInCantCalculate);
		gridCantCalculate.setOnItemClickListener(adapter);
		gridCantCalculate.setAdapter(adapter);
		
		adapter = new GridItemAdapter(this, propertiesInKnown);
		gridKnown.setOnItemClickListener(adapter);
		gridKnown.setAdapter(adapter);

		updateGridsVisibility();
	}

	public void addKnownProperty(ShapeProperty property) {
		propertiesInDefault.remove(property);
		propertiesInKnown.add(property);
		((BaseAdapter) gridDefault.getAdapter()).notifyDataSetChanged();
		((BaseAdapter) gridKnown.getAdapter()).notifyDataSetChanged();
		updateGridsVisibility();
	}

	private void updateGridsVisibility() {
		if (propertiesInDefault.isEmpty()) {
			gridDefault.setVisibility(View.GONE);
		} else {
			gridDefault.setVisibility(View.VISIBLE);
		}
		if (propertiesInCalculated.isEmpty()) {
			gridCalculated.setVisibility(View.GONE);
			headerCalculated.setVisibility(View.GONE);
		} else {
			gridCalculated.setVisibility(View.VISIBLE);
			headerCalculated.setVisibility(View.VISIBLE);
		}
		if (propertiesInKnown.isEmpty()) {
			gridKnown.setVisibility(View.GONE);
			headerKnown.setVisibility(View.GONE);
		} else {
			gridKnown.setVisibility(View.VISIBLE);
			headerKnown.setVisibility(View.VISIBLE);
		}
		if (propertiesInCantCalculate.isEmpty()) {
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
		Triangle triangle = new Triangle(propertiesInKnown, inDegrees, this);

		/*
		 * Triangle triangle = new Triangle(getEditTextStringById(R.id.et_a),
		 * getEditTextStringById(R.id.et_b), getEditTextStringById(R.id.et_c),
		 * getEditTextStringById(R.id.et_ha), getEditTextStringById(R.id.et_hb),
		 * getEditTextStringById(R.id.et_hc),
		 * getEditTextStringById(R.id.et_alpha),
		 * getEditTextStringById(R.id.et_beta),
		 * getEditTextStringById(R.id.et_gamma),
		 * getEditTextStringById(R.id.et_area),
		 * getEditTextStringById(R.id.et_perimiter),
		 * getEditTextStringById(R.id.et_r), getEditTextStringById(R.id.et_R),
		 * inDegrees, this);
		 */

		triangle.calculateAll();

		if (triangle.isValid()) {
			ArrayList<ShapeProperty> properties = triangle.getProperties();
			propertiesInDefault.clear();
			for (ShapeProperty p : properties) {
				if (!p.isValid()) {
					propertiesInCantCalculate.add(p);
				} else {
					if (!propertiesInKnown.contains(p)) {
						propertiesInCalculated.add(p);
					}
				}
			}
			updateGridsVisibility();
		}
		// setTriangleData(triangle);
		else
			Toast.makeText(this, getString(R.string.invalidTriangle),
					Toast.LENGTH_LONG).show();
	}

	/*
	 * private void setTriangleData(Triangle triangle) {
	 * setEditTextStringById(R.id.et_a, triangle.getA());
	 * setEditTextStringById(R.id.et_b, triangle.getB());
	 * setEditTextStringById(R.id.et_c, triangle.getC());
	 * 
	 * setEditTextStringById(R.id.et_area, triangle.getArea());
	 * setEditTextStringById(R.id.et_perimiter, triangle.getPerimeter());
	 * 
	 * setEditTextStringById(R.id.et_ha, triangle.getHa());
	 * setEditTextStringById(R.id.et_hb, triangle.getHb());
	 * setEditTextStringById(R.id.et_hc, triangle.getHc());
	 * 
	 * setEditTextStringById(R.id.et_alpha, triangle.getAlpha());
	 * setEditTextStringById(R.id.et_beta, triangle.getBeta());
	 * setEditTextStringById(R.id.et_gamma, triangle.getGamma());
	 * 
	 * setEditTextStringById(R.id.et_r, triangle.getr());
	 * setEditTextStringById(R.id.et_R, triangle.getR()); }
	 */
	/*
	 * private String getEditTextStringById(int id) { return
	 * etMap.get(id).getText().toString(); }
	 * 
	 * private void setEditTextStringById(int id, String text) {
	 * etMap.get(id).setText(text); }
	 */

	private void clear() {
		resetGridViews();
		/*
		 * for (int k : etMap.keySet()) etMap.get(k).setText("");
		 */
	}

	/*
	 * private void setupEditTexts() { EditText et; et = (EditText)
	 * findViewById(R.id.et_a); etMap.put(et.getId(), et); et = (EditText)
	 * findViewById(R.id.et_b); etMap.put(et.getId(), et); et = (EditText)
	 * findViewById(R.id.et_c); etMap.put(et.getId(), et); et = (EditText)
	 * findViewById(R.id.et_ha); etMap.put(et.getId(), et); et = (EditText)
	 * findViewById(R.id.et_hb); etMap.put(et.getId(), et); et = (EditText)
	 * findViewById(R.id.et_hc); etMap.put(et.getId(), et); et = (EditText)
	 * findViewById(R.id.et_alpha); etMap.put(et.getId(), et); et = (EditText)
	 * findViewById(R.id.et_beta); etMap.put(et.getId(), et); et = (EditText)
	 * findViewById(R.id.et_gamma); etMap.put(et.getId(), et); et = (EditText)
	 * findViewById(R.id.et_area); etMap.put(et.getId(), et); et = (EditText)
	 * findViewById(R.id.et_perimiter); etMap.put(et.getId(), et); et =
	 * (EditText) findViewById(R.id.et_r); etMap.put(et.getId(), et); et =
	 * (EditText) findViewById(R.id.et_R); etMap.put(et.getId(), et); }
	 */
}
