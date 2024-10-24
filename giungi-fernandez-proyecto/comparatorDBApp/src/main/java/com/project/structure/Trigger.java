package com.project.structure;

public class Trigger extends MetaObjectDB {
	
	private TimeEvent time;
	private Event event;

	
	/**
	 * @param time
	 * @param triggerName
	 * @param event
	 */
	public Trigger(String triggerName,TimeEvent time, Event event) {
		super(triggerName);
		this.event = event;
		this.time = time;
	
	}
	
	
	public Trigger(String triggerName, String time, String event) {
		super(triggerName);
		if(time == null || time.isEmpty())
			throw new IllegalArgumentException("Invalid Argument : time");
		if(event == null || event.isEmpty())
			throw new IllegalArgumentException("Invalid Argument : event");
		if(!isATimeEvent(time))
			throw new IllegalArgumentException("Invalid Argument : event");
			
		if(!isAnEvent(event))
			throw new IllegalArgumentException("Invalid Argument : event");
		this.time =  convertStringtoTime(time);
		this.event = convertStringtoEvent(event);
	}
	
	/**
	 * 
	 * @return time when trigger is start.
	 */
	public TimeEvent getTime() {
		return this.time;
	}

	/**
	 * 
	 * @return kind of trigger event.
	 */
	public Event getEvent() {
		
		return this.event;
	}
	
	@Override
	/**
	 * Comparator trigger method.
	 * 
	 */
	public boolean equals(Object o) {
		if(!(o instanceof Trigger))
			return false;
		Trigger t = (Trigger) o;
		if(this.name.equals(t.getName()) && this.time == t.getTime() &&	this.event == t.getEvent())
			return true;
		
		return false;
	}
	
	private boolean isAnEvent(String event) {
		
		return ( event.equals("INSERT") || event.equals("DELETE") || event.equals("UPDATE") || event.equals("TRUNCATE") );
			
	} 
	
	@Override
	public String toString() {
		return "[TRIGGER NAME: " + this.getName() + " , TIME EVENT: " + this.time  + " , EVENT: " + this.event +  "]\n";
	}
	
	
	private boolean isATimeEvent(String time) {
		
		return ( time.equals("AFTER") || time.equals("BEFORE") );
	}

	private TimeEvent convertStringtoTime(String time) {
		switch(time) {
			case "AFTER" :
				return TimeEvent.AFTER;
			case "BEFORE" : 
				return TimeEvent.BEFORE;
		}
		return null;
	}

	
	private Event convertStringtoEvent(String event) {
		switch(event) {
			case "INSERT" :
				return Event.INSERT;
			case "DELETE" : 
				return Event.DELETE;
			case "UPDATE" : 
				return Event.UPDATE;
			case "TRUNCATE" : 
				return Event.TRUNCATE;
		}
		return null;
	}


}
