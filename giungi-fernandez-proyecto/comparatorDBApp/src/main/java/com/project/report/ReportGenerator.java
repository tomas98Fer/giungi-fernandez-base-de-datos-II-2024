package com.project.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.project.structure.Column;
import com.project.structure.DBModel;
import com.project.structure.ForeignKey;
import com.project.structure.Procedure;
import com.project.structure.Index;
import com.project.structure.Table;
import java.util.function.Function;

public class ReportGenerator {

	/**
	 * 
	 * @param db1 DB1 represents a model of data base.
	 * @param db2 DB1 represents a model of data base.
	 * @param fileName file where  filling it with difference information.
	 */
	public static void findDifferences( DBModel db1, DBModel db2 , String fileName ) {
		if(db1 == null || db2 == null)
			throw new IllegalArgumentException("Everyone data base model are null");
		if(fileName == null || fileName.isEmpty()) 
			throw new IllegalArgumentException("File name is incorrect.");
		File f = new File("file/" + fileName);

		try( FileWriter fw = new FileWriter(f);
			 BufferedWriter bw = new BufferedWriter(fw);
			 PrintWriter out = new PrintWriter(bw); )	
		{
			
			
			AllNameofTableNoMatch(db1 , db2 , f);
			if(f.length() == 0) {
				aditionalTables(db1 , db2 , f);
				treatmentTables(db1, db2, f);
			}
			treatmentProcedures(db1, db2 , f);
			
			if(f.length() == 0)
				out.println("BOTH DATA BASE REPRESENT THE SAME MODEL.\n");
			
			bw.close();
		}
		catch(IOException e) {
			System.out.println("Error E/S: "+e);
		}
				
}

	
	/*
	 * Compare the table list of the DBmodels.
	 * Write the file if search difference in columns in a couple Table. 
	 * Write the file if db1 Model has aditional columns.
	 * Write the file if db2 Model has aditional columns.
	 */

	private static void treatmentTables(DBModel db1, DBModel db2, File f) {
		// find the tables that their names match
		for (Table t1: db1.getTables()) {
			for(Table t2 : db2.getTables()) {
				String t1_lc = t1.getName().toLowerCase();
				String t2_lc = t2.getName().toLowerCase();
				if(t1_lc.equals(t2_lc)) {
					treatmentColumns(t1,t2,f);
					treatmentPK(t1,t2,f);
					treatmentRepeatedObjInTables(t1,t2,f,Table::getForeignKeys,"FOREING KEY");
					treatmentRepeatedObjInTables(t1,t2,f,Table::getIndexes,"INDEX");
					treatmentRepeatedObjInTables(t1,t2,f,Table::getUniqueKeys,"UNIQUE KEY");
					treatmentRepeatedObjInTables(t1,t2,f,Table::getTriggers,"TRIGGER");
				}
			}
		}
	}

	private static <T> void treatmentRepeatedObjInTables(Table t1, Table t2, File f, Function<Table, ArrayList<T>> function, String type) {
		ArrayList<T> objColumns1 = function.apply(t1);
		ArrayList<T> objColumns2 = function.apply(t2);
		
		if (objColumns1 == null || objColumns2 == null || objColumns1.isEmpty() || objColumns2.isEmpty()) {
			return;
		}

		objColumns1 = cloneList(objColumns1);
		objColumns2 = cloneList(objColumns2);
		// Lógica para eliminar elementos coincidentes de ambas listas sin perder índice
		int i = 0;
		while (i < objColumns1.size()) {
			T o1 = objColumns1.get(i);
			boolean removed = false;
			for (T o2 : objColumns2) {
				if (o1.equals(o2)) {
					objColumns1.remove(o1);
					objColumns2.remove(o2);
					removed = true;
					break;
				}
			}
			if (!removed) {
				i++;
			}
		}
		
		if (!objColumns1.isEmpty()) {
			for (T o1 : objColumns1) {
				writeObject(o1, f, "EXTRA " + type + " IN TABLE " + t1.getName() + " OF DATABASE 1");
			}
		}
		if (!objColumns2.isEmpty()) {
			for (T o2 : objColumns2) { 
				writeObject(o2, f, "EXTRA " + type + " IN TABLE " + t2.getName() + " OF DATABASE 2");
			}
		}
	}
	

