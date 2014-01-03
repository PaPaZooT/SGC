package matvidako.supertrianglecalculator;

import java.util.ArrayList;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class GridItemAdapter extends BaseAdapter implements OnItemClickListener{

	private LayoutInflater inflater;
	private int layoutId = R.layout.grid_item;
	private ArrayList<ShapeProperty> properties = new ArrayList<ShapeProperty>();
	private Context context;
	private OnPropertyChangeListener onPropertyChangeListener = null;
	
	public GridItemAdapter(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	
	public void addItem(ShapeProperty property){
		properties.add(property);
		this.notifyDataSetChanged();
	}
	
	public void addItems(ArrayList<ShapeProperty> properties){
		this.properties.addAll(properties);
		this.notifyDataSetChanged();
	}
	
	public void removeItem(ShapeProperty property){
		properties.remove(property);
		this.notifyDataSetChanged();
	}
	
	public void clearItems(){
		properties.clear();
		this.notifyDataSetChanged();
	}
	
	public boolean contains(ShapeProperty property){
		return properties.contains(property);
	}
	
	public void updateItem(ShapeProperty property){
		Utils.addOrUpdate(properties, property);
		this.notifyDataSetChanged();
	}
	
	public void setOnPropertyChangeListener(OnPropertyChangeListener listener){
		onPropertyChangeListener = listener;
	}
	
	@Override
	public int getCount() {
		return properties.size();
	}

	@Override
	public ShapeProperty getItem(int pos) {
		return properties.get(pos);
	}

	public ArrayList<ShapeProperty> getItems(){
		return properties;
	}
	
	@Override
	public long getItemId(int pos) {
		return 0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		final View view = inflater.inflate(layoutId, parent, false);
		view.setTag(true);
		final ShapeProperty property = properties.get(pos);
		
		TextView tv = (TextView) view.findViewById(R.id.grid_item_text_view);
		tv.setText(property.getName());
		
		final EditText et = (EditText) view.findViewById(R.id.grid_item_edit_text);
		et.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_DONE){
					try{
						property.setValue(Double.parseDouble(v.getText().toString()));
						if(onPropertyChangeListener != null) onPropertyChangeListener.propertyChanged(property);
					} catch(Exception e){}
					switchViewVisibility(true, view);
					GridItemAdapter.this.notifyDataSetChanged();
					Utils.hideKeyboard(view, context);
				}
				return false;
			}
		});
		
		et.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					//property.setValue(Double.parseDouble(et.getText().toString()));
					switchViewVisibility(true, view);
				}
			}
		});
		
		updateValueLabel(view, pos);
		return view;
	}

	private void updateValueLabel(View view, int pos){
		ShapeProperty property = getItem(pos);
		TextView tv = (TextView) view.findViewById(R.id.grid_item_text_view);
		EditText et = (EditText) view.findViewById(R.id.grid_item_edit_text);
		if(property.getValue() > -1){
			String value = String.valueOf(property.getValue());
			tv.setText(tv.getText() + " = " + value);
			et.setText(value);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
		boolean isLabelShown = (Boolean) view.getTag();
		if(!isLabelShown) return;
		view.setTag(!isLabelShown);
		switchViewVisibility(false, view);
	}

	private void switchViewVisibility(boolean showLabel, View view){
		final EditText edit = (EditText) view.findViewById(R.id.grid_item_edit_text);
		View label = view.findViewById(R.id.grid_item_text_view);
		if(showLabel){
			label.setVisibility(View.VISIBLE);
			edit.setVisibility(View.GONE);
			view.setTag(true);
		} else{
			label.setVisibility(View.GONE);
			edit.postDelayed(new Runnable() {
				@Override
				public void run() {
					edit.requestFocus();
					Utils.showKeyboard(edit, context);
				}
			}, 100);
			edit.setVisibility(View.VISIBLE);
		}
	}
}
