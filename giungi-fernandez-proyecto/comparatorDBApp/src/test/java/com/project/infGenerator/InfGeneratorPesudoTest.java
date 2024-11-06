package com.project.infGenerator;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.project.structure.DBModel;
import com.project.structure.Table;

public class InfGeneratorPesudoTest {

	@Test
	public void test1() {
		Table t = new Table("Persona");
		Table t1 = new Table("Gerente");
		//agregar columnas, cp,

		ArrayList<Table> tblist = new ArrayList<Table>();
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist.add(t);
		tblist1.add(t1);
	
		DBModel db = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		db.setTables(tblist);
		db1.setTables(tblist1);
		InfGenerator.findDifferences(db , db1 , "file_test/test1");
		System.out.println("END, THE FILE WAS MADE.");

	}
	
	@Test
	//ambas tablas son iguales no deberia mostrar nada todavia, podriamos revisar informar 
	// que son iguales
	public void test2() {
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
		InfGenerator.findDifferences(db , db1 , "file_test/test2");
		System.out.println("END, THE FILE WAS MADE.");

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
		System.out.println("END, THE FILE WAS MADE.");
	}
}
