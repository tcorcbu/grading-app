package db;

public class Globals {
	private static int class_id;
	
	public static void setClassId(int id) {
		class_id = id;
	}
	
	public static int class_id() {
		return class_id;
	}
	
}