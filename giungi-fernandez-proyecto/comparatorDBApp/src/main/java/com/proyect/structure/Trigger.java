package com.proyect.structure;

public class Trigger {
	private String name;
	private Event event;
	public Trigger(String triggerName, Event event) {
	
		this.name = triggerName;
		this.event = event;
	
	}

	public String getName() {
		
		return this.name;
	}
	
	public Event getEvent() {
		
		return this.event;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Trigger))
			return false;
		Trigger t = (Trigger) o;
		if(this.getName().compareTo(t.getName()) == 0 && this.getEvent()== t.getEvent())
			return true;
		return false;
	}
}
