package gui;

import java.util.ArrayList;
import java.util.Random; // for making fake data


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
		
		// query from database using schoolID (already filtered on classID?) and iterate through all gradables.
		// Random rand = new Random();
		// gradableList.add(new Gradable("Midterm 1",117,new GradableType("Midterm",20),100,rand.nextInt(10),firstName +" note for Midterm 1"));
		// gradableList.add(new Gradable("Blackjack",100,new GradableType("Homework",35),100,rand.nextInt(10),firstName +" note for Blackjack"));
		// gradableList.add(new Gradable("Treinta Ena",100,new GradableType("Homework",35),100,rand.nextInt(10),firstName +" note for Treinta Ena"));
		// gradableList.add(new Gradable("Grading System",200,new GradableType("Project",20),100,rand.nextInt(10),firstName +" Note for Grading System"));
		// gradableList.add(new Gradable("Participation",10,new GradableType("Participation",5),100,rand.nextInt(10),firstName +" Note for Participation"));
		// gradableList.add(new Gradable("Final",100,new GradableType("Final",20),100,rand.nextInt(10),firstName +" Note for Final"));
		
	}
		
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getName() {
		return firstName + " " + lastName;
	}
	
	public String getSchoolID() {
		return schoolID;
	}
	
	public String getYear() {
		return year;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setGradableList(ArrayList<Gradable> gradableList) {
		this.gradableList = gradableList;
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
	
	public int getCategoryAverage(String type) {
		int n = 0;
		int total = 0;
		Gradable gtmp = new Gradable();
		for (int i = 0; i<gradableList.size(); i++) {
			if(gradableList.get(i).getType().getType().equals(type)) {
				gtmp =  gradableList.get(i);
				n += 1;
				total += gtmp.getPercentage();
			}
		}
		if (n == 0){
			return 0;
		} else {
			return total/n;
		}
	}
	
	public String toString() {
		return firstName+" "+lastName;
	}
	
	public boolean is(String type) {
		return this.year.equals(type);
	}	
}