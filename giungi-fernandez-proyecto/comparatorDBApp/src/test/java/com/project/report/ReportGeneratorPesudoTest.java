package com.project.report;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.project.structure.DBModel;
import com.project.structure.ForeignKey;
import com.project.structure.Index;
import com.project.structure.Table;
import com.project.report.ReportGenerator;
import com.project.structure.Column;

public class ReportGeneratorPesudoTest {

@Test
public void difrentBasicModelDBTest() {
		Table t = new Table("Persona");
		Column c1 = new Column("dni" , "int4");
		Column c2 = new Column("nombre", "varchar");
		Column c3 = new Column("apellido" , "varchar");
		ArrayList<Column> cl1 = new ArrayList<Column>();
		cl1.add(c1);
		cl1.add(c2);
		cl1.add(c3);
		ArrayList<Column> pkc = new ArrayList<Column>();
		pkc.add(c1);
		Index pk = new Index("pk_persona",pkc);
		t.setColumns(cl1);
		t.setPrimaryKey(pk);
		Table t1 = new Table("Gerente");
		Column c4 = new Column("id_gerente" , "int4");
		Column c5 = new Column("nombre", "varchar");
		Column c6 = new Column("cargo" , "varchar");
		ArrayList<Column> cl2 = new ArrayList<Column>();
		cl2.add(c4);
		cl2.add(c5);
		cl2.add(c6);
		ArrayList<Column> pkc1 = new ArrayList<Column>();
		pkc.add(c4);
		Index pk1 = new Index("pk_gerente",pkc1);
		t1.setColumns(cl2);
		t1.setPrimaryKey(pk1);
		ArrayList<Table> tblist = new ArrayList<Table>();
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist.add(t);
		tblist1.add(t1);
	
		DBModel db = new DBModel("db_version_1");
		DBModel db1 = new DBModel("db_version2");
		db.setTables(tblist);
		db1.setTables(tblist1);
		ReportGenerator.findDifferences(db , db1 , "file_test/test1.txt");
		System.out.println("END, THE FILE WAS MADE.");

	}
	
	@Test
	//Both tables are equals.
	public void sameTablesTest() {
		Table t = new Table("Persona");
		Column c1 = new Column("dni" , "int4");
		Column c2 = new Column("nombre", "varchar");
		Column c3 = new Column("apellido" , "varchar");
		ArrayList<Column> cl1 = new ArrayList<Column>();
		cl1.add(c1);
		cl1.add(c2);
		cl1.add(c3);
		ArrayList<Column> pkc = new ArrayList<Column>();
		pkc.add(c1);
		Index pk = new Index("pk_persona",pkc);
		t.setColumns(cl1);
		t.setPrimaryKey(pk);
		
		Table t1 = new Table("Persona");
		t1.setColumns(cl1);
		t1.setPrimaryKey(pk);
		ArrayList<Table> tblist = new ArrayList<Table>();
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist.add(t);
		tblist1.add(t1);
	
		DBModel db = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		db.setTables(tblist);
		db1.setTables(tblist1);
		ReportGenerator.findDifferences(db , db1 , "file_test/test2.txt");
	
	}
	
	@Test
	public void test3() {
		
		Table t = new Table("Persona");
	
		Column c1 = new Column("dni" , "int4");
		Column c2 = new Column("nombre", "varchar");
		Column c3 = new Column("apellido" , "varchar");
		ArrayList<Column> cl1 = new ArrayList<Column>();
		cl1.add(c1);
		cl1.add(c2);
		cl1.add(c3);
		ArrayList<Column> pkc = new ArrayList<Column>();
		pkc.add(c1);
		Index pk = new Index("pk_persona",pkc);
		t.setColumns(cl1);
		t.setPrimaryKey(pk);

		Table t1 = new Table("Gerente");
		Column c4 = new Column("id_gerente" , "int4");
		Column c5 = new Column("nombre", "varchar");
		Column c6 = new Column("cargo" , "varchar");
		ArrayList<Column> cl2 = new ArrayList<Column>();
		cl2.add(c4);
		cl2.add(c5);
		cl2.add(c6);
		ArrayList<Column> pkc1 = new ArrayList<Column>();
		pkc1.add(c4);
		Index pk1 = new Index("pk_gerente",pkc1);
		t1.setColumns(cl2);
		t1.setPrimaryKey(pk1);
		
		Table t2 = new Table("Maquinarias");
		Column c7 = new Column("maquinaria_id" , "int4");
		ArrayList<Column> cl3 = new ArrayList<Column>();
		cl3.add(c7);
		ArrayList<Column> pkc3 = new ArrayList<Column>();
		pkc3.add(c7);
		Index pk3 = new Index("pk_maquinaria",pkc3);
		t2.setColumns(cl3);
		t2.setPrimaryKey(pk3);
		

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
		ReportGenerator.findDifferences(db , db1 , "file_test/test3.txt");
	
	}


