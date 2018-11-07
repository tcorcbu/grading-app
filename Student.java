import java.util.ArrayList;


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
		
		// need to add from database
		gradableList.add(new Gradable("Midterm 1",20,117,"Midterm",5,firstName +" note for Midterm 1"));
		gradableList.add(new Gradable("Blackjack",20,100,"Homework",6,firstName +" note for Blackjack"));
		gradableList.add(new Gradable("Treinta Ena",20,100,"Homework",4,firstName +" note for Treinta Ena"));
		gradableList.add(new Gradable("Grading System",30,200,"Project",7,firstName +" Note for Grading System"));
		
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
	
	public String getSchoolID() {
		return schoolID;
	}
	
	public String getYear() {
		return year;
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
	
	public String toString() {
		return firstName+" "+lastName;
	}
	
	public boolean is(String type) {
		return this.year.equals(type);
	}	
}