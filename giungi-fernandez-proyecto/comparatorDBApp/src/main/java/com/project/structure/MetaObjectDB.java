package com.project.structure;


/*
 * Class that has finallity of  represent an sql datbase artefact
 * like a schemas,columns,etc  
 */

public class MetaObjectDB {

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
	
	public void setName(String name) {
		if( name == null || name.isEmpty() ) throw new IllegalArgumentException("Name Canot be null or empty");
		this.name = name;
	}
	
	@Override
	public String toString() {
		
		return "MetaObjectDB name: " + this.name ;
		
	}
	
}