package com.proyect.structure;

import java.util.ArrayList;

public class Table extends MetaObjectDB  {


	//table's columns.
	private ArrayList<Column> columns;
	
	//table's primary key.
	private Index primaryKey;
	//table's uniques key.
	private ArrayList<Index> uniqueKeys;
	
	//table's foreign keys.
	private ArrayList<ForeignKey> foreignKeys;
	
	//table's trigger.
	private ArrayList<Trigger> triggers;
	
	public Table(String name) {
		super(name);
	}
	/**
	 * 
	 * @return columns of  the table;
	 */
	public ArrayList<Column> getColumns() {
		return columns;
	}
	
	/**
	 * Setter Columns of table.
	 * @param columns.
	 */
	public void setColumns(ArrayList<Column> columns) {
		this.columns = columns;
	}
	
	/**
	 * Getter primaryKey method.
	 * @return primaryKey.
	 */
	public Index getPrimaryKey() {
		return primaryKey;
	}
	
	/**
	 * Setter primaryKey method
	 * @param primary key of the table.
	 */
	public void setPrimaryKey(Index primaryKey) {
		this.primaryKey = primaryKey;
	}
	/**
	 * Getter unique keys method
	 * @return List of unique keys at the table.
	 */
	public ArrayList<Index> getUniqueKeys() {
		return uniqueKeys;
	}
	/**
	 * Setter unique keys of the table.
	 * @param unique keys of the table.
	 */
	public void setUniqueKeys(ArrayList<Index> uniqueKeys) {
		this.uniqueKeys = uniqueKeys;
	}

	/**
	 * Getter foreign key method.
	 * @return foreign keys of the table.
	 */
	public ArrayList<ForeignKey> getForeignKeys() {
		return foreignKeys;
	}
	/**
	 * Setter foreign keys method.
	 * @param foreign Keys of the table
	 */
	public void setForeignKeys(ArrayList<ForeignKey> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}
	
	/**
	 *Getter triggers method.
	 * @return triggers of the table.
	 */
	public ArrayList<Trigger> getTriggers() {
		return triggers;
	}
	
	/**
	 * Setter triggers method.
	 * @param triggers of the table.
	 */
	public void setTriggers(ArrayList<Trigger> triggers) {
		this.triggers = triggers;
	}

	/**
	 * 
	 * @param idx is other PrimaryKey.
	 * @return true iff both primary keys are equals.
	 * @return false in otherwise.
	 */
	public boolean equalsPK(Index pk) {
		
		
		return this.primaryKey.getName().equals(pk.getName()) && this.primaryKey.equals(pk);
	}
	
	
	@Override
	public String toString() {
		String result = "Table : " + this.getName() + "\n";
		for(Column c : this.columns) {
			result += c.toString() ;
			
		}
		result += "\n";
		result += toStringPK();
		result += toStringFKs();
		result += toStringUKs();
		result += "\n\nEnd  Table Iformation.\n";
		return  result;
		
	}
	
	//string primary key
	private String toStringPK() {
		String res = "PRIMARY KEY:\n";
		if(this.primaryKey != null) {
			res += "name: " + this.primaryKey.getName() + "\n";
			for(Column c : this.primaryKey.getColumns()) {
				res+= c.toString();
			}
		}
		else {
			res += "The table hasn't primary key.\n";
		}
		return res;
	}
	
	//string primary key
	private String toStringFKs() {
		if(this.foreignKeys == null)
			return "FOREIGN KEY: The table hasn't FOREIGN KEYS.\n";
		String res = "";
		for(ForeignKey fk : this.foreignKeys) {
			res += fk.toString();
		}
		res += "\n";
		return res;
	}
	
	// string unique keys
	private String toStringUKs() {
		if(this.uniqueKeys == null)
			return "UNIQUE KEY: The table hasn't UNIQUE KEYS.\n";
		String res = "UNIQUE KEYS";
		for(Index uk : this.uniqueKeys) {
			res += uk.toString();
		}
		res += "\n";
		return res;
	}
	
}