	private static void treatmentPK(Table t1, Table t2, File f) {
		Index pk1 = t1.getPrimaryKey();
		Index pk2 = t2.getPrimaryKey();
		if (pk1 == null || pk2 == null)
			return;
		if (pk1.getColumns() == null || pk2.getColumns() == null)
			return;
		ArrayList<Column> pkColumnst1 = t1.getPrimaryKey().getColumns();
		ArrayList<Column> pkColumnst2 = t2.getPrimaryKey().getColumns();
		String description = "";
		if(pkColumnst1.size() == pkColumnst2.size()) {
			for(int i = 0 ; i < pkColumnst1.size() ; i++) {
				if(!pkColumnst1.get(i).equals(pkColumnst2.get(i))) {
					description = "PRIMARY KEYS ARE DIFFRENT.\nPRIMARY KEY IN DATA BASE 1, TABLE: " + t1.getName() + "\n";
					writeObject(t1,f, description);
					description = "PRIMARY KEYS ARE DIFFRENT.\nPRIMARY KEY IN DATA BASE 2, TABLE: " + t2.getName() + "\n";
					writeObject(t2 , f , description);
				}
			}
		}
		else {
			description = "PRIMARY KEYS ARE DIFFRENT.\nPRIMARY KEY IN DATA BASE 1, TABLE: " + t1.getName() + "\n";
			writeObject(t1,f, description);
			description = "PRIMARY KEYS ARE DIFFRENT.\nPRIMARY KEY IN DATA BASE 2, TABLE: " + t2.getName() + "\n";
			writeObject(t2 , f , description);
		}
		
	}

	private static void writefile(File f, String description) {
		try( FileWriter fw1 = new FileWriter(f , true);
				 BufferedWriter bw1 = new BufferedWriter(fw1);
				 PrintWriter out1 = new PrintWriter(bw1); )	
		{
			out1.println(description);
			bw1.close();
		}
		catch(IOException e) {
			System.out.println("Error writing  writefile method" + e);
    }
		
	}


	private static <T> ArrayList<T> cloneList  (ArrayList<T> list) {
		ArrayList<T> clone = new ArrayList<T>(list.size());
		for(T item: list) 
			clone.add(item);
		return clone;
	}
	/*
	 * Compare if two table has the same columns.
	 * if exist some difference write it in the file.
	 * 
	 */
	private static void treatmentColumns(Table t1, Table t2, File f) {
		int pos = -1;
		Column auxC1;
		Column auxC2;
		String description;
		
		ArrayList<Column> addColt1 = new ArrayList<Column>();
		ArrayList<Column> addColt2 = new ArrayList<Column>();
		ArrayList<Column> sameColumns = new ArrayList<Column>();
		
		for(int i = 0 ; i < t1.getColumns().size() ; i++) {
			// ask if exist a column with the same name.
			pos = t2.containsColumnName(t1.getColumns().get(i));
			
			if (pos == -2){
				throw new IllegalArgumentException("Error in method containsColumnName");
			}
			// column is an additional.
			if(pos == -1) {
				addColt1.add(t1.getColumns().get(i));
			}
			// exist column with the same name in t2.
			if(pos >= 0 ) {
				//check if are equals.
				
				auxC1 = t1.getColumns().get(i);
				auxC2 = t2.getColumns().get(pos);
				sameColumns.add(auxC2);
				
				if(!auxC1.equals(auxC2)) {
					description = "SAME COLUMN NAME BUT DIFFERENT TYPE";
					writeObject(auxC1, f , description + " IN THE TABLE: " + t1.getName() + "\n" );
					writeObject(auxC2, f ,  description + "IN THE TABLE: " + t1.getName() + "\n" );
				}
				//if they aren't dont should print the column.
				
			}
			
			
		}
		// when out of the last loop : The columns with the same name already treatments y and get the aditional of t1.
		
		//get aditionals columns in t2.
		for(int i = 0 ; i < t2.getColumns().size() ; i ++) {
			
			if( !sameColumns.contains(t2.getColumns().get(i))) {
				addColt2.add(t2.getColumns().get(i));
			}
			
		}
		// print aditional tables to both tables weather they have it.
		
		description = "ADDITIONAL COLUMNS ON TABLE " + t1.getName() + " OF DATABASE 1";
		writeAdditionalColumns(f,addColt1,description);
		
		description = "ADDITIONALS COLUMN ON TABLE " + t2.getName() + " OF DATABASE 2";
		writeAdditionalColumns(f,addColt2,description);
	}

