package gui;

import java.util.ArrayList;
import java.util.HashSet;

import db.GradeService;


public class Student {
	private String firstName;
	private String lastName;
	private String schoolID;
	private String year;
	private ArrayList<Gradable> gradableList = new ArrayList<Gradable>();
	
	public Student() {
	}
	
	public Student(String firstName, String lastName, String schoolID, String year) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.schoolID = schoolID;
		this.year = year;
	}
		
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	
	
	public String getLastName() {
		return lastName;
	}
	
	public String getName() {
		return firstName + " " + lastName;
	}
	
	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}
	
	public String getSchoolID() {
		return schoolID;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getYear() {
		return year;
	}

	public void setGradableList(ArrayList<Gradable> gradableList) {
		this.gradableList = gradableList;
	}
	
	public ArrayList<Gradable> getGradableList() {
		return gradableList;
	}

	public void addGradable(Gradable g) {
		gradableList.add(g);
	}
	
	public void dropGradable(Gradable g) {
		for (int i = 0; i<gradableList.size(); i++) {
			if(gradableList.get(i).getName().equals(g.getName())) {
				gradableList.remove(gradableList.get(i));
			}
		}
	}
	
	public Gradable getGradable(int i) {
		return gradableList.get(i);
	}
	
	public Gradable getGradable(String name) {
		Gradable gtmp = new Gradable();
		for (int i = 0; i<gradableList.size(); i++) {
			if(gradableList.get(i).getName().equals(name)) {
				gtmp =  gradableList.get(i);
			}
		}
		return gtmp;
	}
	
	public boolean hasCategoryNote(Category c) {
		for(Gradable g : gradableList) {
			if(g.hasNote() && g.isType(c.getType())) { // 
				return true;
			}
		}
		return false;
	}
	
	public int getCategoryAverage(String type) {
		int total = 0;
		int sumWeights = 0;
		int assignmentTotal;
		int assignmentPointsLost;
		int assignmentIntraCategoryWeight;
		Gradable gtmp;
		for (int i = 0; i<gradableList.size(); i++) {
			if(gradableList.get(i).isType(type)) {
				gtmp = gradableList.get(i);
				assignmentTotal = gtmp.getPoints();
				assignmentPointsLost = gtmp.getPointsLost();
				assignmentIntraCategoryWeight = gtmp.getIntraCategoryWeight();
				if (assignmentTotal == 0) {
					total += 0;
				}else {
					total += (assignmentTotal-assignmentPointsLost)*assignmentIntraCategoryWeight/assignmentTotal;
				}
				sumWeights += assignmentIntraCategoryWeight;
			}
		}
		if (sumWeights == 0){
			return 0;
		} else {
			return total*100/sumWeights;
		}
	}
	
	public int getOverallPercent(ArrayList<Category> categoryList) {
		int total = 0;
		for(int i=0; i<categoryList.size(); i++) {
			total += getCategoryAverage(categoryList.get(i).getType())*categoryList.get(i).getWeight(year);
		}
		return total/100;
	}

	public String toString(){
		return firstName + " " + lastName;
	}
	
	public boolean isYear(String type) {
		return this.year.equals(type);
	}	

}