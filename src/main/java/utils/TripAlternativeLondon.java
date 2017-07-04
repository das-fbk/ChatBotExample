package utils;

import java.util.Comparator;

import com.google.common.base.CharMatcher;

public class TripAlternativeLondon {
	private String mean;
	private Integer duration;
	private Integer number_changes;
	public TripAlternativeLondon(String mean, Integer duration, Integer number_changes) {
		super();
		this.mean = mean;
		this.duration = duration;
		this.number_changes = number_changes;
	}
	public String getMean() {
		return mean;
	}
	public void setMean(String mean) {
		this.mean = mean;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getNumber_changes() {
		return number_changes;
	}
	public void setNumber_changes(Integer number_changes) {
		this.number_changes = number_changes;
	}
	
	public static Comparator <TripAlternativeLondon> timeComparator = new Comparator<TripAlternativeLondon>() {

        public int compare(TripAlternativeLondon t1, TripAlternativeLondon t2) {
        	
            int travel1 = t1.getDuration();
            int travle2 = t2.getDuration();

            return travel1-travle2;
        }
    };
    
    public static Comparator <TripAlternativeLondon> changesComparator = new Comparator<TripAlternativeLondon>() {

        public int compare(TripAlternativeLondon t1, TripAlternativeLondon t2) {
        	
            int travel1 = Integer.parseInt(CharMatcher.DIGIT.retainFrom(t1.getNumber_changes().toString()));
            int travle2 = Integer.parseInt(CharMatcher.DIGIT.retainFrom(t2.getNumber_changes().toString()));

            return travel1-travle2;
        }
    };
	
}
