package gui;
public class GradableType {
	private String type;
	private int GraduateWeight;
	private int UndergradWeight;

	public GradableType() {
		
	}
	
	public GradableType(String type, int GraduateWeight, int UndergradWeight) {
		this.type = type;
		this.GraduateWeight = GraduateWeight;
		this.UndergradWeight = UndergradWeight;
	}
		
	public String getType() {
		return type;
	}
	
	public int getWeight(String type) {
		if(type == "Graduate"){
			return GraduateWeight;}
		else {
			return UndergradWeight;
			}
	}
	
	public String toString() {
		return type;
	}

}