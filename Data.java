import java.util.ArrayList;

public class Data {
	private String LoadedClass;
	private ArrayList<Student> studentList = new ArrayList<Student>();
	private ArrayList<Gradable> gradableList = new ArrayList<Gradable>();
	private ArrayList<String> studentTypes = new ArrayList<String>();
	private ArrayList<String> gradableTypes = new ArrayList<String>();


	public Data(String LoadedClass) {
		this.LoadedClass = LoadedClass;
		
		// construct the list of gradables
		gradableTypes.add("Midterm");
		gradableTypes.add("Homework");
		gradableTypes.add("Project");
		gradableList.add(new Gradable("Midterm 1",20,117,"Midterm"));
		gradableList.add(new Gradable("Blackjack",20,100,"Homework"));
		gradableList.add(new Gradable("Treinta Ena",20,100,"Homework"));
		gradableList.add(new Gradable("Grading System",30,200,"Project"));
		
		studentTypes.add("Graduate");
		studentTypes.add("Undergraduate");
		// construct the list of students
		studentList.add(new Student("Joe","O'Donnell","U08447737","Graduate"));
		studentList.add(new Student("Alexis","Nunes","U08447738","Graduate"));
		studentList.add(new Student("Tom","Corcoran","U08447739","Undergraduate"));
		studentList.add(new Student("Yize","Liu","U08447740","Undergraduate"));
		

		
	}

	// Loaded Class accessors and mutators
	public void setLoadedClass(String lc) {
		LoadedClass = lc;
	}
	
	public String getLoadedClass() {
		return LoadedClass;
	}
	
	// Student accessors and mutators
	public Student getStudent(int i) {
		return studentList.get(i);
	}
	
	public void addStudent(String firstName, String lastName, String schoolID, String year) {
		studentList.add(new Student(firstName,lastName,schoolID,year));
	}
	
	public void dropStudent(Student s) {
		studentList.remove(s);
	}
	
	public int nStudents() {
		return studentList.size();
	}
	
	
	public ArrayList<String> studentTypes() {
		return studentTypes;
	}
	
	public Gradable getGradable(int i) {
		return gradableList.get(i);
	}
	
	public int nGradables() {
		return gradableList.size();
	}
	
	public ArrayList<String> gradableTypes() {
		return gradableTypes;
	}
	// public getStudent(String fname,String lname)
	
	
	

}