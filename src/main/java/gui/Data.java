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
	private int classCurve;


	public Data() {
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
		classCurve = ClassService.getCurve();
	}
	
	public void setLoadedClass(String lc) {
		LoadedClass = lc;
		Globals.setClassId(ClassService.getId(LoadedClass));
	}
	
	public String getLoadedClass() {
		return LoadedClass;
	}
		
    public ArrayList<Category> getCategories(){
	    return this.categoryList;
    }
		
	public ArrayList<Category> copyCategories() {
		return new ArrayList<Category>(categoryList);
	}
	
	public void setCategories(ArrayList<Category> gt) {
		categoryList = new ArrayList<Category>(gt);
	}
	
	public void addCategory(Category gt) {
		categoryList.add(gt);
		this.addSaveCommand(CategoryService.insert(gt));
	}
	
	public void dropCategory(String gt) {
		for(int i=0; i<categoryList.size(); i++) {
			if(categoryList.get(i).getType().equals(gt)){
				for(int j=0; j<gradableList.size(); j++){
					if (gradableList.get(j).isType(categoryList.get(i).getType())) {
						dropGradable(gradableList.get(j));
						j-=1;
					}
				}
				categoryList.remove(i);
			}
		}
		
		this.addSaveCommand(CategoryService.drop(gt));
	}

	public void setGradables(ArrayList<Gradable> gl) {
		gradableList = new ArrayList<Gradable>(gl);
	}
		
	public ArrayList<Gradable> getGradables() {
		return gradableList;
	}

	public ArrayList<Gradable> copyGradables() {
		return new ArrayList<Gradable>(gradableList);
	}
	
	public int nGradables() {
		return gradableList.size();
	}
			
	public Gradable getGradable(int i) {
		return gradableList.get(i);
	}
	
	public Gradable getGradable(String name) {
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
		this.addSaveCommand(GradableService.insert(newGradable));
		
		for(Student student : studentList){
			student.addGrade(newGradable);
			this.addSaveCommand(GradeService.insert(student.getGrade(newGradable.getName()),student));
		}
							
	}
	
	public void dropGradable(Gradable g) {
	    this.addSaveCommand(GradableService.drop(g));
		this.addSaveCommand(GradeService.drop(g));
		
		for (Student student : studentList) {
			student.dropGrade(g);
		}
					
		gradableList.remove(g);
		// for (int i = 0; i<gradableList.size(); i++) {
			// if(gradableList.get(i).getName().equals(g.getName())) {
				// gradableList.remove(gradableList.get(i));
			// }
		// }
	}
		
	public Student getStudent(int i) {
		return studentList.get(i);
	}
	
	public Student getStudent(String name) {
		Student s = new Student();
		for (int i = 0; i<studentList.size(); i++) {
			if(studentList.get(i).getName().equals(name)) {
				s =  studentList.get(i);
			}
		}
		return s;
	}
	
	public void addStudent(Student newStudent) {
		studentList.add(newStudent);
		if(!StudentService.studentInDb(newStudent)){
			this.addSaveCommand(StudentService.insertStudent(newStudent));
		}
		
        this.addSaveCommand(StudentClassService.insertStudentClass(newStudent.getSchoolID()));

		for(Gradable gradable : gradableList) {
			newStudent.addGrade(gradable);
			this.addSaveCommand(GradeService.insert(newStudent.getGrade(gradable.getName()),newStudent));
		}
	}
	
	public void dropStudent(Student s) {
	    studentList.remove(s);
        this.addSaveCommand(StudentClassService.deleteStudentClass(s.getSchoolID()));
		this.addSaveCommand(GradeService.dropStudentGrades(s.getSchoolID()));
	}

	public int nStudents() {
		return studentList.size();
	}
	
	public ArrayList<String> studentTypes() {
		return studentTypes;
	}
		
	public void setCurve(int c) {
		classCurve = c;
		this.addSaveCommand(ClassService.setCurve(c));
	}
	
	public int getCurve() {
		return classCurve;
	}
	
	public void loadCategories(){
	    this.categoryList = CategoryService.getAll(Globals.class_id());
    }

	public void loadGradables(){
		this.gradableList = GradableService.getAll(Globals.class_id());
		for (Gradable gradable : gradableList) {
			gradable.setType(findCategory(gradable.getType()));
		}
    }
	
	public void loadStudents(){
        List<String>studentIds = StudentClassService.getAllStudentsId(Globals.class_id());

		this.studentList.clear();
        for (String id : studentIds) {
            studentList.add(StudentService.getStudentById(id));
        }

        for (Student student : studentList) {
            student.setGradeList(GradeService.getAllGradablesForStudent(student));
			for (Grade grade : student.getGradeList()) {
				grade.setGradable(findGradable(grade.getGradable()));
			}
        }
    }
		
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

    private Gradable findGradable(Gradable g) {
		for (Gradable gradable : gradableList) {
			if (gradable.getName().equals(g.getName())) {
				return gradable;
			}
		}
		return null;
	}
	
    private Category findCategory(Category c) {
		for (Category category : categoryList) {
			if (category.getType().equals(c.getType())) {
				return category;
			}
		}
		return null;
	}
	
	public ArrayList<String> getStudentTypes() {
		return studentTypes;
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
	
	public boolean needSave() {
		if(saveCommand.size()>0) {
			return true;
		}else{
			return false;
		}
	}

	public int getClassMean() {
		if(studentList.size() == 0) {
			return 0;
		} else {
			int mean = 0;
			for (Student student : studentList) {
				mean += student.getOverallPercent(categoryList);
			}
			return mean/studentList.size()+classCurve;
		}
	}
	
	public int getClassMedian() {
		if(studentList.size() == 0) {
			return 0; 
		}else{
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
			
			return median+classCurve;
		}
	}
	
	public int getClassStandardDeviation() {
		if(studentList.size() == 0) {
			return 0;
		} else {
			int mean = 0;
			int numi = 0;

			mean = getClassMean()-classCurve;

			for (Student student : studentList) {
				numi += (student.getOverallPercent(categoryList) - mean)*(student.getOverallPercent(categoryList) - mean);
			}
		
			return (int)Math.sqrt(numi/studentList.size());
		}
    }

	public int getGradableAverage(Gradable g) {
		if(studentList.size() == 0) {
			return 0;
		}else{
			int sum = 0;
			int total = 0;
			for(Student s : studentList) {
				Grade gtmp = s.getGrade(g.getName());
				sum += gtmp.getPointsLost();
				total += gtmp.getPoints();
			}
			
			if(total == 0){
				return 0;
			}else{
			return (total-sum)*100/total;
			}
		}
	}
	
}