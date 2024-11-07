package com.project.structure;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class TriggerTest {

	/*A Trigger has a name and an event. an event can be: UPDATE,INSERT,DELETE,TRUNCATE*/
	
	@Test
	public void triggerHasANameATimeAndAnEvent() {
		String triggerName = "incre_trigg";
		TimeEvent time = TimeEvent.AFTER;
		Event event = Event.UPDATE;
		Trigger t = new Trigger(triggerName,time,event);
		
		assertTrue(t.getName().equals((triggerName)) && t.getEvent() == event);
	}
	
	@Test
	public void equalTriggers() {
		
		MetaObjectDB t1 = new Trigger("incre_trig" , TimeEvent.AFTER , Event.INSERT);
		MetaObjectDB t2 = new Trigger("incre_trig", TimeEvent.AFTER , Event.INSERT);
		
		assertTrue(t1.equals(t2));
			
	}
	
	@Test
	public void notequalTriggers() {
		
		MetaObjectDB t1 = new Trigger("incre_trig" , TimeEvent.BEFORE , Event.INSERT);
		MetaObjectDB t2 = new Trigger("incre_trig", TimeEvent.AFTER , Event.INSERT);
		
		assertFalse(t1.equals(t2));
			
	}
	
}
