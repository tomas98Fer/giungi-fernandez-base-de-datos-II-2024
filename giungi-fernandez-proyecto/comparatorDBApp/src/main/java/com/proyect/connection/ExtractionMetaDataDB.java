package com.proyect.connection;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.proyect.structure.Column;
import com.proyect.structure.ForeignKey;
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
		String tableName = null;
		ArrayList<Column> columns = null;
		Table t;
		
		while(rsTables.next()) {
			tableName = rsTables.getString("TABLE_NAME");
			columns = getColumns(schemaName , tableName);
			Index pk = getPrimaryKey(schemaName, tableName, columns);
			
			t = new Table(tableName);
			t.setColumns(columns);
			
			t.setPrimaryKey(pk);
			tableList.add(t);
			
		}
		//Set all foreign key for each table
		for(int i = 0; i < tableList.size(); i++ ) {
			
			ArrayList<ForeignKey> fkList = getForeignsKeys(schemaName,tableList.get(i).getName(),tableList);
			tableList.get(i).setForeignKeys(fkList);
			
		}
		
		
		
		return tableList;
		 
	 }
	
	
	private ArrayList<ForeignKey> getForeignsKeys(String schemaName, String tableName, ArrayList<Table> tableList) throws SQLException {
		ResultSet rsFKs = metaData.getImportedKeys(null , schemaName , tableName);
		if(!rsFKs.next()) {
			return null;
		}
		rsFKs.previous(); 	//back pointer to the begin.
		
		ArrayList<ForeignKey> fks = new ArrayList<>();
		String oldFKname = "";
		String  currentFKname= "";
		Table t = getTable(tableName,tableList); 
		Table refTable = null;
		ForeignKey currentFK = null;
		String fkColumnName = null;
		String refColumnName = null;
		String refTableName = null;
		Column c = null;
		Column refC = null;
		while(rsFKs.next()) {
			currentFKname = rsFKs.getString("FK_NAME");
			refTableName = rsFKs.getString("PKTABLE_NAME");
			fkColumnName = rsFKs.getString("FKCOLUMN_NAME");
			refColumnName = rsFKs.getString("PKCOLUMN_NAME");
			if(!currentFKname.equals(oldFKname)) {
				oldFKname = currentFKname;
				currentFK = new ForeignKey(currentFKname);
				refTable = getTable(refTableName,tableList);
				currentFK.setReference(refTable);
				fks.add(currentFK);
				
			}
			c = getColumn(fkColumnName,t.getColumns());
			currentFK.getColumns().add(c);
			refC = getColumn(refColumnName,refTable.getColumns());
			currentFK.getReferencedColumns().add(refC);
		}	
		
		return fks;
	}




	public ArrayList<Column> getColumns(String schemaName, String tableName) throws SQLException {	
		
		ResultSet rsColumns = metaData.getColumns(null , schemaName , tableName , null);
		ArrayList<Column> columnList = new ArrayList<Column>();
		String nameC = null;
		String typeC = null;
		while(rsColumns.next()) {
			nameC = rsColumns.getString("COLUMN_NAME");
			typeC = rsColumns.getString("TYPE_NAME");
			columnList.add(new Column(nameC , typeC));
		}
		
		return columnList;
		 
	 }
	
	public Index getPrimaryKey(String schemaName, String tableName,ArrayList<Column> columnsTable) throws SQLException {
		ResultSet rsColumnsPK = metaData.getPrimaryKeys(null , schemaName , tableName);
		ArrayList<Column> columnPKList = new ArrayList<Column>();
		String namePK = null;
		Column c;
		String nameC;
		if(!rsColumnsPK.next()) {
			return null;
		}
		else {
			rsColumnsPK.previous();
		}
		while(rsColumnsPK.next()) {
			if(namePK == null)
				namePK = rsColumnsPK.getString("PK_NAME");
			
			nameC = rsColumnsPK.getString("COLUMN_NAME");
			
			 c = getColumn(nameC,columnsTable);
			if(c != null)
				columnPKList.add(c);
			
		}
		return new Index(namePK,columnPKList);
		
	}
	
	
	// get a Column of a list of Columns given a name
	private static Column getColumn(String name , ArrayList<Column> listC ) {
		for(Column c : listC) {
			if(c.getName().equals(name))
				return c;
		}
		return null;
		
	}
	
	//get a table of a list of Tables given a name
	private static Table getTable(String name , ArrayList<Table> listT) {
		for(Table t : listT) {
			if(t.getName().equals(name))
				return t;

		}
		return null;
	}


}
