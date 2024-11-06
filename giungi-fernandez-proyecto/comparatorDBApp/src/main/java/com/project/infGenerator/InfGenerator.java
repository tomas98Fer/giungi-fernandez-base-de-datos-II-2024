package com.project.infGenerator;

import java.io.BufferedWriter;
import java.io.Closeable;
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
		File f = new File("file/" + fileName + ".txt");
		boolean a1, a2 = false;
		try( FileWriter fw = new FileWriter(f);
			 BufferedWriter bw = new BufferedWriter(fw);
			 PrintWriter out = new PrintWriter(bw); )	
		{
			

			a1 = AllNameofTableNoMatch(db1, db2, f);
			a2 = aditionalTables( db1 , db2 , f);
			
			// buscar las tablas que matchean por nombre
			
				//buscarTablas con el mismo nombre (hacer un buscar por nombre en la clace MDBprocedure)
			
			
			
			//treatmentColumns(db1 , db2 , f);
			if(!(a1 || a2))
				out.println("BOTH DATA BASE REPRESENT THE SAME MODEL.");
			
			bw.close();
		}
		catch(IOException e) {
			System.out.println("Error E/S: "+e);
		}
				
}

	private static void treatmentColumns(Table t1, Table t2, File f, String db1N, String db2N) {
		int pos = -1;
		Column auxC1 = null;
		Column auxC2 = null;
		String description = null;
		
		ArrayList<Column> addColt1 = new ArrayList<Column>();
		ArrayList<Column> addColt2 = new ArrayList<Column>();
		ArrayList<Column> sameColumns = new ArrayList<Column>();
		
		for(int i = 0 ; i < t1.getColumns().size() ; i++) {
			// ask if exist a column with that name.
			pos = t2.containsColumName(t1.getColumns().get(i));
			
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
					writeColumn(auxC1, f , description);
					writeColumn(auxC2, f ,  description);
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
		// printear tabalas addicionales para ambdas tablas si estan existen.
		
		if(addColt1.size() > 0) {
			//printiar las adicionales de t1.
		}
		
		if() {
			//printitear las adicionales de t2
		}
		
		
		/* comparar por nombres
			si encuentra un mach de columnas por nombre comparar.
				si son distinto escribir en el file.
				sino avazar.
		*/
	}

	private static void writeColumn(Column auxC1, File f, String description) {
		try( FileWriter fw1 = new FileWriter(f , true);
				 BufferedWriter bw1 = new BufferedWriter(fw1);
				 PrintWriter out1 = new PrintWriter(bw1); )	
		{
			out1.println(description);
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
