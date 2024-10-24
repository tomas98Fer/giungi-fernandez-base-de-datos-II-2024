package com.proyect.structure;

import java.util.ArrayList;

public class ForeignKey extends Index{

	private Table reference;
	//columns has reference foreign key
	private ArrayList<Column> referencedColumns;
	public ForeignKey(String name) {
		super(name);
		this.referencedColumns = new ArrayList<Column>();
		
	}
	
	
	
	ForeignKey(String name, ArrayList<Column> l,Table reference ) {
		super(name,l);
		this.reference = reference;
		
	}
	
	/**
	 * 
	 * @return table reference
	 */
	public Table getReference(){
		
		return this.reference;
	}
	
	/**
	 * 
	 * @param reference table referenced
	 */
	public void setReference(Table reference) {
		if(reference == null) throw new IllegalArgumentException(" The reference is null");
		this.reference = reference;
	};
	
	/**
	 * 
	 * @return columns are referenced.
	 */
	public ArrayList<Column> getReferencedColumns() {
		
		return this.referencedColumns;
	}
	/**
	 * 
	 * @param rc referenced columns.
	 */
	public void setRefeferencedColumns(ArrayList<Column> rc) {
		if(rc == null) throw new IllegalArgumentException(" The referencedColumns are is null");
		this.referencedColumns = rc;
		
	}
	
	
	
	/**
	 * comparator ForeignKeys.
	 */
	@Override
	public boolean equals(Object o) {
		
		if( o instanceof ForeignKey) {
			ForeignKey f = (ForeignKey) o;
			
			if(f.name.equals(this.getName())) {
				
				if(f.getReference().equals(this.reference)) {
					
					if(f.getColumns().size() == this.getColumns().size() && f.getReferencedColumns().size() == this.referencedColumns.size()) {
						String type_fk1 = null;
						String type_fk2 = null;
						// check if types order columns are the same
						for(int i = 0; i < f.getColumns().size(); i++) {
							type_fk1 =this.getColumns().get(i).getType();
							type_fk2 = f.getColumns().get(i).getType();
							if(!type_fk1.equals(type_fk2))
								return false;
						}
						
						//check if referenced columns
						type_fk1 = null;
						type_fk2 = null;
						for(int i = 0 ; i < f.getReferencedColumns().size() ; i++) {
							type_fk1 = this.referencedColumns.get(i).getType();
							type_fk2 = f.getColumns().get(i).getType();
							if(!type_fk1.equals(type_fk2))
								return false;
						}
						return true;
							
					}
					
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		String res = "FOREIGN KEY:\n";
		res += "fk_name: " + this.getName() + "\n";
		for(Column c : this.getColumns()) {
			res += c.toString();
		}
		res += "REFERENCE TABLE: " + this.reference.getName() + "\n";
		res += "REFERENCED COLUMNS:\n";
		for(Column c : this.referencedColumns) {
			res += c.toString();
		}
		return res;
	}
	

}