	@Test
	public void sameTableDiffColumnsAndDiffPKTest(){
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
		ArrayList<Column> pkc0 = new ArrayList<Column>();
		pkc0.add(c0);
		Index pk0 = new Index("pk_persona", pkc0);
		ArrayList<Column> collist1 = new ArrayList<Column>();
		collist1.add(c3);
		collist1.add(c1);
		collist1.add(c2);
		ArrayList<Column> pkc1 = new ArrayList<Column>();
		pkc1.add(c3);
		Index pk1 = new Index("pk_persona", pkc1);
		t0.setColumns(collist0);
		t0.setPrimaryKey(pk0);
		t1.setColumns(collist1);
		t1.setPrimaryKey(pk1);

		DBModel db0 = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		ReportGenerator.findDifferences(db0 , db1 , "file_test/test4.txt");
	}
	
	@Test
	public void sameTableDiffColumnsTestAndAtionalColumns(){
		Table t0 = new Table("Persona");
		Table t1 = new Table("Persona");
		Column c0 = new Column("id", "int");
		Column c1 = new Column("nombre", "varchar");
		Column c2 = new Column("apellido", "varchar");
		Column c3 = new Column("id", "varchar");
		Column c4 = new Column("telefono" , "varchar");
		Column c5 = new Column("direccion" , "varchar");
		
		ArrayList<Column> collist0 = new ArrayList<Column>();
		collist0.add(c0);
		collist0.add(c1);
		collist0.add(c2);
		collist0.add(c4);
		ArrayList<Column> pkc0 = new ArrayList<Column>();
		pkc0.add(c0);
		Index pk0 = new Index("pk_Persona",pkc0);
		ArrayList<Column> collist1 = new ArrayList<Column>();
		collist1.add(c3);
		collist1.add(c1);
		collist1.add(c2);
		collist1.add(c5);
		ArrayList<Column> pkc1 = new ArrayList<Column>();
		pkc1.add(c0);
		Index pk1 = new Index("pk_Persona",pkc1);
		t0.setColumns(collist0);
		t1.setColumns(collist1);
		t0.setPrimaryKey(pk0);
		t1.setPrimaryKey(pk1);

		DBModel db0 = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		ReportGenerator.findDifferences(db0 , db1 , "file_test/test5.txt");
	}
	
	@Test
	//Both tables are same called but has different primary Key.
	public void sameTableNamesButNotPKTest() {
		Table t = new Table("Persona");
		Column c1 = new Column("dni" , "int4");
		Column c2 = new Column("nombre", "varchar");
		Column c3 = new Column("apellido" , "varchar");
		ArrayList<Column> cl1 = new ArrayList<Column>();
		cl1.add(c1);
		cl1.add(c2);
		cl1.add(c3);
		ArrayList<Column> pkc = new ArrayList<Column>();
		pkc.add(c1);
		Index pk = new Index("pk_persona",pkc);
		t.setColumns(cl1);
		t.setPrimaryKey(pk);
		
		Table t1 = new Table("Persona");
		t1.setColumns(cl1);
		ArrayList<Column> pkc1 = new ArrayList<Column>();
		pkc1.add(c1);
		pkc1.add(c2);
		Index pk1 = new Index("pk_persona",pkc1);
		t1.setPrimaryKey(pk1);
		ArrayList<Table> tblist = new ArrayList<Table>();
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist.add(t);
		tblist1.add(t1);
	
		DBModel db = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		db.setTables(tblist);
		db1.setTables(tblist1);
		ReportGenerator.findDifferences(db , db1 , "file_test/test6.txt");
	
	}

