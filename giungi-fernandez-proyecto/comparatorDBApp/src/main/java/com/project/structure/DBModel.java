package com.project.structure;

import java.util.ArrayList;

public class DBModel {

	private ArrayList<Table> tables;

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
	
	
}
