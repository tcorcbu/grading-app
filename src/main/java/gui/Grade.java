package gui;

public class Grade {
	private Gradable gradable;
	private int pointsLost;
	private int studentWeight;
	private String note;
	
	public Grade() {
	}
	
	public Grade(Gradable g) {
		gradable = g;
	}
	
	public Grade(Gradable g, int pl, int sw, String n) {
		gradable = g;
		pointsLost = pl;
		studentWeight = sw;
		note = n;
	}
	
	public void setGradable(Gradable g) {
		gradable = g;
	}
	
	public Gradable getGradable() {
		return gradable;
	}
	
	public void setPointsLost(int pl) {
		pointsLost = pl;
	}
	
	public int getPointsLost() {
		return pointsLost;
	}
	
	public void setStudentWeight(int sw) {
		studentWeight = sw;
	}
	
	public int getStudentWeight() {
		return studentWeight;
	}
	
	public void setNote(String n) {
		note = n;
	}
	
	public String getNote() {
		return note;
	}
	
	public int getPoints() {
		return gradable.getPoints();
	}
	
	public int getIntraCategoryWeight() {
		return gradable.getIntraCategoryWeight();
	}
	
	public int getPercentage() {
		return (gradable.getPoints()-pointsLost)*100/gradable.getPoints();
	}
	
	public boolean hasNote() {
		if(note != null && note.length() > 0){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isType(String c) {
		if(gradable.isType(c)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return gradable.getName();
	}
	
	public boolean isGradable(Gradable g) {
		if(this.gradable.getName().equals(g.getName())) {
			return true;
		}else{
			return false;
		}
	}
}