package kr.ac.ajou.lazybones.templates;

import java.util.Date;

/**
 * Reservation (Actual expected time) template
 * @author AJOU
 *
 */
public class RealReservation {
	
	private String machine;
	private String subscriber;
	private Date from;
	private Date to;
	
	
	
	public RealReservation(String machine, String subscriber, Date from, Date to) {
		super();
		this.machine = machine;
		this.subscriber = subscriber;
		this.from = from;
		this.to = to;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	
	
	

}