	// write a list of Columns in the file. 
	private static void writeAdditionalColumns(File f, ArrayList<Column> columns, String description ){
			for(Column columnAd : columns){
				writeObject(columnAd,f,description);
			}
	}


	private static void writeObject(Object o, File f, String description) {
		try( FileWriter fw1 = new FileWriter(f , true);
				 BufferedWriter bw1 = new BufferedWriter(fw1);
				 PrintWriter out1 = new PrintWriter(bw1); )	
		{
			out1.println(description);
			out1.println(o.toString());
			bw1.close();
		}
		catch(IOException e) {
			System.out.println("Error writing  writeFK method" + e);
		}
	}

	private static boolean isTheSameColumnsList(ArrayList<Column> columns1, ArrayList<Column> columns2) {
		if (columns1.size() != columns2.size())
			return false;
			
		for (Column c1 : columns1) {
			if (!columns2.contains(c1)) {
				return false;
			}
		}
		return true;
	}			


	//true if this method write in file
	private static void AllNameofTableNoMatch(DBModel db1, DBModel db2, File f) {
		String description = "";
		if (db1.getTables()==null)
			throw new IllegalArgumentException("Error in method AllNameofTableNoMatch, tables of "+ db1.getName() +" are null");
		if (db2.getTables()==null)
			throw new IllegalArgumentException("Error in method AllNameofTableNoMatch, tables of "+ db2.getName() +" are null");
			if(db1.getTables().isEmpty() && !db2.getTables().isEmpty()) {
			description = "DATABASE MODEL : " + db1.getName() + " DON'T HAS TABLES.";
			writefile(f, description);
			description = "TABLES IN DATABASE MODEL : " + db2.getName() ;
			writeTables(f , db2.getTables() , description);
			return;
		}
		if(!db1.getTables().isEmpty() && db2.getTables().isEmpty()) {
			description = "DATABASE MODEL : " + db2.getName() + " DON'T HAS TABLES.";
			writefile(f, description);
			description = "TABLES IN DATABASE MODEL : " + db1.getName() ;
			writeTables(f , db1.getTables() , description);
			return;
		}
		if(db1.getTables().isEmpty() && db2.getTables().isEmpty()) {
			return;
		}
		
		ArrayList<Table> nmtdb1 = noMatchingTables(db1.getTables() , db2.getTables());
		
		//at least db1 model has one table that matching with someone table in db2 model.
		if(nmtdb1.size() != db1.getTables().size()) {
			return;
		}
		description = "ALL TABLES IN EACH DATABASE MODEL ARE DIFFERENT.\n" ;
		writeTables(f, db1.getTables() , description + "TABLES FROM: " + db1.getName() + "\n" );
		writeTables(f, db2.getTables() , description + "TABLES FROM: " + db2.getName() + "\n");
		
		return;
		
	}

