package gui;
public class GradableType {
	private Integer id;
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

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}