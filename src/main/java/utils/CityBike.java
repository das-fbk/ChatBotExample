package utils;

public class CityBike {
	int empty;
	int free;
	String street;
	String name;
	public CityBike(int empty, int free, String street, String name) {
		super();
		this.empty = empty;
		this.free = free;
		this.street = street;
		this.name = name;
	}
	public int getEmpty() {
		return empty;
	}
	public void setEmpty(int empty) {
		this.empty = empty;
	}
	public int getFree() {
		return free;
	}
	public void setFree(int free) {
		this.free = free;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
