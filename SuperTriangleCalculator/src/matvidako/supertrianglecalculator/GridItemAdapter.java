package matvidako.supertrianglecalculator;

import java.util.ArrayList;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

public class GridItemAdapter extends BaseAdapter implements OnItemClickListener {

	private LayoutInflater inflater;
	private int layoutId = R.layout.grid_item;
	private ArrayList<ShapeProperty> properties = new ArrayList<ShapeProperty>();
	private Context context;
	private OnPropertyChangeListener onPropertyChangeListener = null;

	public GridItemAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public void addItem(ShapeProperty property) {
		properties.add(property);
		this.notifyDataSetChanged();
	}

	public void addItems(ArrayList<ShapeProperty> properties) {
		this.properties.addAll(properties);
		this.notifyDataSetChanged();
	}

	public void removeItem(ShapeProperty property) {
		properties.remove(property);
		this.notifyDataSetChanged();
	}

	public void clearItems() {
		properties.clear();
		this.notifyDataSetChanged();
	}

	public boolean contains(ShapeProperty property) {
		return properties.contains(property);
	}

	public void updateItem(ShapeProperty property) {
		Utils.addOrUpdate(properties, property);
		this.notifyDataSetChanged();
	}

	public void setOnPropertyChangeListener(OnPropertyChangeListener listener) {
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

	public ArrayList<ShapeProperty> getItems() {
		return properties;
	}

	@Override
	public long getItemId(int pos) {
		return 0;
	}

	class ViewHolder implements OnEditorActionListener, TextWatcher, OnFocusChangeListener{
		
		TextView label;
		EditText edit;
		ShapeProperty property;
		
		public ViewHolder(TextView label, EditText edit, ShapeProperty property){
			this.label = label;
			this.edit = edit;
			this.property = property;
			this.edit.setOnEditorActionListener(this);
			this.edit.addTextChangedListener(this);
			this.edit.setOnFocusChangeListener(this);
			updateLabelText();
			updateEditText();
		}
		
		private void updateLabelText() {
			label.setText(property.getName());
			if (property.getValue() > -1) {
				String value = String.valueOf(property.getValue());
				label.setText(label.getText() + " = " + value);
			}
		}
		
		private void updateEditText() {
			if (property.getValue() > -1) {
				String value = String.valueOf(property.getValue());
				edit.setText(value);
			}
		}

		@Override
		public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				try {
					property.setValue(Double.parseDouble(tv.getText().toString()));
					if (onPropertyChangeListener != null)
						onPropertyChangeListener.propertyChanged(property);
				} catch (Exception e) {
				}
				//switchViewVisibility(this);
				GridItemAdapter.this.notifyDataSetChanged();
				Utils.hideKeyboard(edit, context);
			}
			return true;
		}

		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			try {
				property.setValue(Double.parseDouble(s.toString()));
				updateLabelText();
				if (onPropertyChangeListener != null)
					onPropertyChangeListener.propertyChanged(property);
			} catch (Exception e) {
			}
		}

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (!hasFocus) {
				switchViewVisibility(this);
			}
		}
	}
	
	@Override
	public View getView(final int pos, View convertView, ViewGroup parent) {
		View view = inflater.inflate(layoutId, parent, false);
		ShapeProperty property = properties.get(pos);
		TextView tv = (TextView) view.findViewById(R.id.grid_item_text_view);
		EditText et = (EditText) view.findViewById(R.id.grid_item_edit_text);
		ViewHolder holder = new ViewHolder(tv, et, property);
		view.setTag(holder);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder.label.getVisibility() == View.GONE) return;
		switchViewVisibility(holder);
	}

	private void switchViewVisibility(final ViewHolder holder) {
		boolean showLabel = holder.label.getVisibility() == View.GONE;
		if (showLabel) {
			holder.label.setVisibility(View.VISIBLE);
			holder.edit.setVisibility(View.GONE);
		} else {
			holder.label.setVisibility(View.GONE);
			holder.edit.setSelection(holder.edit.getText().length());
			holder.edit.postDelayed(new Runnable() {
				@Override
				public void run() {
					holder.edit.requestFocus();
					Utils.showKeyboard(holder.edit, context);
				}
			}, 100);
			holder.edit.setVisibility(View.VISIBLE);
		}
	}
}
