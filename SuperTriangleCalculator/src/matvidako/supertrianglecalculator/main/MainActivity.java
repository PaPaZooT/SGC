package matvidako.supertrianglecalculator.main;

import java.util.ArrayList;

import matvidako.supertrianglecalculator.R;
import matvidako.supertrianglecalculator.calculator.CalculatorActivity;
import matvidako.supertrianglecalculator.shapes.Shape;
import matvidako.supertrianglecalculator.shapes.ShapeProperty;
import matvidako.supertrianglecalculator.shapes.Triangle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity implements OnItemClickListener {
	
	private interface MenuItemIds{
		public static final int triangle = 1;
		public static final int circle = 2;
		public static final int rectangle = 3;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GridView gridView = (GridView) findViewById(R.id.main_grid_view);
		gridView.setOnItemClickListener(this);
		gridView.setAdapter(setupAdapter());
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
		if(id == MenuItemIds.triangle){
			Intent i = new Intent(this, CalculatorActivity.class);
			Shape shape = new Triangle(createTriangleProperties(), true);
			//i.putExtra(CalculatorActivity.ARG_PROPERTIES, createTriangleProperties());
			i.putExtra(CalculatorActivity.ARG_SHAPE, shape);
			startActivity(i);
		} else if(id == MenuItemIds.rectangle){
			
		} else if(id == MenuItemIds.circle){
			
		}
		
	}
	
	private ArrayList<ShapeProperty> createTriangleProperties() {
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
		return triangleProperties;
	}

	private GridItemMainAdapter setupAdapter(){
		ArrayList<MainMenuItem> items = new ArrayList<MainMenuItem>();
		items.add(new MainMenuItem(MenuItemIds.triangle, getString(R.string.triangle), R.drawable.triangle_blue));
		items.add(new MainMenuItem(MenuItemIds.circle, getString(R.string.circle), R.drawable.triangle_blue));
		items.add(new MainMenuItem(MenuItemIds.rectangle, getString(R.string.rectangle), R.drawable.triangle_blue));
		GridItemMainAdapter adapter = new GridItemMainAdapter(this, items);
		return adapter;
	}
}
