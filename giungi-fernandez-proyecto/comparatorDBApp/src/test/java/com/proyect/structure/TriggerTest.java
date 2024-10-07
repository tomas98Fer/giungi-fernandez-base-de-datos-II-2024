package com.proyect.structure;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TriggerTest {

	/*A Trigger has a name and an event. an event can be: UPDATE,INSERT,DELETE,TRUNCATE*/
	
	@Test
	public void triggerHasANameAndAnEvent() {
		String triggerName = "incre_trigg";
		Event event = Event.UPDATE;
		Trigger t = new Trigger(triggerName,event);
		
		assertTrue(t.getName().compareTo(triggerName) == 0 && t.getEvent() == event);
	}
	
	
	
}
