package matvidako.supertrianglecalculator.shapes;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

public abstract class Shape implements Serializable{

	private static final long serialVersionUID = -3322714904948064240L;
	
	protected ArrayList<ShapeProperty> properties = new ArrayList<ShapeProperty>();
	protected boolean inDegrees;
	protected Context context;
	
	public Shape(boolean inDegrees){
		this.inDegrees = inDegrees;
	}
	
	public void setInDegrees(boolean inDegrees){
		this.inDegrees = inDegrees;
	}
	
	public void setContext(Context context){
		this.context = context;
	}
	
	public ArrayList<ShapeProperty> getProperties(){
		return properties;
	}

	public abstract void setProperties(ArrayList<ShapeProperty> properties);
	public abstract boolean isValid();
	public abstract void calculateAll();
	
}
