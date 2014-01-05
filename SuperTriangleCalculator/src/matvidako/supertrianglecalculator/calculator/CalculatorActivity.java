package matvidako.supertrianglecalculator.calculator;

import java.util.ArrayList;

import matvidako.supertrianglecalculator.R;
import matvidako.supertrianglecalculator.general.DialogLauncher;
import matvidako.supertrianglecalculator.general.Utils;
import matvidako.supertrianglecalculator.shapes.GridItemCalculatorAdapter;
import matvidako.supertrianglecalculator.shapes.ShapeProperty;
import matvidako.supertrianglecalculator.shapes.Triangle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

@SuppressLint("UseSparseArrays")
public class CalculatorActivity extends Activity {

	boolean inDegrees = true;

	private GridView gridDefault, gridKnown, gridCalculated, gridCantCalculate;
	private View headerKnown, headerCalculated, headerCantCalculate;
	private GridItemCalculatorAdapter adapterDefault, adapterKnown, adapterCalculated,
			adapterCantCalculate;

	private ArrayList<ShapeProperty> allProperties,
			inputedProperties = new ArrayList<ShapeProperty>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		setupViews();
		setupAdapters();
		Explain.setup(this);
		resetGridViews();
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
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

	private class MovePropertyChangeListener implements
			OnPropertyChangeListener {

		private GridItemCalculatorAdapter from;
		private GridItemCalculatorAdapter to;

		public MovePropertyChangeListener(GridItemCalculatorAdapter from,
				GridItemCalculatorAdapter to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public void propertyChanged(ShapeProperty property) {
			// from.removeItem(property);
			// to.addItem(property);
			// updateGridsVisibility();
			Utils.addOrUpdate(inputedProperties, property);
			//inputedProperties.add(property);
		}
	}

	private void setupAdapters() {
		adapterDefault = new GridItemCalculatorAdapter(this);
		gridDefault.setAdapter(adapterDefault);
		gridDefault.setOnItemClickListener(adapterDefault);

		adapterKnown = new GridItemCalculatorAdapter(this);
		gridKnown.setAdapter(adapterKnown);
		gridKnown.setOnItemClickListener(adapterKnown);

		adapterCalculated = new GridItemCalculatorAdapter(this);
		gridCalculated.setAdapter(adapterCalculated);
		gridCalculated.setOnItemClickListener(adapterCalculated);

		adapterCantCalculate = new GridItemCalculatorAdapter(this);
		gridCantCalculate.setAdapter(adapterCantCalculate);
		gridCantCalculate.setOnItemClickListener(adapterCantCalculate);

		adapterDefault
				.setOnPropertyChangeListener(new MovePropertyChangeListener(
						adapterDefault, adapterKnown));
		adapterCantCalculate
				.setOnPropertyChangeListener(new MovePropertyChangeListener(
						adapterCantCalculate, adapterKnown));
	}

	private void resetGridViews() {
		allProperties = new ArrayList<ShapeProperty>();
		allProperties.add(new ShapeProperty(Triangle.Properties.a));
		allProperties.add(new ShapeProperty(Triangle.Properties.b));
		allProperties.add(new ShapeProperty(Triangle.Properties.c));
		allProperties.add(new ShapeProperty(Triangle.Properties.ha));
		allProperties.add(new ShapeProperty(Triangle.Properties.hb));
		allProperties.add(new ShapeProperty(Triangle.Properties.hc));
		allProperties.add(new ShapeProperty(Triangle.Properties.alpha));
		allProperties.add(new ShapeProperty(Triangle.Properties.beta));
		allProperties.add(new ShapeProperty(Triangle.Properties.gamma));
		allProperties.add(new ShapeProperty(Triangle.Properties.r));
		allProperties.add(new ShapeProperty(Triangle.Properties.R));
		allProperties.add(new ShapeProperty(Triangle.Properties.P));
		allProperties.add(new ShapeProperty(Triangle.Properties.A));

		adapterDefault.clearItems();
		adapterDefault.addItems(allProperties);
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
		case android.R.id.home:
			finish();
			break;
		case R.id.action_calculate:
			calculate();
			Utils.hideKeyboard(gridCalculated, this);
			break;
		case R.id.action_clear:
			clear();
			Utils.hideKeyboard(gridCalculated, this);
			break;
		case R.id.action_explain:
			DialogLauncher.launchExplainDialog(this);
			break;
		case R.id.action_help:
			DialogLauncher.launchHelpDialog(this, getString(R.string.help),
					getString(R.string.helpText));
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

	private void calculate() {
		Explain.clear();
		Triangle triangle = new Triangle(inputedProperties, inDegrees, this);
		triangle.calculateAll();

		if (triangle.isValid()) {
			ArrayList<ShapeProperty> properties = triangle.getProperties();
			adapterDefault.clearItems();
			adapterCantCalculate.clearItems();
			for (ShapeProperty p : properties) {
				if (inputedProperties.contains(p)) {
					adapterKnown.updateItem(p);
				} else if (!p.isValid()) {
					adapterCantCalculate.updateItem(p);
				} else if (!adapterKnown.contains(p)) {
					adapterCalculated.updateItem(p);
				}
			}
			updateGridsVisibility();
		} else {
			Toast.makeText(this, getString(R.string.invalidTriangle),
					Toast.LENGTH_LONG).show();
		}
	}

	private void clear() {
		inputedProperties.clear();
		resetGridViews();
	}

}
