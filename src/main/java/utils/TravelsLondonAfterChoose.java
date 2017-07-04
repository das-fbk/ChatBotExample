package utils;

public class TravelsLondonAfterChoose {
	private String start;
	private String arrive;
	private Integer duration; 
	private String vehicle;
	private String direction;
	private Integer numberBus;
	private String position; 
	
	public TravelsLondonAfterChoose(String start, String arrive, Integer duration,
			String vehicle, String direction, Integer numberBus, String position) {
		super();
		this.start = start;
		this.arrive = arrive;
		this.duration = duration;
		this.vehicle = vehicle;
		this.direction = direction;
		this.numberBus = numberBus;
		this.position = position;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getArrive() {
		return arrive;
	}
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Integer getNumberBus() {
		return numberBus;
	}
	public void setNumberBus(Integer numberBus) {
		this.numberBus = numberBus;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}

