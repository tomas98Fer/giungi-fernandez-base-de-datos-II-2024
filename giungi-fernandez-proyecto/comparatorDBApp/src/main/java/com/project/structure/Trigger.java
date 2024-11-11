package com.project.structure;

public class Trigger extends MetaObjectDB {
	
	private String timeEvent;
	private String actionsEvent;

	
	/**
	 * @param time
	 * @param triggerName
	 * @param event
	 */
	
	public Trigger(String triggerName, String timeEvent, String actionsEvent) {
		super(triggerName);
		if(timeEvent == null || timeEvent.isEmpty())
			throw new IllegalArgumentException("Invalid Argument : time");
		if(actionsEvent == null || actionsEvent.isEmpty())
			throw new IllegalArgumentException("Invalid Argument : event");
		
		this.timeEvent = timeEvent;
		this.actionsEvent = actionsEvent;
		

	}
	
	/**
	 * 
	 * @return time when trigger is start.
	 */
	public String getTime() {
		return this.timeEvent;
	}

	/**
	 * 
	 * @return kind of trigger event.
	 */
	public String getEvent() {
		
		return this.actionsEvent;
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
		boolean same_trigName = false;
		same_trigName = this.getName().equals(t.getName());
		if(same_trigName) {
			same_trigName = this.getTime().equals(t.getTime());
			if(same_trigName) {
				same_trigName = this.getEvent().equals(t.getEvent());
				return same_trigName;
					
			}
		}
		return false;
	}
	

	
	@Override
	public String toString() {
		return "[TRIGGER NAME: " + this.getName() + " , TIME EVENT: " + this.timeEvent + " , EVENT: " + this.actionsEvent +  "]\n";
	}
	


}
