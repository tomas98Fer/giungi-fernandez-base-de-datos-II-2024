package com.project.structure;

import java.util.ArrayList;

public class DBModel {

	private ArrayList<Table> tables;
	
	private ArrayList<Procedure> procedures;

	/**
	 * getter tables method.
	 */
	public ArrayList<Table>getTables(){
		
		return this.tables;
	}
	
	/**
	 * setter Tables method. 
	 * @param ldb list of table in the data base.
	 */
	public void setTables(ArrayList<Table> ldb) {
		if(ldb == null)
			throw new IllegalArgumentException ("The list table can be null");
		this.tables = ldb;
		
	}
	/**
	 * 
	 * @param pd List of procedures.
	 */
	public void setProcedures(ArrayList<Procedure> pd) {
		if( pd == null )
			throw new IllegalArgumentException("Procedure list param is null.");
		this.procedures = pd;
		
	}
	
	
	public void printProcedures() {
		
		for(Procedure p : this.procedures) {
			System.out.println(p.toString());
		}
	}

	public ArrayList<Procedure> getProcedures() {
	
		return this.procedures;
	}
	
	


	
}
