package com.proyect.structure;

import java.util.ArrayList;

public class ForeignKey extends Index{

	private Table reference;
	private ArrayList<Column> columnsReference;
	public ForeignKey(String name) {
		super(name);
		
	}
	
	/*private Table reference;
	
	ForeignKey(String name, ArrayList<Column> l,Table reference ) {
		super(name,l);
		this.reference = reference;
		
	}
	
	
	public Table getReference(){
		
		return this.reference;
	}
	
	public void setReference(Table reference) {
		if(reference == null) throw new IllegalArgumentException(" The reference is null");
		this.reference = reference;
	};
	
	*/

}
