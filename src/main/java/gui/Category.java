package gui;
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
		
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getGraduateWeight() {
		return GraduateWeight;
	}

	public void setGraduateWeight(int graduateWeight) {
		GraduateWeight = graduateWeight;
	}

	public int getUndergradWeight() {
		return UndergradWeight;
	}

	public void setUndergradWeight(int undergradWeight) {
		UndergradWeight = undergradWeight;
	}

	public int getWeight(String type) {
		if(type == "Graduate"){
			return GraduateWeight;}
		else {
			return UndergradWeight;
			}

	}

	public String toString() {
		return type + " (" + String.valueOf(UndergradWeight) + "%, " + String.valueOf(GraduateWeight) + "%)";
	}

	// @Override
	// public boolean equals(Category c) {
		// boolean tf = false;
		// if(this.getType().equals(c.getType())){
			// System.out.println("true");
			// tf = true;
		// }
		// return tf;
	// }
}