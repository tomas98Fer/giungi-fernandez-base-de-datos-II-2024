package com.project.infGenerator;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.project.structure.DBModel;
import com.project.structure.Table;
import com.project.structure.Column;

public class InfGeneratorPesudoTest {

@Test
public void test1() {
		Table t = new Table("Persona");
		Table t1 = new Table("Gerente");

		ArrayList<Table> tblist = new ArrayList<Table>();
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist.add(t);
		tblist1.add(t1);
	
		DBModel db = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		db.setTables(tblist);
		db1.setTables(tblist1);
		InfGenerator.findDifferences(db , db1 , "file_test/test1.txt");
		System.out.println("END, THE FILE WAS MADE.");

	}
	
	@Test
	//Both tables are equals.
	public void sameTablesTest() {
		Table t = new Table("Persona");
		Table t1 = new Table("Persona");

		ArrayList<Table> tblist = new ArrayList<Table>();
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist.add(t);
		tblist1.add(t1);
	
		DBModel db = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		db.setTables(tblist);
		db1.setTables(tblist1);
		InfGenerator.findDifferences(db , db1 , "file_test/test2.txt");
	
	}
	
	@Test
	public void test3() {
		
		Table t = new Table("Persona");
		Table t1 = new Table("Gerente");
		Table t2 = new Table("Maquinarias");
		

		ArrayList<Table> tblist = new ArrayList<Table>();
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist.add(t);
		tblist1.add(t);
		tblist1.add(t1);
		tblist1.add(t2);
	
		DBModel db = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		db.setTables(tblist);
		db1.setTables(tblist1);
		InfGenerator.findDifferences(db , db1 , "file_test/test3.txt");
	
	}


	@Test
	public void sameTableDiffColumnsTest(){
		Table t0 = new Table("Persona");
		Table t1 = new Table("Persona");
		Column c0 = new Column("id", "int");
		Column c1 = new Column("nombre", "varchar");
		Column c2 = new Column("apellido", "varchar");
		Column c3 = new Column("id", "varchar");
		
		ArrayList<Column> collist0 = new ArrayList<Column>();
		collist0.add(c0);
		collist0.add(c1);
		collist0.add(c2);
		ArrayList<Column> collist1 = new ArrayList<Column>();
		collist1.add(c3);
		collist1.add(c1);
		collist1.add(c2);
		t0.setColumns(collist0);
		t1.setColumns(collist1);

		DBModel db0 = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		InfGenerator.findDifferences(db0 , db1 , "file_test/test4.txt");
	}

}
