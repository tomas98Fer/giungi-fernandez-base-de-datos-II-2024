package com.proyect.structure;
import static org.junit.Assert.assertTrue;

import org.junit.Test;




/**
 * Unit test for simple App.
 */

public class ColumnTest {

	/*A column has a name and a type*/
	@Test
	public void columnNameAndTypeTest() {
		String myName = "numb_costumer";
		String type = "VARCHAR";
		Column myColumn = new Column(myName,type);
		assertTrue(myColumn.getName().compareTo(myName)== 0 && myColumn.getType().compareTo(type)== 0);
		
	}
	
	/* The name and type of column can not be empty or null
	 * */
	
	@Test(expected = IllegalArgumentException.class)
	public void noEmptyNameOrTypeinAColumn() {
		String myName = "";
		String type = "";
		new Column(myName,type);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noNullNameOrTypeinAColumn() {
		String myName = null;
		String type = null;
		new Column(myName,type);
	}
	
	
	
}
