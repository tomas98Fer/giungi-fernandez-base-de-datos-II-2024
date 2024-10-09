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
	public ArrayList<Column> getColumns(){
		
		return this.columns;
	}
	
	
	@Override
	public boolean equals(Object o ) {
		if(o instanceof Index  ) {
			Index idx = (Index) o;
			if(idx.getColumns().size() != this.columns.size()) 
				return false;
			for(int i = 0; i < this.columns.size();i++) {
				String type_idx = idx.getColumns().get(i).getType();
				String type_this = this.columns.get(i).getType();
				if(!type_idx.equals(type_this)) {
					return false;
				}
				
			}
			return true;
		}
		return false;
	}
	
}
