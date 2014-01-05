package matvidako.supertrianglecalculator.main;

import java.util.ArrayList;

import matvidako.supertrianglecalculator.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridItemMainAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private int layoutId = R.layout.grid_item_main;
	private ArrayList<MainMenuItem> items = new ArrayList<MainMenuItem>();
	
	public GridItemMainAdapter(Context context, ArrayList<MainMenuItem> items) {
		inflater = LayoutInflater.from(context);
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public MainMenuItem getItem(int pos) {
		return items.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return items.get(pos).getId();
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		View view = inflater.inflate(layoutId, parent, false);
		MainMenuItem item = getItem(pos);
		TextView titleTv = (TextView) view.findViewById(R.id.title);
		titleTv.setText(item.getTitle());
		ImageView image = (ImageView) view.findViewById(R.id.image);
		image.setImageResource(item.getImageId());
		return view;
	}
	
	
}
