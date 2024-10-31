package com.project.structure;

public class Param extends MetaObjectDB{
	
	private String dataType;
	private TYPE_PARAM typeParam;

	public Param(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param name name of the param.
	 * @param typePar type param: IN,OUT,INOUT
	 * @param datType data type of param int4,varchar,...
	 */
	public  Param(String name , int typePar , String datType) {
		super(name);
		if(datType == null || datType.isEmpty())
			throw new IllegalArgumentException("Data type is invalid");
		if(typePar < 0 && typePar > 5)
			throw new IllegalArgumentException("Type param is invalid");
		this.dataType = datType;
		this.typeParam = toTypeParam(typePar);
	}
	
	/**
	 * getter DataType method.
	 * @return name of date type param.
	 */
	
	public String getDataType() {
		return dataType;
	}

	/**
	 * getter typeParam method.
	 * @return type of param.
	 */
	public TYPE_PARAM getTypeParam() {
		return typeParam;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Param) {
			Param p = (Param) o;
			return this.dataType.equals(p.dataType) && this.typeParam == p.getTypeParam();
		}
		
		return false;
		
	}
	
	@Override
	public String toString() {
		String res = "PARAM:\n";
		res += "name: " + this.name + ", type: " + this.typeParam + ", data_type: " + this.dataType ; 
		return res;
	}

	private TYPE_PARAM toTypeParam(int typePar){
		
		switch(typePar) {
			case 0 :
				return TYPE_PARAM.UNKNOW;
			case 1:
				return TYPE_PARAM.IN;
			case 2:
				return TYPE_PARAM.INOUT;
			case 3:
				return TYPE_PARAM.COLUMN;
			case 4:
				return TYPE_PARAM.OUT;
			case 5:
				return TYPE_PARAM.RETURN;
			
			default: return TYPE_PARAM.UNKNOW;
			
			
		}
		
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}














