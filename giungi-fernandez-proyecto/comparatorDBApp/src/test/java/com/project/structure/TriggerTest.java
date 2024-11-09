package com.project.structure;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class TriggerTest {

	/*
	 * A Trigger has a name and an timeevent. an actionsevent.
	 * */
	
	 @Test
	 public void triggerHasANameATimeAndAnEvent() {
		String name = "incre_trigg";
		String time = "AFTER";
		String event = "INSERT";
		Trigger t = new Trigger("incre_trigg" ,"AFTER" , "INSERT" );
		boolean result = true;
		result = result && t.getName().equals(name);
		result = result && t.getTime().equals(time);
		result = result && t.getEvent().equals(event);
		assertTrue(result);
	}
	 /*
	  *Two triggers are equals if _
	  *both has the same name,
	  *both has the same time event and
	  *both has the same actions event.  
	  */
	 @Test
	 public void equalTriggers() {
		Trigger t = new Trigger("incre_trigg" ,"AFTER" , "INSERT" );
		Trigger t1 = new Trigger("incre_trigg" ,"AFTER" , "INSERT" );
		assertTrue(t.equals(t1));
			
	}
	
	 @Test
	 public void notequalTriggersDiffActionEvent() {
		
		Trigger t = new Trigger("incre_trigg" ,"AFTER" , "INSERT" );
		Trigger t1 = new Trigger("incre_trigg" ,"AFTER" , "UPDATE" );
		
		assertFalse(t.equals(t1));
			
	}

	 @Test
	 public void notequalTriggersDiffTimeEvent() {
		
		Trigger t = new Trigger("incre_trigg" ,"AFTER" , "INSERT" );
		Trigger t1 = new Trigger("incre_trigg" ,"BEFORE" , "INSERT" );
		
		assertFalse(t.equals(t1));
			
	}
	 
	 @Test
	 public void notequalTriggersDiffName() {
		
		Trigger t = new Trigger("incre_trigg" ,"AFTER" , "INSERT" );
		Trigger t1 = new Trigger("decre_trigg" ,"AFTER" , "INSERT" );
		
		assertFalse(t.equals(t1));
			
	}



}