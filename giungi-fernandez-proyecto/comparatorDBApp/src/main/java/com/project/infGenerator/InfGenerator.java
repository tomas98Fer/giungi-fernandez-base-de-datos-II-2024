package com.project.infGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.project.structure.Column;
import com.project.structure.DBModel;
import com.project.structure.Table;



public class InfGenerator {

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
			
	
			// buscar las tablas que matchean por nombre
			for (Table t1: db1.getTables()) {
				for(Table t2 : db2.getTables()) {
					String t1_lc = t1.getName().toLowerCase();
					String t2_lc = t2.getName().toLowerCase();
					if(t1_lc.equals(t2_lc)) {
						treatmentColumns(t1,t2,f);
					}
				}
			}
			//buscarTablas con el mismo nombre (hacer un buscar por nombre en la clace MDBprocedure)
			
			
			
			//treatmentColumns(t1 , t2 , f);
			if(f.length() == 0)
				out.println("BOTH DATA BASE REPRESENT THE SAAME MODEL.\n askdk\n");
			
			bw.close();
		}
		catch(IOException e) {
			System.out.println("Error E/S: "+e);
		}
				
}

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
			// column is an additional
			if(pos == -1) {
				addColt1.add(t1.getColumns().get(i));
			}
			// exist column with the same name in t2.
			if(pos >= 0 ) {
				//check if are equals
				
				auxC1 = t1.getColumns().get(i);
				auxC2 = t2.getColumns().get(pos);
				sameColumns.add(auxC2);
				
				if(!auxC1.equals(auxC2)) {
					description = "SAME COLUMN NAME BUT DIFFERENT TYPE";
					writeColumn(auxC1, f , description + "IN DB1");
					writeColumn(auxC2, f ,  description + "IN DB2");
				}
				//if they aren't dont should print the column.
				
			}
			
			
		}
		// cuando salgo de este siglo obtengo : columnas con mismo nombre ya tratadas y las adicionales de db1
		
		//sacar archivos adicionales de t2
		for(int i = 0 ; i < t2.getColumns().size() ; i ++) {
			
			if( !sameColumns.contains(t2.getColumns().get(i))) {
				addColt2.add(t2.getColumns().get(i));
			}
			
		}
		// printear tablas addicionales para ambas tablas si estas existen.
		
		description = "ADDITIONAL COLUMN ON TABLE " + t1.getName() + "OF DATABASE 1";
		writeAdditionalColumns(f,addColt1,description);
		
		description = "ADDITIONAL COLUMN ON TABLE " + t2.getName() + "OF DATABASE 2";
		writeAdditionalColumns(f,addColt2,description);
	}

	private static void writeAdditionalColumns(File f, ArrayList<Column> columns, String description ){
			for(Column columnAd : columns){
				writeColumn(columnAd,f,description);
			}
	}

	private static void writeColumn(Column c, File f, String description) {
		try{ FileWriter fw1 = new FileWriter(f , true);
				BufferedWriter bw1 = new BufferedWriter(fw1);
				PrintWriter out1 = new PrintWriter(bw1); 
				out1.println(description);
				out1.println(c.toString());
				out1.close();
				bw1.close();
				fw1.close();
		}
		catch(IOException e) {
			System.out.println("Error escribiendo en  metodo writeAdditionalTables" + e);
		}
	}

	//true if this method write in file
	private static boolean AllNameofTableNoMatch(DBModel db1, DBModel db2, File f) {
		
		ArrayList<Table> nmtdb1 = noMatchingTables(db1.getTables() , db2.getTables());
		
		//hay almenos una tabla que coincide
		if(nmtdb1.size() != db1.getTables().size()) {
			return false;
		}
		String description = "THE TABLES FROM" + db1.getName().toUpperCase() + " NOT MATCHING "
				+ "WITH TABLES FROM " + db2.getName().toUpperCase() + ".\n" 
				+ db1.getName().toUpperCase() + " TABLES:\n";
		writeTables(f, db1.getTables() , description );
		description = db2.getName() + " TABLES:\n";
		writeTables(f, db2.getTables() , description );
		
		return true;
		
	}

	//true if this method write in the file
	private static boolean aditionalTables(DBModel db1, DBModel db2, File f) {
		
		boolean w1 = false;
		boolean w2 = false;
		String description = null;
		ArrayList<Table> addtabledb1 = noMatchingTables(db1.getTables() , db2.getTables()); 
		//almenos una tabla tienen en comun
		if(addtabledb1.size() < db1.getTables().size() && !addtabledb1.isEmpty()) {
			description = "ADITIONAL TABLES IN : " + db1.getName().toUpperCase();
			w1 = writeTables(f , addtabledb1 , description);
		}
		ArrayList<Table> addtabledb2 = noMatchingTables(db2.getTables() , db1.getTables());
		if(addtabledb2.size() < db2.getTables().size() && !addtabledb2.isEmpty()) {
			description = "ADITIONAL TABLES IN: " + db2.getName().toUpperCase();
			w2 = writeTables(f , addtabledb2 , db2.getName());
		}

		return w1 || w2;
	}
	
	
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

	// return all tables in tables1 that aren't matching with table names from tables2 
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
