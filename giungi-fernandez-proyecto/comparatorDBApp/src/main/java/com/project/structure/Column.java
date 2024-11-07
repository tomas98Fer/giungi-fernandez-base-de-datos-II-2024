package com.project.structure;

public class Column extends MetaObjectDB {
	/**
	 * Column type
	 * */
	private String type;
	
	public Column(String name) {
		super(name);
	}

	/**
	 * 
	 * @param name
	 * @param type
	 */
	public Column(String name, String type) {
		super(name);
		if(type == null || type.isEmpty()) throw new IllegalArgumentException("Parameter type error");
		this.type = type;
	}
	
	/**
	 * 
	 * @return type of column.
	 */
	public String getType() {
		
		return this.type;
	}

	
	/**
	 * 	
	 * Comparator columns method.
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Column) {
			Column c = (Column) o;
			if(this.getName().compareTo(c.getName()) == 0  && this.getType().compareTo(c.getType())== 0)
				return true;
		}
	
		return false;
	}
	
	
	@Override
	public String toString() {
		String res = "[Column name: " + this.getName() + ", type: " + this.type +"]\n";
		return res;
	}
	

}