	@Test
	public void sameTableDiffForeignKeyTest(){
		DBModel db0 = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		Table t0 = new Table("Persona");
		Table t1 = new Table("Persona");
		Column c0 = new Column("id", "int");
		Column c1 = new Column("nombre", "varchar");
		Column c2 = new Column("apellido", "varchar");
		Column c3 = new Column("direccion" , "varchar");
		
		ArrayList<Column> collist0 = new ArrayList<Column>();
		collist0.add(c0);
		collist0.add(c1);
		collist0.add(c2);
		collist0.add(c3);
		ArrayList<Column> collist1 = new ArrayList<Column>();
		collist1.add(c0);
		collist1.add(c1);
		collist1.add(c2);
		collist1.add(c3);
		t0.setColumns(collist0);
		t1.setColumns(collist1);
		ArrayList<Column> fkreflist0 = new ArrayList<Column>();
		fkreflist0.add(c0);
		fkreflist0.add(c1);
		ArrayList<Column> fkreflist1 = new ArrayList<Column>();
		fkreflist1.add(c0);
		fkreflist1.add(c3);
		
		ForeignKey fk = new ForeignKey("fk_persona1" , fkreflist0 , t0);
		ForeignKey fk1 = new ForeignKey("fk_persona2" , fkreflist1 , t1);
		
		ArrayList<ForeignKey> fklist0 = new ArrayList<ForeignKey>();
		fklist0.add(fk);
		ArrayList<ForeignKey> fklist1 = new ArrayList<ForeignKey>();
		fklist1.add(fk1);
		t0.setForeignKeys(fklist0);
		t1.setForeignKeys(fklist1);
		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);
	
		ReportGenerator.findDifferences(db0 , db1 , "file_test/diffFKColumns.txt");
	}


	@Test
	public void sameTableSameForeignKeyTest(){
		DBModel db0 = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		Table t0 = new Table("Persona");
		Table t1 = new Table("Persona");
		Column c0 = new Column("id", "int");
		Column c1 = new Column("nombre", "varchar");
		Column c2 = new Column("apellido", "varchar");
		Column c3 = new Column("direccion" , "varchar");
		
		ArrayList<Column> collist0 = new ArrayList<Column>();
		collist0.add(c0);
		collist0.add(c1);
		collist0.add(c2);
		collist0.add(c3);
		ArrayList<Column> collist1 = new ArrayList<Column>();
		collist1.add(c0);
		collist1.add(c1);
		collist1.add(c2);
		collist1.add(c3);
		t0.setColumns(collist0);
		t1.setColumns(collist1);
		ArrayList<Column> fkreflist0 = new ArrayList<Column>();
		fkreflist0.add(c1);
		fkreflist0.add(c0);
		ArrayList<Column> fkreflist1 = new ArrayList<Column>();
		fkreflist1.add(c1);
		fkreflist1.add(c0);
		
		ForeignKey fk = new ForeignKey("fk_persona1" , fkreflist0 , t0);
		ForeignKey fk1 = new ForeignKey("fk_persona2" , fkreflist1 , t1);

		ArrayList<ForeignKey> fklist0 = new ArrayList<ForeignKey>();
		fklist0.add(fk);
		ArrayList<ForeignKey> fklist1 = new ArrayList<ForeignKey>();
		fklist1.add(fk1);
		t0.setForeignKeys(fklist0);
		t1.setForeignKeys(fklist1);
		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);
	
		ReportGenerator.findDifferences(db0 , db1 , "file_test/diffFKColumns.txt");
	}

	@Test
	public void diffTableReferenceForeignKeyTest(){
		DBModel db0 = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		Table t0 = new Table("Persona");
		Table t1 = new Table("Persona");
		Table t2 = new Table("Gerente");
		Column c0 = new Column("id", "int");
		Column c1 = new Column("nombre", "varchar");
		Column c2 = new Column("apellido", "varchar");
		Column c3 = new Column("direccion" , "varchar");
		
		ArrayList<Column> collist0 = new ArrayList<Column>();
		collist0.add(c0);
		collist0.add(c1);
		collist0.add(c2);
		collist0.add(c3);
		ArrayList<Column> collist1 = new ArrayList<Column>();
		collist1.add(c0);
		collist1.add(c1);
		collist1.add(c2);
		collist1.add(c3);
		ArrayList<Column> collist2 = new ArrayList<Column>();
		collist2.add(c0);
		t0.setColumns(collist0);
		t1.setColumns(collist1);
		t2.setColumns(collist2);
		ArrayList<Column> fkreflist0 = new ArrayList<Column>();
		fkreflist0.add(c1);
		fkreflist0.add(c0);
		ArrayList<Column> fkreflist1 = new ArrayList<Column>();
		fkreflist1.add(c1);
		fkreflist1.add(c0);
		
		ForeignKey fk = new ForeignKey("fk_persona1" , fkreflist0 , t0);
		ForeignKey fk1 = new ForeignKey("fk_persona2" , fkreflist1 , t2);

		ArrayList<ForeignKey> fklist0 = new ArrayList<ForeignKey>();
		fklist0.add(fk);
		ArrayList<ForeignKey> fklist1 = new ArrayList<ForeignKey>();
		fklist1.add(fk1);
		t0.setForeignKeys(fklist0);
		t1.setForeignKeys(fklist1);
		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);
	
		ReportGenerator.findDifferences(db0 , db1 , "file_test/diffFKColumns.txt");
	}

}
