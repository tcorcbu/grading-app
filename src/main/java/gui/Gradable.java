package gui;

public class Gradable {
	private String name;
	private int total;
	private Category type;
	private int pointsLost;
	private int IntraCategoryWeight;
	private int StudentWeight;
	private String note;
	
	public Gradable() {
	}
	
	public Gradable(String name, int total, Category type, int IntraCategoryWeight) {
		this.name = name;
		this.total = total;
		this.type = type;
		this.IntraCategoryWeight = IntraCategoryWeight;
		this.pointsLost = total;
		this.IntraCategoryWeight = 100;
	}
		
	public Gradable(String name, int total, Category type,int IntraCategoryWeight,int pointsLost,int StudentWeight,String note) {
		this.name = name;
		this.total = total;
		this.type = type;
		this.IntraCategoryWeight = IntraCategoryWeight;
		this.StudentWeight = StudentWeight;
		this.pointsLost = pointsLost;
		this.note = note;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getTotal() {
		return total;
	}

	public void setType(Category type) {
		this.type = type;
	}

	public Category getType() {
		return type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setPointsLost(int pointsLost) {
		this.pointsLost = pointsLost;
	}
	
	public int getPointsLost() {
		return pointsLost;
	}
	
	public void setIntraCategoryWeight(int weight) {
		IntraCategoryWeight = weight;
	}	
	
	public int getIntraCategoryWeight() {
		return IntraCategoryWeight;
	}
	
	public void setStudentWeight(int weight) {
		StudentWeight = weight;
	}	
	
	public int getStudentWeight() {
		return StudentWeight;
	}
	
	public void setPoints(int p) {
		total = p;
	}

	public int getPoints() {
		return total;
	}
	
	public void setNote(String n) {
		note = n;
	}

	public String getNote() {
		return note;
	}
	
	public boolean hasNote() {
		if(note == null || note.length()<1) {
			return false;
		} else {
			return true;
		}
	}
	
	public int getCategoryWeight(String studentType) {
		return type.getWeight(studentType);
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
	
	public Gradable copy() {
		Gradable g = new Gradable(this.getName(),
									this.getPoints(),
									this.getType(),
									this.getIntraCategoryWeight(),
									this.getPointsLost(),
									this.getStudentWeight(),
									this.getNote());
		return g;
	}
}