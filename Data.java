import java.util.ArrayList;

public class Data {
	private String LoadedClass;
	private ArrayList<Student> studentList = new ArrayList<Student>();
	private ArrayList<Gradable> gradableList = new ArrayList<Gradable>();
	private ArrayList<String> studentTypes = new ArrayList<String>();
	private ArrayList<GradableType> gradableTypes = new ArrayList<GradableType>();


	public Data() {
	}
	
	public Data(String LoadedClass) {
		this.LoadedClass = LoadedClass;
		
		// construct the list of gradables
		gradableTypes.add(new GradableType("Midterm",20));
		gradableTypes.add(new GradableType("Homework",35));
		gradableTypes.add(new GradableType("Project",20));
		gradableTypes.add(new GradableType("Participation",5));
		gradableTypes.add(new GradableType("Final",20));
		
		gradableList.add(new Gradable("Midterm 1",117,gradableTypes.get(0),100));
		gradableList.add(new Gradable("Blackjack",100,gradableTypes.get(1),100));
		gradableList.add(new Gradable("Treinta Ena",100,gradableTypes.get(1),100));
		gradableList.add(new Gradable("Grading System",200,gradableTypes.get(2),100));
		gradableList.add(new Gradable("Participation",10,gradableTypes.get(3),100));
		gradableList.add(new Gradable("Final",100,gradableTypes.get(4),100));
		
		studentTypes.add("Graduate");
		studentTypes.add("Undergraduate");
		// construct the list of students
		studentList.add(new Student("Joe","O'Donnell","U08447737","Graduate"));
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
	
	public ArrayList<String> getStudentTypes() {
		return studentTypes;
	}
	
	public void addStudent(Student newStudent) {
		studentList.add(newStudent);
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
	
	public void addGradable(Gradable newGradable) {
		gradableList.add(newGradable);
	}
	
	public void dropGradable(Gradable g) {
		gradableList.remove(g);
	}
	
	public int nGradables() {
		return gradableList.size();
	}
	
	public ArrayList<GradableType> gradableTypes() {
		return gradableTypes;
	}
	
	public GradableType gradableTypes(int i) {
		return gradableTypes.get(i);
	}
	
	public ArrayList<Gradable> copyGradables() {
		return new ArrayList<Gradable>(gradableList);
	}
	
	public ArrayList<Gradable> getGradables() {
		return gradableList;
	}
	
	public void setGradables(ArrayList<Gradable> gl) {
		gradableList = new ArrayList<Gradable>(gl);
	}
	
	public ArrayList<GradableType> copyGradableTypes() {
		return new ArrayList<GradableType>(gradableTypes);
	}
	
	public void setGradableTypes(ArrayList<GradableType> gt) {
		gradableTypes = new ArrayList<GradableType>(gt);
	}
	
	public ArrayList<String> copyStudentTypes() {
		return new ArrayList<String>(studentTypes);
	}
	
	public void clone(Data data2clone) {
		this.gradableList = data2clone.copyGradables();
		this.gradableTypes = data2clone.copyGradableTypes();
		this.studentTypes = data2clone.copyStudentTypes();
	}
	// public getStudent(String fname,String lname)
	
	
	

}