package matvidako.supertrianglecalculator.main;

import java.util.ArrayList;

import matvidako.supertrianglecalculator.R;
import matvidako.supertrianglecalculator.calculator.CalculatorActivity;
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
			startActivity(new Intent(this, CalculatorActivity.class));
		} else if(id == MenuItemIds.rectangle){
			
		} else if(id == MenuItemIds.circle){
			
		}
		
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
