package utils;

public class TravelsFlixbus_EmtMalaga {
	private int position;
	private String from;
	private String to;
	private String route;
	private String headsign;
	private String departure_time;
	private String departure_stop_name;
	private String arrival_time;
	private String arrival_stop_name;
	public TravelsFlixbus_EmtMalaga(int position, String from, String to, String route, String headsign, String departure_time,
			String departure_stop_name, String arrival_time, String arrival_stop_name) {
		super();
		this.position = position;
		this.from = from;
		this.to = to;
		this.route = route;
		this.headsign = headsign;
		this.departure_time = departure_time;
		this.departure_stop_name = departure_stop_name;
		this.arrival_time = arrival_time;
		this.arrival_stop_name = arrival_stop_name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
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
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getHeadsign() {
		return headsign;
	}
	public void setHeadsign(String headsign) {
		this.headsign = headsign;
	}
	public String getDeparture_time() {
		return departure_time;
	}
	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}
	public String getDeparture_stop_name() {
		return departure_stop_name;
	}
	public void setDeparture_stop_name(String departure_stop_name) {
		this.departure_stop_name = departure_stop_name;
	}
	public String getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}
	public String getArrival_stop_name() {
		return arrival_stop_name;
	}
	public void setArrival_stop_name(String arrival_stop_name) {
		this.arrival_stop_name = arrival_stop_name;
	}
	
	
	

}
