
public class Gradable {
	private String name;
	private int weight;
	private int total;
	private String type;
	private int pointsLost;
	private String note;
	
	public Gradable() {
		
	}
	
	public Gradable(String name, int weight, int total, String type) {
		this.name = name;
		this.weight = weight;
		this.total = total;
		this.type = type;
	}
		
	public Gradable(String name, int weight, int total, String type,int pointsLost,String note) {
		this.name = name;
		this.weight = weight;
		this.total = total;
		this.type = type;
		this.pointsLost = pointsLost;
		this.note = note;
	}
		
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPointsLost(int pointsLost) {
		this.pointsLost = pointsLost;
	}
	
	public int getPointsLost() {
		return pointsLost;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getPoints() {
		return total;
	}
	
	public String getType() {
		return type;
	}

	public String getNote() {
		return note;
	}

	
	public String toString() {
		return name;
	}

	
	public boolean is(String type) {
		return this.type.equals(type);
	}	
	
}