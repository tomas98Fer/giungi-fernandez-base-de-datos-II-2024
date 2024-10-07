package com.proyect.structure;

public class Column {
	
	private String name;
	private String type;
	
	public Column(String myName, String type) {
		if(myName == null || type == null) throw new IllegalArgumentException("Parameter error");
		if(myName.isEmpty() || type.isEmpty()) throw new IllegalArgumentException("Parameter error");
		this.name = myName;
		this.type = type;
	}

	public String getName() {
	
		return this.name;
	}
	
	public String getType() {
		
		return this.type;
	}
	
	
	

}