	//true if this method write in the file.
	private static boolean aditionalTables(DBModel db1, DBModel db2, File f) {
		
		boolean w1 = false;
		boolean w2 = false;
		String description = "ADITIONAL TABLES IN : ";
		ArrayList<Table> addtabledb1 = noMatchingTables(db1.getTables() , db2.getTables()); 
		//at least db1 has one table that match with some table in db2.
		if(addtabledb1.size() < db1.getTables().size() && !addtabledb1.isEmpty()) {
			
			w1 = writeTables(f , addtabledb1 , description +  db1.getName().toUpperCase());
		}
		ArrayList<Table> addtabledb2 = noMatchingTables(db2.getTables() , db1.getTables());
		if(addtabledb2.size() < db2.getTables().size() && !addtabledb2.isEmpty()) {
			
			w2 = writeTables(f , addtabledb2 , description +  db2.getName().toUpperCase());
		}

		return w1 || w2;
	}
	
	//Write a list of tables in the file.
	private static boolean writeTables(File f, ArrayList<Table> addTables, String description) {
		if(addTables.isEmpty())
			return false;
		try( FileWriter fw1 = new FileWriter(f , true);
				 BufferedWriter bw1 = new BufferedWriter(fw1);
				 PrintWriter out1 = new PrintWriter(bw1); )	
		{
			out1.println(description);
			for(Table t : addTables) {
				 out1.println(t.toString());
				
			}
			bw1.close();
		}
		catch(IOException e) {
			System.out.println("Error escribiendo en  metodo writeAdditionalTables" + e);
		}
		
		return true;
	}

	// return all tables in tables1 that aren't matching with table names from tables2. 
	private static ArrayList<Table> noMatchingTables(ArrayList<Table> tables1, ArrayList<Table> tables2) {
		ArrayList<Table> addTables = new ArrayList<>();
		String s = null;
		Table t = null;
		for (int i = 0 ; i < tables1.size(); i++) {
			t = tables1.get(i);
			s = t.getName();
			if(!thisNameIsIn(s,tables2))
				addTables.add(t);
			
		}
		
		return addTables;
	}

	private static boolean thisNameIsIn(String s, ArrayList<Table> tables) {
		for(Table t : tables) {
			if( t.getName().equals(s))
				return true;
		}
		return false;
	}
	
	
	
