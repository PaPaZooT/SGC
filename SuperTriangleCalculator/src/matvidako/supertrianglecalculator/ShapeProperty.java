package matvidako.supertrianglecalculator;

public class ShapeProperty {

	private String name;
	private double value;
	int decimalPlaces = 4;//TOOD settings 
	
	public ShapeProperty(String name){
		this.name = name;
		this.value = - 1.0f;
	}

	public ShapeProperty(String name, double value){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public double getValue() {
		return roundValue();
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public boolean isValid(){
		return value > -1;
	}
	
	public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof ShapeProperty))
            return false;
        ShapeProperty other = (ShapeProperty) obj;
        return name.equals(other.getName());
	}
	
	private double roundValue(){
		return Math.round( value * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);//TODO refactor if needed
	}
}
