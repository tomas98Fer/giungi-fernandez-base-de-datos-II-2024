package com.project.report;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.project.structure.DBModel;
import com.project.structure.ForeignKey;
import com.project.structure.Index;
import com.project.structure.Param;
import com.project.structure.Procedure;
import com.project.structure.Table;
import com.project.structure.Trigger;
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
	
		ReportGenerator.findDifferences(db0 , db1 , "file_test/sameFK.txt");
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

	@Test
	public void sameTableDiffColumnsOrderIdxTest(){
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
		ArrayList<Column> idxColList0 = new ArrayList<Column>();
		idxColList0.add(c0);
		idxColList0.add(c1);
		Index index0 = new Index("idx_persona_id_name",idxColList0);
		ArrayList<Column> idxColList1 = new ArrayList<Column>();
		idxColList1.add(c1);
		idxColList1.add(c0);
		Index index1 = new Index("idx_persona_id_name",idxColList1);
		ArrayList<Index> idxList0 = new ArrayList<Index>();
		idxList0.add(index0);
		ArrayList<Index> idxList1 = new ArrayList<Index>();
		idxList1.add(index1);
		t0.setIndexes(idxList0);
		t1.setIndexes(idxList1);
		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		ReportGenerator.findDifferences(db0 , db1 , "file_test/diffIdxOrderColumns.txt");
	
	}

	@Test
	public void sameTableDiffColumnsIdxTest(){
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
		ArrayList<Column> idxColList0 = new ArrayList<Column>();
		idxColList0.add(c0);
		idxColList0.add(c1);
		Index index0 = new Index("idx_persona_id_name",idxColList0);
		ArrayList<Column> idxColList1 = new ArrayList<Column>();
		idxColList1.add(c0);
		idxColList1.add(c2);
		Index index1 = new Index("idx_persona_id_name",idxColList1);
		ArrayList<Index> idxList0 = new ArrayList<Index>();
		idxList0.add(index0);
		ArrayList<Index> idxList1 = new ArrayList<Index>();
		idxList1.add(index1);
		t0.setIndexes(idxList0);
		t1.setIndexes(idxList1);
		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		ReportGenerator.findDifferences(db0 , db1 , "file_test/diffIdxColumns.txt");
	
	}


	@Test
	public void sameTableSameIdxTest(){
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
		ArrayList<Column> idxColList0 = new ArrayList<Column>();
		idxColList0.add(c0);
		idxColList0.add(c1);
		Index index0 = new Index("UK_persona_id_name",idxColList0);
		ArrayList<Column> idxColList1 = new ArrayList<Column>();
		idxColList1.add(c0);
		idxColList1.add(c1);
		Index index1 = new Index("UK_persona_id_name",idxColList1);
		ArrayList<Index> idxList0 = new ArrayList<Index>();
		idxList0.add(index0);
		ArrayList<Index> idxList1 = new ArrayList<Index>();
		idxList1.add(index1);
		t0.setIndexes(idxList0);
		t1.setIndexes(idxList1);
		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		ReportGenerator.findDifferences(db0 , db1 , "file_test/SameIdxColumns.txt");
	
	}

	@Test
	public void sameTableDiffColumnsUKTest(){
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
		ArrayList<Column> idxColList0 = new ArrayList<Column>();
		idxColList0.add(c0);
		idxColList0.add(c1);
		Index index0 = new Index("UK_persona_id_name",idxColList0);
		ArrayList<Column> idxColList1 = new ArrayList<Column>();
		idxColList1.add(c0);
		idxColList1.add(c2);
		Index index1 = new Index("UK_persona_id_ape",idxColList1);
		ArrayList<Index> idxList0 = new ArrayList<Index>();
		idxList0.add(index0);
		ArrayList<Index> idxList1 = new ArrayList<Index>();
		idxList1.add(index1);
		t0.setUniqueKeys(idxList0);
		t1.setUniqueKeys(idxList1);
		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		ReportGenerator.findDifferences(db0 , db1 , "file_test/diffUKColumns.txt");
	
	}	
	
	@Test
	public void sameTableExtraUniqueKey(){
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

		ArrayList<Column> idxColList0 = new ArrayList<Column>();
		idxColList0.add(c0);
		idxColList0.add(c1);
		Index index0 = new Index("UK_persona_id_name",idxColList0);
		ArrayList<Index> idxList0 = new ArrayList<Index>();
		idxList0.add(index0);

		ArrayList<Column> idxColList1 = new ArrayList<Column>();
		idxColList1.add(c2);
		idxColList1.add(c3);
		Index index1 = new Index("UK_ape_dire",idxColList1);
		ArrayList<Column> idxColList2 = new ArrayList<Column>();
		idxColList2.add(c0);
		idxColList2.add(c1);
		Index index2 = new Index("UK_persona_id_name",idxColList2);
		
		ArrayList<Index> idxList1 = new ArrayList<Index>();
		idxList1.add(index1);
		idxList1.add(index2);
		
		t0.setUniqueKeys(idxList0);
		t1.setUniqueKeys(idxList1);

		String AUX;
		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		AUX = db0.getTables().get(0).getUniqueKeys().toString();
		System.out.println(AUX.toString());
		AUX = db1.getTables().get(0).getUniqueKeys().toString();
		System.out.println(AUX.toString());

		System.out.println("REPORT GENERATOR \n\n");
		ReportGenerator.findDifferences(db0 , db1 , "file_test/ExtraUK.txt");
	
		AUX = db0.getTables().get(0).getUniqueKeys().toString();
		System.out.println(AUX.toString());
		AUX = db1.getTables().get(0).getUniqueKeys().toString();
		System.out.println(AUX.toString());

	}
	
	@Test
	public void sameTableDiffTimeEventTriggersTest(){
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


		ArrayList<Trigger> trigList0 = new ArrayList<Trigger>();
		ArrayList<Trigger> trigList1 = new ArrayList<Trigger>();

		Trigger tr1 = new Trigger("trg_auditria", "BEFORE", "INSERT");
		Trigger tr2 = new Trigger("trg_auditria", "AFTER", "INSERT");
		
		trigList0.add(tr1);
		trigList1.add(tr2);

		t0.setTriggers(trigList0);
		t1.setTriggers(trigList1);

		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		ReportGenerator.findDifferences(db0 , db1 , "file_test/SameTableDiffTimeEventTriggers.txt");
	}

	@Test
	public void sameTableSameTriggersTest(){
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


		ArrayList<Trigger> trigList0 = new ArrayList<Trigger>();
		ArrayList<Trigger> trigList1 = new ArrayList<Trigger>();

		Trigger tr1 = new Trigger("trg_auditria", "BEFORE", "INSERT");
		Trigger tr2 = new Trigger("trg_auditria", "BEFORE", "INSERT");
		
		trigList0.add(tr1);
		trigList1.add(tr2);

		t0.setTriggers(trigList0);
		t1.setTriggers(trigList1);

		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		ReportGenerator.findDifferences(db0 , db1 , "file_test/SameTableSameTriggers.txt");
	}

	@Test
	public void sameTableDiffActionTimeTriggersTest(){
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


		ArrayList<Trigger> trigList0 = new ArrayList<Trigger>();
		ArrayList<Trigger> trigList1 = new ArrayList<Trigger>();

		Trigger tr1 = new Trigger("trg_auditria", "AFTER", "UPDATE");
		Trigger tr2 = new Trigger("trg_auditria", "AFTER", "INSERT");
		
		trigList0.add(tr1);
		trigList1.add(tr2);

		t0.setTriggers(trigList0);
		t1.setTriggers(trigList1);

		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		ReportGenerator.findDifferences(db0 , db1 , "file_test/SameTableDiffActionEventTriggers.txt");
	}

	@Test
	public void sameTableDiffTriggerNameTest(){
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


		ArrayList<Trigger> trigList0 = new ArrayList<Trigger>();
		ArrayList<Trigger> trigList1 = new ArrayList<Trigger>();

		Trigger tr1 = new Trigger("trg_auditria", "BEFORE", "INSERT");
		Trigger tr2 = new Trigger("trg_auditria", "AFTER", "INSERT");
		
		trigList0.add(tr1);
		trigList0.add(tr1);
		trigList0.add(tr1);
		trigList1.add(tr1);
		trigList1.add(tr2);
		trigList1.add(tr2);

		t0.setTriggers(trigList0);
		t1.setTriggers(trigList1);

		ArrayList<Table> tblist0 = new ArrayList<Table>();
		tblist0.add(t0);
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		tblist1.add(t1);
		db0.setTables(tblist0);
		db1.setTables(tblist1);

		ReportGenerator.findDifferences(db0 , db1 , "file_test/SameTableDiffTimeEventMultiTriggers.txt");
	}
	
	@Test
	public void firstDBModelHasOneProcedureSecondhasntProcedures() {
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
		ArrayList<Param> paramlist = new ArrayList<Param>();
		ArrayList<Procedure> proclist = new ArrayList<Procedure>();
		Param p1 = new Param("edadPersona", 1 , "int4");
		Param p2 = new Param("edadPromedio", 4 , "float");
		paramlist.add(p1);
		paramlist.add(p2);
		proclist.add(new Procedure ("prom_proc" , "void" ,   paramlist));
		
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
		db.setProcedures(proclist);
		db1.setTables(tblist1);
		ReportGenerator.findDifferences(db , db1 , "file_test/firstDBModelHasOneProcedureSecondhasntProcedures.txt");
	
		
	}
	
	@Test
	public void secondDBModelHasOneProcedureFirsthasntProcedures() {
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
		ArrayList<Param> paramlist1 = new ArrayList<Param>();
		ArrayList<Procedure> proclist1 = new ArrayList<Procedure>();
		Param p1 = new Param("edadPersona", 1 , "int4");
		Param p2 = new Param("edadPromedio", 4 , "float");
		paramlist1.add(p1);
		paramlist1.add(p2);
		proclist1.add(new Procedure ("prom_proc" , "void" , paramlist1));
		
		ArrayList<Table> tblist = new ArrayList<Table>();
		ArrayList<Table> tblist1 = new ArrayList<Table>();
		
		tblist.add(t);
		tblist1.add(t1);
		
	
		DBModel db = new DBModel("DATA BASE 1");
		DBModel db1 = new DBModel("DATA BASE 2");
		db.setTables(tblist);
		db1.setTables(tblist1);
		db1.setProcedures(proclist1);
		ReportGenerator.findDifferences(db , db1 , "file_test/secondDBModelHasOneProcedureFirsthasntProcedures.txt");
	
		
	}
	
	@Test
	public void DBModelsOnlyhaveProcAdtheyAreTheSame() {
		Param p1 = new Param("a", 1 , "int4");
		Param p2 = new Param("b", 1 , "int4");
		Param p3 = new Param("c", 4 , "varchar");
		Param p4 = new Param("d", 1 , "Date");
		ArrayList<Param> p1paramlist = new ArrayList<Param>();
		ArrayList<Param> p2paramlist = new ArrayList<Param>();
		ArrayList<Param> p3paramlist = new ArrayList<Param>();
		ArrayList<Procedure> proclist1 = new ArrayList<Procedure>();
		ArrayList<Procedure> proclist2 = new ArrayList<Procedure>();
		p1paramlist.add(p1);
		p1paramlist.add(p3);
		
		p2paramlist.add(p2);
		p2paramlist.add(p3);
		p2paramlist.add(p4);
		
		proclist1.add(new Procedure("subString", "varchar" , p1paramlist));
		proclist1.add(new Procedure("calcula_interes", "float" , p2paramlist));
		proclist1.add(new Procedure("actualiza_pedidos", "boolean" , p3paramlist));
		
		proclist2.add(new Procedure("subString", "varchar" , p1paramlist));
		proclist2.add(new Procedure("calcula_interes", "float" , p2paramlist));
		proclist2.add(new Procedure("actualiza_pedidos", "boolean" , p3paramlist));
		
		DBModel db1 = new DBModel("BASE DE DATOS 1");
		db1.setProcedures(proclist1);
		DBModel db2 = new DBModel("BASE DE DATOS 2");
		db2.setProcedures(proclist2);
		ReportGenerator.findDifferences(db1 , db2 , "file_test/DBModelsOnlyhaveProcAndtheyAreTheSame.txt");

	}
	
	@Test
	public void onlyProceduresSameNamebutDiff() {
		Param p1 = new Param("a", 1 , "int4");
		Param p2 = new Param("b", 1 , "int4");
		Param p3 = new Param("c", 4 , "varchar");
		Param p4 = new Param("d", 1 , "Date");
		Param p5 = new Param("d", 2 , "Date");
		ArrayList<Param> p1paramlist = new ArrayList<Param>();
		ArrayList<Param> p2paramlist = new ArrayList<Param>();
		ArrayList<Param> p3paramlist = new ArrayList<Param>();
		ArrayList<Param> p4paramlist = new ArrayList<Param>();
		ArrayList<Param> p5paramlist = new ArrayList<Param>();
		ArrayList<Procedure> proclist1 = new ArrayList<Procedure>();
		ArrayList<Procedure> proclist2 = new ArrayList<Procedure>();
		p1paramlist.add(p1);
		p1paramlist.add(p3);
		
		p2paramlist.add(p2);
		p2paramlist.add(p3);
		p2paramlist.add(p4);
		
		p4paramlist.add(p2);
		p4paramlist.add(p3);
		p4paramlist.add(p5);
		
		p5paramlist.add(p1);
		
		proclist1.add(new Procedure("subString", "varchar" , p1paramlist));
		proclist1.add(new Procedure("calcula_interes", "float" , p2paramlist));
		proclist1.add(new Procedure("actualiza_pedidos", "boolean" , p3paramlist));
		
		proclist2.add(new Procedure("subString", "int" , p1paramlist));
		proclist2.add(new Procedure("calcula_interes", "float" , p4paramlist));
		proclist2.add(new Procedure("actualiza_pedidos", "boolean" , p5paramlist));
		
		DBModel db1 = new DBModel("BASE DE DATOS 1");
		db1.setProcedures(proclist1);
		DBModel db2 = new DBModel("BASE DE DATOS 2");
		db2.setProcedures(proclist2);
		ReportGenerator.findDifferences(db1 , db2 , "file_test/onlyProceduresSameNamebutDiff.txt");

	}
	
	@Test
	public void onlyProceduresOneSameAndBothHasAdditional() {
		Param p1 = new Param("a", 1 , "int4");
		Param p2 = new Param("b", 1 , "int4");
		Param p3 = new Param("c", 4 , "varchar");
		
		ArrayList<Param> p1paramlist = new ArrayList<Param>();
		ArrayList<Param> p2paramlist = new ArrayList<Param>();
		ArrayList<Param> p4paramlist = new ArrayList<Param>();

		ArrayList<Procedure> proclist1 = new ArrayList<Procedure>();
		ArrayList<Procedure> proclist2 = new ArrayList<Procedure>();
		p1paramlist.add(p1);
		p1paramlist.add(p3);
		
		p2paramlist.add(p1);
		p2paramlist.add(p2);
		
		p4paramlist.add(p1);
		p4paramlist.add(p2);
	
			
		proclist1.add(new Procedure("subString", "varchar" , p1paramlist));
		proclist1.add(new Procedure("calcula_sumatoria", "int" , p2paramlist));
		
		
		proclist2.add(new Procedure("subString", "varchar" , p1paramlist));
		proclist2.add(new Procedure("calcula_productoria", "int" , p4paramlist));
		
		
		DBModel db1 = new DBModel("BASE DE DATOS 1");
		db1.setProcedures(proclist1);
		DBModel db2 = new DBModel("BASE DE DATOS 2");
		db2.setProcedures(proclist2);
		ReportGenerator.findDifferences(db1 , db2 , "file_test/onlyProceduresOneSameAndBothHasAdditional.txt");

	}
	
	@Test
	public void onlyProceduresAllHaveDifferentName() {
		Param p1 = new Param("a", 1 , "int4");
		Param p2 = new Param("b", 1 , "int4");
		Param p3 = new Param("c", 4 , "varchar");
		
		ArrayList<Param> p1paramlist = new ArrayList<Param>();
		ArrayList<Param> p2paramlist = new ArrayList<Param>();
		ArrayList<Param> p4paramlist = new ArrayList<Param>();

		ArrayList<Procedure> proclist1 = new ArrayList<Procedure>();
		ArrayList<Procedure> proclist2 = new ArrayList<Procedure>();
		p1paramlist.add(p1);
		p1paramlist.add(p3);
		
		p2paramlist.add(p1);
		p2paramlist.add(p2);
		
		p4paramlist.add(p1);
		p4paramlist.add(p2);
	
			
		proclist1.add(new Procedure("imprime_factura", "varchar" , p1paramlist));
		proclist1.add(new Procedure("calcula_sumatoria", "int" , p2paramlist));
		
		
		proclist2.add(new Procedure("imprime_remito", "varchar" , p1paramlist));
		proclist2.add(new Procedure("calcula_productoria", "int" , p4paramlist));
		
		
		DBModel db1 = new DBModel("BASE DE DATOS 1");
		db1.setProcedures(proclist1);
		DBModel db2 = new DBModel("BASE DE DATOS 2");
		db2.setProcedures(proclist2);
		ReportGenerator.findDifferences(db1 , db2 , "file_test/onlyProceduresAllHaveDifferentName.txt");

	}


}
