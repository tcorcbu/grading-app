package gui;

import java.util.ArrayList;
import java.util.HashSet;

import db.GradeService;


public class Student {
	private String firstName;
	private String lastName;
	private String schoolID;
	private String year;
	private ArrayList<Grade> gradeList = new ArrayList<Grade>();
	
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

	public void setGradeList(ArrayList<Grade> gradeList) {
		this.gradeList = gradeList;
	}
	
	public ArrayList<Grade> getGradeList() {
		return gradeList;
	}

	public void addGrade(Gradable g) {
		gradeList.add(new Grade(g, g.getPoints(), 100, ""));
	}
	
	public void dropGrade(Gradable g) {
		for(int i=0; i<gradeList.size(); i++) {
			if(gradeList.get(i).isGradable(g)) {
				gradeList.remove(gradeList.get(i));
				GradeService.drop(g);
			}
		}
	}
	
	public Grade getGrade(int i) {
		return gradeList.get(i);
	}
	
	public Grade getGrade(String name) {
		Grade gtmp = new Grade();
		for (Grade grade : gradeList) {
			if(grade.getGradable().getName().equals(name)) {
				gtmp =  grade;
			}
		}
		return gtmp;
	}
	
	public boolean hasCategoryNote(Category c) {
		for(Grade g : gradeList) {
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
		for (Grade grade : gradeList) {
			if(grade.isType(type)) {
				assignmentTotal = grade.getPoints();
				assignmentPointsLost = grade.getPointsLost();
				assignmentIntraCategoryWeight = grade.getIntraCategoryWeight();
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
		for(Category category : categoryList) {
			total += getCategoryAverage(category.getType())*category.getWeight(year);
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