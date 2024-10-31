package com.project.structure;

import java.util.ArrayList;

public class Procedure extends MetaObjectDB {
	

	private String returnType;
	
	private ArrayList<Param> params;
	
	public Procedure(String name) {
		super(name);
		this.returnType = "void";
		this.params = new ArrayList<Param>();
	}

	public Procedure(String name, String retType, ArrayList<Param> plist) {
		super(name);
		if(retType == null || retType.isEmpty())
			throw new IllegalArgumentException("Return type is incorrect");
		if(plist == null)
			throw new IllegalArgumentException("Param list is null");
		this.returnType = retType;
		this.params = plist;
	}
	
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public ArrayList<Param> getParams() {
		return params;
	}

	public void setParams(ArrayList<Param> params) {
		this.params = params;
	}
	
	@Override
	public boolean equals(Object o ) {
		
		if(o instanceof Procedure ) {
			Procedure p = (Procedure) o;
			if(this.name.equals(p.name)) {
				if(this.returnType.equals(p.returnType)) {
					if(this.params.size() == p.params.size()) {
						Param p1 = null;
						Param p2 = null;
						for(int i = 0 ; i < this.params.size() ; i++) {
							p1 = this.params.get(i);
							p2 = p.params.get(i);
							if(!p1.equals(p2)) {
								return false;
							}
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
		String res = "PROCEDURE: \n";
		res += "name: " + this.name + ", return: " + this.returnType + "\n";
		if(!this.params.isEmpty()) {
			for(Param p : this.params) {
				res += p.toString(); 
			}
		}
		res += "\n";
		return res;
		
		
		
	}
	
	
	

}
