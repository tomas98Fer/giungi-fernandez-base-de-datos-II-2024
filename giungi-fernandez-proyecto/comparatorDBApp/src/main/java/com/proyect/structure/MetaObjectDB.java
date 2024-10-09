package com.proyect.structure;


/*
 * Abstract Class that has finallity of  represent an sql datbase artefact
 * like a schemas,columns,etc  
 */

public abstract class MetaObjectDB {

	protected String name;
	
	/*
	 * Constructor
	 * @param name 
	 **/
	public MetaObjectDB( String name) {
		if( name == null || name.isEmpty() ) throw new IllegalArgumentException("Parameter error");
		this.name = name;
	}
	/*
	 * @return; name at the meta object.
	 * */
	public String getName() {
		
		return this.name;
	}
	
	
}