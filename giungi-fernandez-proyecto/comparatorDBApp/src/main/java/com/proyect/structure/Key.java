package com.proyect.structure;

import java.awt.List;
import java.util.ArrayList;

public class Key extends MetaObjectDB  {
	
	private ArrayList<Column> columnsKey;
	
	public Key(String name) {
		super(name);
		this.columnsKey = new ArrayList<Column>();
	}


	public Key(String name, ArrayList<Column> l) {
		super(name);
		this.columnsKey = l;
		
	}


	
	
	public ArrayList<Column> getColumnsKey(){
		
		return this.columnsKey;
	}
	
	
	
	
}
