package com.project.structure;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class ParamTest {

	
	
	@Test
	public void equalsParamHasTheSameTypeAndDataType() {
		
		Param p = new Param("maxA" , 1 , "varchar");
		
		Param p1 = new Param("maxB" , 1 , "varchar");
		
		assertTrue(p.equals(p1));
	}
	
	@Test
	public void noEqualsParamHasTheSameTypeButNotDataType() {
		
		Param p = new Param("maxA" , 1 , "varchar");
		
		Param p1 = new Param("maxB" , 1 , "int4");
		
		assertFalse(p.equals(p1));
	}
	
	@Test
	public void noEqualsParamHasTheSameDataTypeButNotType() {
		
		Param p = new Param("maxA" , 1 , "varchar");
		
		Param p1 = new Param("maxB" , 3 , "int4");
		
		assertFalse(p.equals(p1));
	}
}
