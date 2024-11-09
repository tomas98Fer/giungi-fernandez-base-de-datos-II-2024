package com.project.extraction;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project.connection.ConnectionDB;
import com.project.structure.Column;
import com.project.structure.ForeignKey;
import com.project.structure.Index;
import com.project.structure.Param;
import com.project.structure.Procedure;
import com.project.structure.Table;
import com.project.structure.Trigger;


/**
 * 
 * @author Tomas Fernandez
 * @author Agustin Giungi
 *
 *Abstract class defines meta data extractor objects.
 */
public abstract class ExtractionMetaDataDB {
	
	private ConnectionDB c = null;
	
	private DatabaseMetaData metaData = null;
	
	/**
	 * 
	 * @param c ConnectionDB connection information. The conncetion has been created.
	 * @throws SQLException
	 */
	public ExtractionMetaDataDB(ConnectionDB c) throws SQLException{
		if(c == null) 
			throw new IllegalArgumentException("Connection must have been alredy created");
		this.c = c;
		metaData = c.getConnection().getMetaData();
		
	}
	
	/**
	 * Getter connection method. 
	 * @return ConnectionDB with the data base.
	 */
	
	public ConnectionDB getConnectionDB() {
		return this.c;
	}
	
	
	/**
	 * Extract metada  table information of database.
	 * @param catalog name.
	 * @param schema name.
	 * @return list of Tables.
	 * @throws SQLException
	 */
	
	public ArrayList<Table> extractTables() throws SQLException {
		String catalogName = c.getDatabaseName();
		String schemaName = c.getSchema();
		String[] typeTable = {"TABLE"};	
		System.out.println("PREVIO A OBTENER INFORMACION DE LAS TABLAS");
		ResultSet rsTables = metaData.getTables(catalogName, schemaName, null, typeTable);
		System.out.println("OBTUVE INFORMACION DE LAS TABLAS");
		ArrayList<Table> tableList = new ArrayList<Table>();
		String tableName = null;
		ArrayList<Column> columns = null;
		ArrayList<Trigger> triggers = null;
		Table t;
		
		while(rsTables.next()) {
			tableName = rsTables.getString("TABLE_NAME");
			columns = extractColumns(tableName);
			Index pk = extractPrimaryKey(tableName , columns);
			triggers = getTriggers(tableName);
			
			t = new Table(tableName);
			t.setColumns(columns);
			t.setTriggers(triggers);
			
			t.setPrimaryKey(pk);
			tableList.add(t);
			
			
		}
	
		ArrayList<ForeignKey> fkList = null;
		ArrayList<Index> idxList = null;
		//Set all foreign keys for each table
		//Set all unique keys for each table
		for(int i = 0; i < tableList.size(); i++ ) {
			
			fkList = extractForeignsKeys(tableList.get(i).getName(),tableList);
			tableList.get(i).setForeignKeys(fkList);
			idxList = extractUniqueKeys(tableList.get(i).getName(),tableList.get(i).getColumns());
			tableList.get(i).setUniqueKeys(idxList);
			idxList = extractIndexes(tableList.get(i).getName(),tableList.get(i).getColumns());
			tableList.get(i).setIndexes(idxList);;
		}
		
		
		
		return tableList;
		 
	 }
	
	

