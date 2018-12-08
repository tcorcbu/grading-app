package gui;

import db.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.lang.Math;
import java.sql.*;

public class Data {
	private String LoadedClass;
	private ArrayList<Student> studentList = new ArrayList<Student>();
	private ArrayList<Gradable> gradableList = new ArrayList<Gradable>();
	private ArrayList<String> studentTypes = new ArrayList<String>();
	private ArrayList<Category> categoryList = new ArrayList<Category>();
	private ArrayList<PreparedStatement> saveCommand = new ArrayList<PreparedStatement>();


	public Data() {
		// add student types for new class
		studentTypes.add("Graduate");
		studentTypes.add("Undergraduate");
	}
	
	public Data(String LoadedClass) {
		this.LoadedClass = LoadedClass;
		Globals.setClassId(ClassService.getId(LoadedClass));
		
		studentTypes.add("Graduate");
		studentTypes.add("Undergraduate");
        loadCategories();
        loadGradables();
        loadStudents();
	}

	// public void resetData(){
		// LoadedClass = "";
		// class_id = -1;
		// studentList = new ArrayList<Student>();
		// gradableList = new ArrayList<Gradable>();
		// studentTypes = new ArrayList<String>();
		// categoryList = new ArrayList<Category>();
	// }

	// get individual gradable average (name of gradable)

	// public int getGradableAverage(String name){

	// 	class_id = Globals.class_id();
	// 	int sum = 0;

	// 	for(int k = 0; k<data.studentList.size(); k++) {
	// 		ArrayList<Gradable> gradableList = data.studentList.get(k).gradableList;
			
	// 		for (int i = 0; i<gradableList.size(); i++) {
	// 			if(gradableList.get(i).getName().equals(name) && gradableList.get(i).getClassId() == class_id) {
	// 				gtmp = gradableList.get(i);
	// 				sum += gtmp.getPercentage();
	// 				break;
	// 			}
	// 		}

	// 	}

	// 	return sum / studentList.size();

	// }

	

	public int sumUndergradCategories() {
		int mysum = 0;
		for(int i=0; i<categoryList.size(); i++) {
				mysum += categoryList.get(i).getWeight("Undergraduate");
		}
		return mysum;
	}
	
	public int sumGradCategories() {
		int mysum = 0;
		for(int i=0; i<categoryList.size(); i++) {
				mysum += categoryList.get(i).getWeight("Graduate");
		}
		return mysum;
	}
	
	public void addSaveCommand(PreparedStatement savecommand) {
		saveCommand.add(savecommand);
	}
	
	public void closeClass(){
		ClassService.closeClass(Globals.class_id());
	}

	public void loadGradables(){
		this.gradableList = GradableService.getAll(Globals.class_id());
		for (Gradable gradable : gradableList) {
			gradable.setType(findCategory(gradable.getType()));
		}
    }

    private Category findCategory(Category c) {
		for (Category category : categoryList) {
			if (category.getType().equals(c.getType())) {
				return category;
			}
		}
		return null;
	}

	public void loadCategories(){
	    this.categoryList = CategoryService.getAll(Globals.class_id());
    }

    public ArrayList<Category> getCategories(){
	    return this.categoryList;
    }
	// Loaded Class accessors and mutators
	public void setLoadedClass(String lc) {
		LoadedClass = lc;
		Globals.setClassId(ClassService.getId(LoadedClass));
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
		if(!StudentService.studentInDb(newStudent)){
			this.addSaveCommand(StudentService.insertStudent(newStudent));
		}
		
        this.addSaveCommand(StudentClassService.insertStudentClass(newStudent.getSchoolID()));

		for(int i=0; i<gradableList.size(); i++) {
			Gradable g = new Gradable(gradableList.get(i).getName(),
										gradableList.get(i).getPoints(),
										gradableList.get(i).getType(),
										gradableList.get(i).getIntraCategoryWeight(),
										gradableList.get(i).getPoints(),
										100,"");
			newStudent.addGradable(g);
			this.addSaveCommand(GradeService.insert(g,newStudent));
		}
	}
	
