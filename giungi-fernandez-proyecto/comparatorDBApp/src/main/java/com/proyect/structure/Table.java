package com.proyect.structure;

import java.util.ArrayList;

public class Table extends MetaObjectDB  {


	//table's columns.
	private ArrayList<Column> columns;
	
	//table's primary key.
	private Key primaryKey;
	//table's uniques key.
	private ArrayList<Key> uniqueKeys;
	
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
	public Key getPrimaryKey() {
		return primaryKey;
	}
	
	/**
	 * Setter primaryKey method
	 * @param primary key of the table.
	 */
	public void setPrimaryKey(Key primaryKey) {
		this.primaryKey = primaryKey;
	}
	/**
	 * Getter unique keys method
	 * @return List of unique keys at the table.
	 */
	public ArrayList<Key> getUniqueKeys() {
		return uniqueKeys;
	}
	/**
	 * Setter unique keys of the table.
	 * @param unique keys of the table.
	 */
	public void setUniqueKeys(ArrayList<Key> uniqueKeys) {
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


	
	
	
	
}
