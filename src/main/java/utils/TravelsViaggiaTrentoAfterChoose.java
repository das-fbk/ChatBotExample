package utils;

public class TravelsViaggiaTrentoAfterChoose {
	
	String from;
	String to;
	String busNumber;
	String hours;
	public TravelsViaggiaTrentoAfterChoose(String from, String to, String busNumber,String hours) {
		super();
		this.from = from;
		this.to = to;
		this.hours = hours;
		this.busNumber = busNumber;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getBusNumber() {
		return busNumber;
	}
	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}
	
	

}
