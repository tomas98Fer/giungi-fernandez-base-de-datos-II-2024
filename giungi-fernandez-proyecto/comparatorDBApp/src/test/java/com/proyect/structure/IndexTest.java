package com.proyect.structure;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class IndexTest {


	
	@Test
	public void indexHasAname() {
		String name = "Persona";
		Index idx = new Index(name);
		assertTrue(idx.getName().equals(name));
	}
	
	/**
	 * Two Index are equals iff both and have the same number of columns and 
	 * the same Type columns order.
	 */
	@ParameterizedTest(name = "{index}: {1} and {0} are equals {2}")
	@MethodSource("findLastArgsProvider")
	public void equalIndex(Index input1, Index input2, boolean expectedResult) {
		
		assertTrue(input1.equals(input2) == expectedResult);
	}
	
	@Test
	public void compareIndexandOtherObject() {
		Column c0 = new Column("direccion","VARCHAR");
		Column c1 = new Column("telefono","VARCHAR");
		Column c2 = new Column("DNI","INTEGER");
		ArrayList<Column> l1 = new ArrayList<>();
		
		l1.add(c0);l1.add(c1);l1.add(c2);
		assertFalse(new Index("Empleado_idx",l1).equals(new Table("Empleado")));
	}
	
	
	static Stream<Arguments> findLastArgsProvider() {
		Column c0 = new Column("direccion","VARCHAR");
		Column c1 = new Column("telefono","VARCHAR");
		Column c2 = new Column("DNI","INTEGER");
		ArrayList<Column> l1 = new ArrayList<>();
		ArrayList<Column> l2 = new ArrayList<>();
		ArrayList<Column> l3 = new ArrayList<>();

		l1.add(c0);l1.add(c1);l1.add(c2);
		
		l2.add(c0);l2.add(c1);l2.add(c1);

		l3.add(c0);
		return Stream.of(
		Arguments.of(new Index("Cliente_idx",l1),new Index("Cliente_idx",l1), true ),
		Arguments.of(new Index("Cliente_idx",l1),new Index("Cliente_idx",l2), false ),
		Arguments.of(new Index("Cliente_idx",l1),new Index("Cliente_idx",l3), false),
		Arguments.of(new Index("Empleado_idx",l1),new Index("Cliente_idx",l1), true)
		);
		}
	
}
