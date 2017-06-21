package utils;

public class ParkingTrentoRovereto {
	String name;
	String description;
	int total;
	int available;
	Boolean monitored;
	public ParkingTrentoRovereto(String name, String description, int total, int available, Boolean monitored) {
		super();
		this.name = name;
		this.description = description;
		this.total = total;
		this.available = available;
		this.monitored = monitored;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public Boolean getMonitored() {
		return monitored;
	}
	public void setMonitored(Boolean monitored) {
		this.monitored = monitored;
	}
	
	

}
