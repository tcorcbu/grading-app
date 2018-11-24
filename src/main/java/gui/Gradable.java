package gui;

public class Gradable {
	private int gradableID;
	private String name;
	private int total;
	private GradableType type;
	private int pointsLost;
	private int IntraCategoryWeight;
	private int StudentWeight;
	private String note;
	
	public Gradable() {
		
	}
	
	public Gradable(String name, int total, GradableType type,int IntraCategoryWeight) {
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
	
	public void setPoints(int p) {
		total = p;
	}
	
	public GradableType getType() {
		return type;
	}

	public void setType(GradableType gt) {
		type = gt;
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
	
	public int getID() {
		return gradableID;
	}
	
	public void setID(int id) {
		gradableID = id;
	}
	
}