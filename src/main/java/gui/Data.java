package gui;

import db.*;

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
		// add student types for new class
		studentTypes.add("Graduate");
		studentTypes.add("Undergraduate");
	}
	
	public Data(String LoadedClass) {
		this.LoadedClass = LoadedClass;
        classId = ClassService.getId(LoadedClass);
				
		studentTypes.add("Graduate");
		studentTypes.add("Undergraduate");
        getCategories();
        refreshGradables();
        getStudents();
	}

	public void resetData(){
		LoadedClass = "";
		classId = -1;
		studentList = new ArrayList<Student>();
		gradableList = new ArrayList<Gradable>();
		studentTypes = new ArrayList<String>();
		gradableTypes = new ArrayList<GradableType>();
	}

	public void closeClass(){
		ClassService.closeClass(classId);
	}


	public void refreshGradables(){
        this.gradableList = GradableService.getAll(classId);
    }

	public void getCategories(){
	    this.gradableTypes = CategoryService.getAll(classId);
    }

    public ArrayList<GradableType> getGradableTypes(){
	    return this.gradableTypes;
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

		// check if a student is in the student table

		int res = StudentService.studentInDb(newStudent.getSchoolID());

		if(res == -1){// student not in the student table
			// add to both
			System.out.println("Not in db");
			int studentId = StudentService.insertStudent(newStudent);
			StudentClassService.insertStudentClass(classId, studentId);
		}else{// student in the db
			System.out.println("In db");

			// check if the student id and the class id are in the same row
			// in the studentclasses table

			int studentInClass = StudentClassService.studentInClass(classId, res);

			if(studentInClass == -1){ // not in class

				System.out.println("student not in class");

				StudentClassService.insertStudentClass(classId, res);

			}
			
		}
		
		for(int i=0; i<gradableList.size(); i++) {
			Gradable g = new Gradable(gradableList.get(i).getName(),
										gradableList.get(i).getPoints(),
										gradableList.get(i).getType(),
										gradableList.get(i).getIntraCategoryWeight(),
										gradableList.get(i).getPoints(),
										100,"");
			g.setID(gradableList.get(i).getID());
			newStudent.addGradable(g);
		}
        // getStudents();
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

        for (Student student : studentList) {
            student.setGradableList(GradeService.getAllGradablesForStudent(student,classId));
        }
        // this.studentList.clear();
        // for (Student student : students) {
            // this.studentList.add(student);
        // }
		// System.out.println(students);
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
	
	public Gradable getGradableByName(String name) {
		Gradable g = new Gradable();
		for (int i = 0; i<gradableList.size(); i++) {
			if(gradableList.get(i).getName().equals(name)) {
				g =  gradableList.get(i);
			}
		}
		return g;
	}
	
	public void addGradable(Gradable newGradable) {
		gradableList.add(newGradable);
	}
	
	public void dropGradable(Gradable g) {
	    GradableService.drop(g);
		GradeService.drop(g);
	    refreshGradables();
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
	
	public GradableType getGradableTypeByName(String name) {
		GradableType gt = new GradableType();
		for (int i = 0; i<gradableTypes.size(); i++) {
			if(gradableTypes.get(i).getType().equals(name)) {
				gt =  gradableTypes.get(i);
			}
		}
		return gt;
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
		for(int i=0; i<gradableList.size(); i++) {
			GradableService.insert(gradableList.get(i),classId);
		}
		for(int i=0; i<gradableTypes.size(); i++) {
			CategoryService.insert(gradableTypes.get(i),classId);
		}
	}

	public int getClassId() {
		return classId;
	}
	
	public void saveClass() {
		System.out.println("save class");
	}

}