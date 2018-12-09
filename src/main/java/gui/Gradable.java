package gui;

public class Gradable {
	private String name;
	private int total;
	private Category type;
	private int IntraCategoryWeight;
	
	public Gradable() {
	}
	
	public Gradable(String name, int total, Category type, int IntraCategoryWeight) {
		this.name = name;
		this.total = total;
		this.type = type;
		this.IntraCategoryWeight = 100;
	}
		
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getTotal() {
		return total;
	}

	public void setType(Category type) {
		this.type = type;
	}

	public Category getType() {
		return type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setIntraCategoryWeight(int weight) {
		IntraCategoryWeight = weight;
	}	
	
	public int getIntraCategoryWeight() {
		return IntraCategoryWeight;
	}
	
	public void setPoints(int p) {
		total = p;
	}

	public int getPoints() {
		return total;
	}
	
	public int getCategoryWeight(String studentType) {
		return type.getWeight(studentType);
	}
	
	public String toString() {
		return name;
	}
	
	public boolean isType(String type) {
		return this.type.getType().equals(type);
	}	
	
	public Gradable copy() {
		Gradable g = new Gradable(this.getName(),
									this.getPoints(),
									this.getType(),
									this.getIntraCategoryWeight());
		return g;
	}
}