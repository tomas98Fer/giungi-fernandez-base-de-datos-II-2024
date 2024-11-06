package com.project.extraction;

import com.project.structure.*;

import java.sql.SQLException;
import java.util.ArrayList;

import com.project.connection.*;

public class DBModelFactory {

	/**
	 * Fabric DBModel object. choice the correct factory acording
	 * engine name.
	 * @param c conecction with the database.
	 * @return DBModel
	 * @throws SQLException
	 */
	
	public static DBModel generateDBModel(ConnectionDB c) throws SQLException {
		
		if(c == null)
			throw new IllegalArgumentException("Connection can be null");
		DBModel dbm = new DBModel(c.getDatabaseName());
		ExtractionMetaDataDB extdb = null;
		if(c.getEngine().equals("postgres"))
			extdb = new ExtractionMetaDataPSQLDB(c);
		
		if(c.getEngine().equals("mysql"))
			extdb = new ExtractionMetaDataMSQLDB(c);
		
		//we should add choice to oracle 11xe engine
		ArrayList<Table> l = extdb.extractTables();
		dbm.setTables(l);
		dbm.setProcedures(extdb.extractStoredProcedure());
		
		
		return dbm;
		
		
	}
}
