package gui;

import db.ClassService;
import db.StudentClassService;
import db.StudentService;
import db.GradableTypeService;

import java.util.ArrayList;
import java.util.List;

public class Data {
	private String LoadedClass;
	private int classId;
	private ArrayList<Student> studentList = new ArrayList<Student>();
	private ArrayList<Gradable> gradableList = new ArrayList<Gradable>();
	private ArrayList<String> studentTypes = new ArrayList<String>();
	private ArrayList<GradableType> gradableTypes = new ArrayList<GradableType>();


	public Data() {
		// add default gradable types for new class
		gradableTypes.add(new GradableType("Homework",25,25));
		gradableTypes.add(new GradableType("Project",25,25));
		gradableTypes.add(new GradableType("Participation",25,25));
		gradableTypes.add(new GradableType("Final",25,25));
		
		// add student types for new class
		studentTypes.add("Graduate");
		studentTypes.add("Undergraduate");
	}
	
	public Data(String LoadedClass) {
		this.LoadedClass = LoadedClass;
        classId = ClassService.getId(LoadedClass);
				
		
		studentTypes.add("Graduate");
		studentTypes.add("Undergraduate");
		getStudents();
		getGradableTypes();

	}

	// Loaded Class accessors and mutators
	public void setLoadedClass(String lc) {
		LoadedClass = lc;
		classId = ClassService.getId(LoadedClass);
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
        int studentId = StudentService.insertStudent(newStudent);
        StudentClassService.insertStudentClass(classId, studentId);
        getStudents();
	}
	
	public void dropStudent(Student s) {
	    int studentId = StudentService.getId(s);
        StudentClassService.deleteStudentClass(classId,studentId);
	    getStudents();
	}

	public void getStudents(){
        List<Integer>studentIds = StudentClassService.getAllStudentsId(classId);
		
		this.studentList.clear();
        for (Integer id : studentIds) {
            studentList.add(StudentService.getStudentById(id));
        }
    }
	
	public void getGradableTypes(){
        List<Integer>gradableTypeIDs = GradableTypeService.getAllGradableTypeId(classId);
		
		this.gradableTypes.clear();
        for (Integer id : gradableTypeIDs) {
            gradableTypes.add(GradableTypeService.getGradableTypeById(id));
        }
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
	
	public void addGradableType(GradableType gt) {
		gradableTypes.add(gt);
	}
	
	public void removeGradableType(String gt) {
		for(int i=0; i<gradableTypes.size(); i++) {
			if(gradableTypes.get(i).getType() == gt){
				gradableTypes.remove(gradableTypes.get(i));
			}
		}
	}
	
	public ArrayList<String> copyStudentTypes() {
		return new ArrayList<String>(studentTypes);
	}
	
	public void clone(Data data2clone) {
		this.gradableList = data2clone.copyGradables();
		this.gradableTypes = data2clone.copyGradableTypes();
		this.studentTypes = data2clone.copyStudentTypes();
	}
	
	public int getClassID() {
		return classId;
	}
	
	

}