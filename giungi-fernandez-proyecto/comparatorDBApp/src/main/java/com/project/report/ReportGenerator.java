package com.project.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.project.structure.Column;
import com.project.structure.DBModel;
import com.project.structure.Table;



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
					
				}
			}
		}
	}

	private static void treatmentPK(Table t1, Table t2, File f) {
		ArrayList<Column> pkColumnst1 = t1.getPrimaryKey().getColumns();
		ArrayList<Column> pkColumnst2 = t2.getPrimaryKey().getColumns();
		String description = "";
		if(pkColumnst1.size() == pkColumnst2.size()) {
			for(int i = 0 ; i < pkColumnst1.size() ; i++) {
				if(!pkColumnst1.get(i).equals(pkColumnst2.get(i))) {
					description = "PRIMARY KEYS ARE DIFFRENT.\nPRIMARY KEY IN DATA BASE 1, TABLE: " + t1.getName() + "\n";
					writePK(t1,f, description);
					description = "PRIMARY KEYS ARE DIFFRENT.\nPRIMARY KEY IN DATA BASE 2, TABLE: " + t2.getName() + "\n";
					writePK(t2 , f , description);
				}
			}
		}
		else {
			description = "PRIMARY KEYS ARE DIFFRENT.\nPRIMARY KEY IN DATA BASE 1, TABLE: " + t1.getName() + "\n";
			writePK(t1,f, description);
			description = "PRIMARY KEYS ARE DIFFRENT.\nPRIMARY KEY IN DATA BASE 2, TABLE: " + t2.getName() + "\n";
			writePK(t2 , f , description);
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


	//
	private static void writePK(Table t, File f, String description) {
		try( FileWriter fw1 = new FileWriter(f , true);
				 BufferedWriter bw1 = new BufferedWriter(fw1);
				 PrintWriter out1 = new PrintWriter(bw1); )	
		{
			out1.println(description);
			for(Column c : t.getPrimaryKey().getColumns()) {
				 out1.println(c.toString());
				
			}
			bw1.close();
		}
		catch(IOException e) {
			System.out.println("Error writing  writePK method" + e);
		}
		
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
					writeColumn(auxC1, f , description + " IN THE TABLE: " + t1.getName() + "\n" );
					writeColumn(auxC2, f ,  description + "IN THE TABLE: " + t1.getName() + "\n" );
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
				writeColumn(columnAd,f,description);
			}
	}

	//write a column in the file.
	private static void writeColumn(Column c, File f, String description) {
		try{ FileWriter fw1 = new FileWriter(f , true);
				BufferedWriter bw1 = new BufferedWriter(fw1);
				PrintWriter out1 = new PrintWriter(bw1); 
				out1.println(description);
				out1.println(c.toString());
				bw1.close();
		}
		catch(IOException e) {
			System.out.println("Error writing in writeAdditionalTables method " + e);
		}
	}

	//true if this method write in file
	private static void AllNameofTableNoMatch(DBModel db1, DBModel db2, File f) {
		String description = "";
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
			description = "THE DATABASE MODELS  DON'T HAVE TABLES. THEY ARE EMPTY.";
			writefile(f, description);
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

}
