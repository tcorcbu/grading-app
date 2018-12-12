package objects;
public class Category {
	private String type;
	private int GraduateWeight;
	private int UndergradWeight;

	public Category() {
		
	}
	
	public Category(String type, int GraduateWeight, int UndergradWeight) {
		this.type = type;
		this.GraduateWeight = GraduateWeight;
		this.UndergradWeight = UndergradWeight;
	}
	
	public void setType(String type) {
		this.type = type;
	}		
	
	public String getType() {
		return type;
	}

	public void setGraduateWeight(int graduateWeight) {
		GraduateWeight = graduateWeight;
	}

	public int getGraduateWeight() {
		return GraduateWeight;
	}

	public void setUndergradWeight(int undergradWeight) {
		UndergradWeight = undergradWeight;
	}

	public int getUndergradWeight() {
		return UndergradWeight;
	}

	public int getWeight(String type) {
		if(type.equals("Graduate")){
			return GraduateWeight;
		} else {
			return UndergradWeight;
		}
	}

	public String toString() {
		if(UndergradWeight != GraduateWeight){
			return type + " (" + String.valueOf(UndergradWeight) + "%, " + String.valueOf(GraduateWeight) + "%)";
		}else{
			return type + " (" + String.valueOf(UndergradWeight) + "%)";
		}
	}
}