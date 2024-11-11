package com.project.structure;

import java.util.ArrayList;

public class Table extends MetaObjectDB  {


	//table's columns.
	private ArrayList<Column> columns;
	
	//table's primary key.
	private Index primaryKey;
	
	//table's uniques key.
	private ArrayList<Index> uniqueKeys;
	
	private ArrayList<Index> indexes;
	//table's foreign keys.
	private ArrayList<ForeignKey> foreignKeys;
	
	//table's trigger.
	private ArrayList<Trigger> triggers;
	
	public Table(String name) {
		super(name);
		this.columns = new ArrayList<Column>();
		this.uniqueKeys = new ArrayList<Index>();
		this.foreignKeys = new ArrayList<ForeignKey>();
		this.triggers = new ArrayList<Trigger>();
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

	public ArrayList<Index> getIndexes() {
		return indexes;
	}
	public void setIndexes(ArrayList<Index> indexes) {
		this.indexes = indexes;
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
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public int containsColumnName(Column c) {
		String name = "";
		int idx = 0;
		try {
			name = c.getName();
		} catch (Exception e) {
			return -1;
		}
		for (Column column : this.columns) {
			if (column.getName().equals(name)) {
				return idx;
			}
			idx++;
		}
		return -1;
	}
	
	@Override
	public String toString() {
		String result = "Table : " + this.getName() + "\n";
		
		
		result += toStringColumns();
		result += toStringPK();
		result += toStringFKs();
		result += toStringUKs();
		result += toStringIdx();
		result += toStringTrg();
		result += "____________________________________________________\n";
		return  result;
		
	}
	
	
	private String toStringColumns() {
		String res = "";
		if(this.columns != null) {
			res += "COLUMNS:\n";
			for(Column c : this.columns) {
				res += c.toString();
			}
			res += "\n";
		}
		return res;
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
	
	//string foreign key
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
		String res = "UNIQUE KEYS: \n";
		for(Index uk : this.uniqueKeys) {
			
			res += uk.toString();
			
		}
		res += "\n";
		return res;
	}
	
	// string indexes
	private String toStringIdx() {
		if(this.indexes == null || this.indexes.size() == 0)
			return "THERE ARE NOT INDEXES\n";
		
		String res = "INDEX: \n";
		for(Index uk : this.indexes) {
			res += uk.toString();
		}
		res += "\n";
		return res;
	}
	
	//string triggers
	private String toStringTrg() {
		if(this.triggers == null || this.triggers.isEmpty())
			return "THERE ARE NOT TRIGGERS. \n";
		String res = "TRIGGERS \n";
		for(Trigger t : this.triggers) {
			res += t.toString();
		}
		return res;
	}

	
}