	/**
	 * Extract columns information.
	 * @param tableName name of the table where columns belong.
	 * @return List with columns of the table.
	 * @throws SQLException
	 */
	public ArrayList<Column> extractColumns(String tableName) throws SQLException {	
		
		ResultSet rsColumns = metaData.getColumns(c.getDatabaseName() , c.getSchema(), tableName , null);
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
	
	/**
	 * Extract primary key information of the table given the name table 
	 * @param schemaName can be null
	 * @param tableName name of the table.
	 * @param columnsTable List columns that wer already extracted.
	 * @return primary Key Information or null
	 * @throws SQLException
	 */
	public Index extractPrimaryKey(String tableName , ArrayList<Column> columnsTable) throws SQLException {
		ResultSet rsColumnsPK = metaData.getPrimaryKeys(c.getDatabaseName() , c.getSchema() , tableName);
		ArrayList<Column> columnPKList = new ArrayList<Column>();
		String namePK = null;
		Column c;
		String nameC;
		boolean existPK = false;
		while(rsColumnsPK.next()) {
			existPK = true;
			namePK = rsColumnsPK.getString("PK_NAME");
			
			nameC = rsColumnsPK.getString("COLUMN_NAME");
			
			 c = getColumn(nameC,columnsTable);
			if(c != null)
				columnPKList.add(c);
			
		}
		if(existPK)
			return new Index(namePK,columnPKList);
		return null;
	}
	
	/**
	 * @param tableName
	 * @param tableList
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<ForeignKey> extractForeignsKeys( String tableName, ArrayList<Table> tableList) throws SQLException {
		ResultSet rsFKs = metaData.getImportedKeys(c.getDatabaseName() , c.getSchema() , tableName);
		ArrayList<ForeignKey> fks = new ArrayList<>();
		String oldFKname = "";
		String  currentFKname = "";
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


	
	/**
	 * Extract the unique keys of the table.
	 * @param schemaName schema name. Can be null.
	 * @param tableName name of the table
	 * @param columnsTable list of columns that was already extracted.
	 * @return return all unique keys list in the table.Can be empty .
	 * @throws SQLException 
	 */
	public ArrayList<Index> extractUniqueKeys( String tableName , ArrayList<Column> columnsTable) throws SQLException{
		ResultSet rsUniqueKeys = metaData.getIndexInfo(c.getDatabaseName() , c.getSchema() , tableName ,true , true);
		ArrayList<Index> uniqueKeysList = new ArrayList<Index>();
		String oldUniqueKeyName = "";
		String currentUniqueKeyName = "";
		String columnName = "";
		Column c = null;
		Index idx = null;
		while(rsUniqueKeys.next()) {
			
			currentUniqueKeyName = rsUniqueKeys.getString("INDEX_NAME");
			if(currentUniqueKeyName != null) {
				if(!currentUniqueKeyName.equals(oldUniqueKeyName)) {
					oldUniqueKeyName = currentUniqueKeyName;
					idx = new Index(currentUniqueKeyName);
					uniqueKeysList.add(idx);
				}
				columnName = rsUniqueKeys.getString("COLUMN_NAME");
				c = getColumn(columnName,columnsTable);
				idx.getColumns().add(c);
			}
		}
		
		return uniqueKeysList;
	}
	
	/**
	 * Extract indexes of a table
	 * @param tableName name of the table
	 * @param columnsTable columns of table that was already extracted.
	 * @return List of indexes in the table.
	 * @throws SQLException
	 */
	public ArrayList<Index> extractIndexes( String tableName,ArrayList<Column> columnsTable) throws SQLException{
		ResultSet rsIndexes = metaData.getIndexInfo(c.getDatabaseName() , c.getSchema() , tableName , false , true);
		ArrayList<Index> indexesList = new ArrayList<Index>();
		String oldIndexName = "";
		String currentIndexName = "";
		String columnName = "";
		Column c = null;
		Index idx = null;

		while(rsIndexes.next()) {
			currentIndexName = rsIndexes.getString("INDEX_NAME");
			if(currentIndexName != null) {
				if(rsIndexes.getBoolean("NON_UNIQUE")) {
					if(!currentIndexName.equals(oldIndexName)) {
						oldIndexName = currentIndexName;
						idx = new Index(currentIndexName);
						indexesList.add(idx);
					}
					columnName = rsIndexes.getString("COLUMN_NAME");
					c = getColumn(columnName,columnsTable);
					idx.getColumns().add(c);
				}
			}
		
		}
		
		return indexesList;
	}
	
	/**
	 * Extractor trigger method.
	 * @param tableName name of the table
	 * @return List of trigger in a data base.
	 * @throws SQLException 
	 */
	public abstract ArrayList<Trigger> getTriggers(String tableName) throws SQLException;
	
	
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

	
	public ArrayList<Procedure> extractStoredProcedures() {
		String dbname = this.c.getDatabaseName();
		String schema = this.c.getSchema();
		ArrayList<Procedure> result = new ArrayList<Procedure>();
		ArrayList<Procedure> p = extractProcedures(dbname , schema);
		ArrayList<Procedure> f = extractStoredFunctions(dbname , schema);
		if(!p.isEmpty())
			result.addAll(p);
		if( !f.isEmpty())
			result.addAll(f);
		return result;
	}
	
	
	private ArrayList<Procedure> extractStoredFunctions(String dbname , String schema) {
		ResultSet rsFunctions = null;
		ResultSet rsParams = null;
		String nameFunction = null;
		String returnType = "void";
		ArrayList<Procedure> funcList = new ArrayList<Procedure>();
		ArrayList<Param> plist = null;
		Param param = null;
		int colType = -1;
		
		try{
			rsFunctions = this.metaData.getFunctions(dbname , schema, null);
			while(rsFunctions.next()) {
				nameFunction = rsFunctions.getString("FUNCTION_NAME");
				plist = new ArrayList<Param>();
				rsParams = this.metaData.getFunctionColumns(dbname, schema, nameFunction, null);
				while(rsParams.next()) {
					colType = rsParams.getInt("COLUMN_TYPE");
					if( colType == 4 || colType == 5) {
						returnType = rsParams.getString("TYPE_NAME");
					}
					else {
						param = new Param(rsParams.getString("COLUMN_NAME"),
								rsParams.getInt("COLUMN_TYPE"),
								rsParams.getString("TYPE_NAME"));
						plist.add(param);
					}
					
				}
				funcList.add(new Procedure(nameFunction , returnType , plist));
			} 
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return funcList;
	}

	
	//extractor procedure method. Extract procedure stored not function.
	private ArrayList<Procedure> extractProcedures(String dbname, String schema) {
		ResultSet rsProced = null;
		ResultSet rsParams = null;
		String name_proced = null;
		String returnType = "void";
		Param param = null;
		ArrayList<Param> plist = null;
		ArrayList<Procedure> procList = new ArrayList<Procedure>();
		int colType = -1;
		try {
			rsProced = this.metaData.getProcedures(dbname,schema,null);
			while(rsProced.next()) {
				
				name_proced = rsProced.getString("PROCEDURE_NAME");
				plist = new ArrayList<Param>();
				rsParams = this.metaData.getProcedureColumns(dbname, schema, name_proced, null);
				
				while(rsParams.next()) {
					colType = rsParams.getInt("COLUMN_TYPE");
					if( colType == 3 || colType == 5 ) {
						
						returnType = rsParams.getString("TYPE_NAME");
					}
					else {
						param = new Param(rsParams.getString("COLUMN_NAME"),
								rsParams.getInt("COLUMN_TYPE"),
								rsParams.getString("TYPE_NAME"));
						plist.add(param);
					}
				}
				
				procList.add(new Procedure(name_proced , returnType , plist));
				

				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return procList;
	}


	

}