	private static void treatmentProcedures(DBModel db1, DBModel db2, File f) {
		String description = "";
		if(db1.getProcedures().isEmpty() && db2.getProcedures().isEmpty())
			return;
		if(!db1.getProcedures().isEmpty() && db2.getProcedures().isEmpty()) {
			description = "THE DATABASE: " + db2.getName().toUpperCase() + " DON'T HAS PROCEDURE\n";
			writefile(f , description);
			description = "PROCEDURES IN : " + db1.getName().toUpperCase() + " DATABASE\n";
			writeProcedureList(db1.getProcedures(), description ,  f);
			return;
		}
		if(db1.getProcedures().isEmpty() && !db2.getProcedures().isEmpty()) {
			description = "THE DATABASE: " + db1.getName().toUpperCase() + " DON'T HAS PROCEDURE\n";
			writefile(f , description);
			description = "PROCEDURES IN : " + db2.getName().toUpperCase() + " DATABASE\n";
			writeProcedureList(db2.getProcedures(), description ,  f);
			return;
		}
		
		ArrayList<String> sameProcName = findSameProcNames(db1.getProcedures(),db2.getProcedures());
		// any procedure match
		if( sameProcName.size() == 0) {
		

			description = "ALL PROCEDURES IN THE DATABASE ARE DIFFERENT.\n" 
							+ "PROCEDURES IN " ;
			
			
			writeProcedureList(db1.getProcedures(), description  + db1.getName() + ".\n"  ,  f);
			writeProcedureList(db2.getProcedures(), description  + db2.getName() + ".\n"  ,  f);
			return;
		}
		// find difference betwen two procedure
		int jdb1,jdb2 = 0; // cursors
		Procedure pdb1,pdb2 = null; //procedures to campare
		for(int i = 0; i < sameProcName.size() ; i ++) {
			jdb1 = indexProcIn(sameProcName.get(i),db1.getProcedures());
			jdb2 = indexProcIn(sameProcName.get(i),db2.getProcedures());
			pdb1 = db1.getProcedures().get(jdb1);
			pdb2 = db2.getProcedures().get(jdb2);
			if( !pdb1.equals(pdb2)) {
				description = "PROCEDURE HAS THE SAME NAME BUT ARE DIFFERENT.\n"
								+ sameProcName.get(i) +" IN  " ;
				writeProcedure(pdb1,description + db1.getName(),f);
				writeProcedure(pdb2,description + db2.getName() ,f);
			}
		}
		//find aditional procedures in db1
		boolean writeMessage = true;
		if(db1.getProcedures().size() > sameProcName.size()) {
			pdb1 = null;
			description = "ADTIONAL PROCEDURES IN " + db1.getName() + "\n";
			for(int i = 0; i < db1.getProcedures().size() ; i++) {
				pdb1 = db1.getProcedures().get(i);
				if(!sameProcName.contains(pdb1.getName())) {
					if(writeMessage) {
						writefile(f,description);
					}
					writeProcedure(pdb1 , "" , f);
				}
				
			}
			
		}
		//find aditional procedures in db2
		if(db2.getProcedures().size() > sameProcName.size()) {
			writeMessage = true;
			pdb2 = null;
			description = "ADTIONAL PROCEDURES IN " + db2.getName() + "\n";
			for(int i = 0; i < db2.getProcedures().size() ; i++) {
				pdb2 = db2.getProcedures().get(i);
				if(!sameProcName.contains(pdb2.getName())) {
					if(writeMessage) {
						writefile(f,description);
					}
					writeProcedure(pdb2 , "" , f);
				}
				
			}			
		}
		
		
	}

	private static void writeProcedure(Procedure p, String description, File f) {
		try( FileWriter fw1 = new FileWriter(f , true);
				 BufferedWriter bw1 = new BufferedWriter(fw1);
				 PrintWriter out1 = new PrintWriter(bw1); )	
		{
			out1.println(description);
			out1.println(p.toString());
			bw1.close();
		}
		catch(IOException e) {
			System.out.println("Error : " + e);
		}
			
		
	}


	/*
	 *  find all Procedure names that match in two Procedure list
	 */
	private static ArrayList<String> findSameProcNames(ArrayList<Procedure> p1, ArrayList<Procedure> p2) {
		ArrayList<String> s = new ArrayList<String>();
		String nameProc = "";
		for(int i = 0; i < p1.size() ; i++ ) {
			nameProc = p1.get(i).getName();
			if( indexProcIn(nameProc,p2) >= 0) {
				s.add(nameProc);
			}
				
				
		}
		return s;
	}

	/*
	 *  return a the index the first occurence of nameProc in a Procedure List
	 */
	private static int indexProcIn(String nameProc, ArrayList<Procedure> p2) {
		for ( int i = 0; i < p2.size() ; i++) {
			if(p2.get(i).getName().equals(nameProc))
				return i;
		} 
		return -1;
	}


	private static void writeProcedureList(ArrayList<Procedure> procedures , String description , File f) {
		if(procedures.isEmpty())
			return;
		try( FileWriter fw1 = new FileWriter(f , true);
				 BufferedWriter bw1 = new BufferedWriter(fw1);
				 PrintWriter out1 = new PrintWriter(bw1); )	
		{
			out1.println(description);
			for(Procedure p : procedures) {
				 out1.println(p.toString());
				
			}
			bw1.close();
		}
		catch(IOException e) {
			System.out.println("Error : " + e);
		}
		
		
	}
	

}