	public void dropStudent(Student s) {
	    // int studentId = StudentService.getId(s);
	    // if (studentId == -1) {
	    	// return;
		// }
        this.addSaveCommand(StudentClassService.deleteStudentClass(s.getSchoolID()));
		this.addSaveCommand(GradeService.dropStudentGrades(s.getSchoolID()));
	    // loadStudents();
	}

	public void loadStudents(){
        List<String>studentIds = StudentClassService.getAllStudentsId(Globals.class_id());

		this.studentList.clear();
        for (String id : studentIds) {
            studentList.add(StudentService.getStudentById(id));
        }

        for (Student student : studentList) {
            student.setGradableList(GradeService.getAllGradablesForStudent(student));
			for (Gradable gradable : student.getGradableList()) {
				gradable.setType(findCategory(gradable.getType()));
			}
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
	    this.addSaveCommand(GradableService.drop(g));
		this.addSaveCommand(GradeService.drop(g));
		
		for (int i = 0; i<gradableList.size(); i++) {
			if(gradableList.get(i).getName().equals(g.getName())) {
				gradableList.remove(gradableList.get(i));
			}
		}
	    // loadGradables();
	}
	
	public int nGradables() {
		return gradableList.size();
	}
	
	public ArrayList<Category> getCategoryList() {
		return categoryList;
	}
	
	public Category CategoryList(int i) {
		return categoryList.get(i);
	}
	
	public Category getCategoryByName(String name) {
		Category gt = new Category();
		for (int i = 0; i<categoryList.size(); i++) {
			if(categoryList.get(i).getType().equals(name)) {
				gt =  categoryList.get(i);
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
	
	public ArrayList<Category> copyCategories() {
		return new ArrayList<Category>(categoryList);
	}
	
	public void setCategories(ArrayList<Category> gt) {
		categoryList = new ArrayList<Category>(gt);
	}
	
	public void addCategory(Category gt) {
		categoryList.add(gt);
	}
	
	public void removeCategory(String gt) {
		for(int i=0; i<categoryList.size(); i++) {
			if(categoryList.get(i).getType().equals(gt)){
				categoryList.remove(categoryList.get(i));
			}
		}
	}

	public ArrayList<String> copyStudentTypes() {
		return new ArrayList<String>(studentTypes);
	}
	
	public void clone(Data data2clone) {
		this.gradableList = data2clone.copyGradables();
		this.categoryList = data2clone.copyCategories();
		this.studentTypes = data2clone.copyStudentTypes();
		System.out.print("class id in clone: ");
		System.out.println(Globals.class_id());
		for(int i=0; i<gradableList.size(); i++) {
			this.addSaveCommand(GradableService.insert(gradableList.get(i)));
		}
		for(int i=0; i<categoryList.size(); i++) {
			this.addSaveCommand(CategoryService.insert(categoryList.get(i)));
		}
	}
	
	public void saveClass() {
		try{
			for(int i=0; i<saveCommand.size(); i++) {
				saveCommand.get(i).execute();
			}	
        }catch (SQLException e) {
            e.printStackTrace();
        }
		saveCommand = new ArrayList<PreparedStatement>();
	}

	public int getClassMean() {
		int mean = 0;
		for (Student student : studentList) {
			mean += student.getOverallPercent(categoryList);
		}
		return mean/studentList.size();
	}
	
	public int getClassMedian() {
		Integer[] gradeList = new Integer[studentList.size()];
		for (int i=0; i<studentList.size(); i++) {
			gradeList[i] = studentList.get(i).getOverallPercent(categoryList);
		}
		Arrays.sort(gradeList);
		int median;
		if (gradeList.length % 2 == 0)
			median = ((int)gradeList[gradeList.length/2] + (int)gradeList[gradeList.length/2 - 1])/2;
		else
			median = (int) gradeList[gradeList.length/2];
		
		return median;
	}
	
	public int getClassStandardDeviation() {
		//public static double stdev(int[] list){
        int mean = 0;
        int numi = 0;

        mean = getClassMean();

        for (Student student : studentList) {
            numi += (student.getOverallPercent(categoryList) - mean)*(student.getOverallPercent(categoryList) - mean);
        }

        return (int)Math.sqrt(numi/studentList.size());
    }
}