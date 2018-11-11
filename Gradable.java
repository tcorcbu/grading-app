
public class Gradable {
	private String name;
	private int total;
	private GradableType type;
	private int pointsLost;
	private int weight;
	private String note;
	
	public Gradable() {
		
	}
	
	public Gradable(String name, int total, GradableType type,int weight) {
		this.name = name;
		this.total = total;
		this.type = type;
		this.weight = weight;
	}
		
	public Gradable(String name, int total, GradableType type,int weight,int pointsLost,String note) {
		this.name = name;
		this.total = total;
		this.type = type;
		this.weight = weight;
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
	
	public int getCategoryWeight() {
		return type.getWeight();
	}
	
	public int getGradableWeight() {
		return weight;
	}
	
	public int getPoints() {
		return total;
	}
	
	public GradableType getType() {
		return type;
	}

	public String getNote() {
		return note;
	}

	
	public String toString() {
		return name;
	}

	public int getPercentage() {
		return (total-pointsLost)*100/total;
	}
	
	public boolean isType(String type) {
		return this.type.getType().equals(type);
	}	
	
}