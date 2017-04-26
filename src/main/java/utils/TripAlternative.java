package utils;

public class TripAlternative {
	
	

	public TripAlternative(String mean, Long price, Long duration, Long distance, Integer number_changes) {
		super();
		this.mean = mean;
		this.price = price;
		this.duration = duration;
		this.distance = distance;
		this.number_changes = number_changes;
	}
	private String mean;
	private Long price;
	private Long duration;
	private Long distance;
	private Integer number_changes;
	
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
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Long getDistance() {
		return distance;
	}
	public void setDistance(Long distance) {
		this.distance = distance;
	}
}
