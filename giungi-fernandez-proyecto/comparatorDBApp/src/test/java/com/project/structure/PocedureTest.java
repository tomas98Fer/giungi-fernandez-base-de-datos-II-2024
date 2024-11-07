package com.project.structure;

import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PocedureTest {
	
	
	/**
	 * Two Procedure are equals iff both and have the same name, same type return and and 
	 * the same param type order.
	 */
	@ParameterizedTest
	@MethodSource("procedureEqualsProvider")
	public void equalProcedure(Procedure input1, Procedure input2, boolean expectedResult) {
		
		assertTrue(input1.equals(input2) == expectedResult);
	}
	
	
	static Stream<Arguments> procedureEqualsProvider() {
		Param p0 = new Param("sideA" , 2 , "void");
		Param p1 = new Param("sideB" , 1 , "void");
		Param p2 = new Param("sideC", 2 , "int4") ;
		Param p3 = new Param("Vector", 3 , "int4") ;
		Param p5 = new Param("Vector", 1 , "varchar") ;
		ArrayList<Param> lp1 = new ArrayList<>();
		ArrayList<Param> lp2 = new ArrayList<>();
		ArrayList<Param> lp3 = new ArrayList<>();
		ArrayList<Param> lp4 = new ArrayList<>();
		ArrayList<Param> lp5 = new ArrayList<>();
	

		lp1.add(p0);lp1.add(p2);lp1.add(p3);
		
		lp2.add(p0);lp2.add(p2);lp2.add(p3);

		lp4.add(p0);lp4.add(p3);
		lp5.add(p1);lp5.add(p5);
		
		
		return Stream.of(
				
		Arguments.of(new Procedure("area","void",lp1) , new Procedure("area","void",lp2) , true ),
		Arguments.of(new Procedure("area","void",lp1) , new Procedure("length","void",lp2) , false),
		Arguments.of(new Procedure("area","int4",lp1) , new Procedure("area","float",lp2) , false),
		Arguments.of(new Procedure("area","void",lp4) , new Procedure("area","void",lp3) , false),
		Arguments.of(new Procedure("area","void",lp3) , new Procedure("area","void",lp1) , false ),
		Arguments.of( new Procedure("area","void",lp1) , new Procedure("area","void",lp4) , false),
		Arguments.of(new Procedure("add","void",lp4) , new Procedure("add","void",lp5) , false)
		
		);
	}


}
