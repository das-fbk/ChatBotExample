package utils;

public class TripAlternativeRome2Rio {
	
	private String mean;
	private Integer price;
	private Double duration;
	private Integer distance;
	private Integer number_changes;

	public TripAlternativeRome2Rio(String mean, Integer price, Double duration, Integer distance, Integer number_changes) {
		super();
		this.mean = mean;
		this.price = price;
		this.duration = duration;
		this.distance = distance;
		this.number_changes = number_changes;
	}
	
	
	public Integer getNumber_changes() {
		return number_changes;
	}
	public void setNumber_changes(Integer number_changes) {
		this.number_changes = number_changes;
	}
	public String getMean() {
		return mean;
	}
	public void setMean(String mean) {
		this.mean = mean;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
}
