package gui;
public class GradableType {
	private String type;
	private int weight;

	public GradableType(String type, int weight) {
		this.type = type;
		this.weight = weight;
	}
		
	public String getType() {
		return type;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public String toString() {
		return type;
	}

}