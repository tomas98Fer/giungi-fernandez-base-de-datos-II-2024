package com.proyect.structure;

import java.util.ArrayList;

public class Index extends  MetaObjectDB {

	private ArrayList<Column> columns;
	
	
	public Index(String name) {
		super(name);
		this.columns = new ArrayList<Column>();
	}

	/**
	 * 
	 * @param name name of index.
	 * @param l list of columns that belong to the table. 
	 */
	
	 public Index(String name, ArrayList<Column> l) {
		super(name);
		this.columns = l;
		
	}
	


	
	/**
	 * 
	 * @return columns of index;
	 */
	public ArrayList<Column> getColumnsKey(){
		
		return this.columns;
	}
	
	
}
