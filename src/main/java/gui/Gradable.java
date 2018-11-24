package gui;

public class Gradable {
	private int gradableId;
	private String name;
	private int total;
	private GradableType type;
	private int pointsLost;
	private int IntraCategoryWeight;
	private int StudentWeight;
	private String note;
	
	public Gradable() {
		
	}

	public int getGradableId() {
		return gradableId;
	}

	public void setGradableId(int gradableId) {
		this.gradableId = gradableId;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setType(GradableType type) {
		this.type = type;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Gradable(String name, int total, GradableType type, int IntraCategoryWeight) {
		this.name = name;
		this.total = total;
		this.type = type;
		this.IntraCategoryWeight = IntraCategoryWeight;
	}
		
	public Gradable(String name, int total, GradableType type,int IntraCategoryWeight,int pointsLost,int StudentWeight,String note) {
		this.name = name;
		this.total = total;
		this.type = type;
		this.IntraCategoryWeight = IntraCategoryWeight;
		this.StudentWeight = StudentWeight;
		this.pointsLost = pointsLost;
		this.note = note;
	}
		
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPointsLost(int pointsLost) {
		this.pointsLost = pointsLost;
	}
	
	public int getPointsLost() {
		return pointsLost;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCategoryWeight(String studentType) {
		return type.getWeight(studentType);
	}
	
	public int getIntraCategoryWeight() {
		return IntraCategoryWeight;
	}
	
	public int getStudentWeight() {
		return StudentWeight;
	}
	
	public void setIntraCategoryWeight(int weight) {
		IntraCategoryWeight = weight;
	}
	
	public void setStudentWeight(int weight) {
		StudentWeight = weight;
	}
	
	public int getPoints() {
		return total;
	}
	
	public GradableType getType() {
		return type;
	}

	public String getNote() {
		return note;
	}

	
	public String toString() {
		return name;
	}

	public int getPercentage() {
		return (total-pointsLost)*100/total;
	}
	
	public boolean isType(String type) {
		return this.type.getType().equals(type);
	}	
	
}