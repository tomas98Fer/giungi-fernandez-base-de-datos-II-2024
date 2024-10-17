package com.proyect.connection;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.proyect.structure.Column;
import com.proyect.structure.Index;
import com.proyect.structure.Table;



public class ExtractionMetaDataDB {
	
	private DatabaseMetaData metaData = null;
	
	public ExtractionMetaDataDB(ConnectionDB c) throws SQLException{
		metaData = c.getConnection().getMetaData();
	}
	

	
	
	/**
	 * Extract metada  table information of database.
	 * @param schema name.
	 * @return list of Tables.
	 * @throws SQLException
	 */
	
	public ArrayList<Table> getTables(String schemaName) throws SQLException {
		String[] typeTable = {"TABLE"};		
		ResultSet rsTables = metaData.getTables(null, schemaName, null, typeTable);
		ArrayList<Table> tableList = new ArrayList<Table>();
		while(rsTables.next()) {
			String tableName = rsTables.getString("TABLE_NAME");
			ArrayList<Column> columns = getColumns(schemaName , tableName);
			Index pk = getPrimaryKey(schemaName, tableName, columns);
			Table t = new Table(tableName);
			t.setColumns(columns);
			
			t.setPrimaryKey(pk);
			tableList.add(t);
			
		}
		
		
		return tableList;
		 
	 }
	
	
	public ArrayList<Column> getColumns(String schemaName, String tableName) throws SQLException {	
		ResultSet rsColumns = metaData.getColumns(null , schemaName , tableName , null);
		ArrayList<Column> columnList = new ArrayList<Column>();
		while(rsColumns.next()) {
			String nameC = rsColumns.getString("COLUMN_NAME");
			String typeC = rsColumns.getString("TYPE_NAME");
			columnList.add(new Column(nameC , typeC));
		}
		
		return columnList;
		 
	 }
	
	public Index getPrimaryKey(String schemaName, String tableName,ArrayList<Column> columnsTable) throws SQLException {
		ResultSet rsColumnsPK = metaData.getPrimaryKeys(null , schemaName , tableName);
		ArrayList<Column> columnPKList = new ArrayList<Column>();
		String namePK = null;
		while(rsColumnsPK.next()) {
			if(namePK == null)
				namePK = rsColumnsPK.getString("PK_NAME");
			
			String nameC = rsColumnsPK.getString("COLUMN_NAME");
			
			Column c = getColumn(nameC,columnsTable);
			if(c != null)
				columnPKList.add(c);
			
		}
		
		return new Index(namePK,columnPKList);
		
	}
	
	
	// get a Column of a list of Columns given a name
	private static Column getColumn(String name, ArrayList<Column> listC ) {
		for(Column c : listC) {
			if(c.getName().equals(name))
				return c;
		}
		return null;
		
	}
	


}
