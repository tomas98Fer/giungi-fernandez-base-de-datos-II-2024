package com.proyect.structure;

public class Trigger extends MetaObjectDB {

	private Event event;
	
	/**
	 * 
	 * @param triggerName
	 * @param event
	 */
	public Trigger(String triggerName, Event event) {
		super(triggerName);
		this.event = event;
	
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
		if(this.getName().compareTo(t.getName()) == 0 && this.getEvent()== t.getEvent())
			return true;
		return false;
	}
}
