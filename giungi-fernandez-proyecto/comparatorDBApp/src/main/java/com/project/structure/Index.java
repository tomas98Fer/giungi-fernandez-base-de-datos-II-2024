package com.project.structure;

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
	
	public String toString() {
		String res = "";
		res = " name: " + this.getName()+"\n";
		for(Column c : this.columns) {
			
			res += c.toString();
			
		}
		
		return res;
	}
	
	
	@Override
	public boolean equals(Object o ) {
		if(o instanceof Index  ) {
			Index idx = (Index) o;
			if(idx.getColumns().size() != this.columns.size()) 
				return false;
			for(int i = 0; i < this.columns.size();i++) {
				Column col_idx = idx.getColumns().get(i);
				Column col_this = this.columns.get(i);
				if(!col_this.equals(col_idx)) {
					return false;
				}
				
			}
			return true;
		}
		return false;
	}
	
}
