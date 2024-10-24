package com.project.structure;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.project.structure.Column;
import com.project.structure.Table;

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
	
	@Test
	public void noEmptyColumnNameTest() {
		String myName = "";
		assertThrows(IllegalArgumentException.class, () -> {
			new Column(myName,"VARCHAR");
		});
	}

	@Test
	public void noEmptyTypeTest() {
		String type = "";
		assertThrows(IllegalArgumentException.class, () -> {
			new Column("name_costumer",type);
		});
	}
	
	@Test
	public void noNullNameOfColumnTest() {
		String myName = null;
		assertThrows(IllegalArgumentException.class, () -> {
			new Column(myName,"VARCHAR");
		});
	}

	@Test
	public void noNullTypeOfColumnTest() {
		String type = null;
		assertThrows(IllegalArgumentException.class, () -> {
			new Column("name_costumer",type);
		});
	}


	
	
	@Test
	/*Check weather two columns are equals
	 * */
	public void equalsColumns() {
		
		Column c1 = new Column("name_costumer","VARCHAR");
		Column c2 = new Column("name_costumer","VARCHAR");
		assertTrue(c1.equals(c2));
	
	}
	
	@Test
	/*Check weather two columns are not equals
	 * */
	public void noEqualsColumns() {
		
		Column c1 = new Column("name_costumer","VARCHAR");
		Column c2 = new Column("name_bank","VARCHAR");
		assertFalse(c1.equals(c2));
	
	}
	
	@Test
	/*Check weather if a table is equal with a column.
	 * */
	public void anyObjectAreNotEqualwithColumns() {
		
		Column c = new Column("name_costumer","VARCHAR");
		Table t = new Table("name_bank");
		assertFalse(c.equals(t));
	
	}
	
}
