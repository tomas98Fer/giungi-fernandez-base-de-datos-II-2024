package com.project.extraction;

import com.project.structure.*;

import java.sql.SQLException;
import java.util.ArrayList;

import com.project.connection.*;

public class DBModelFactory {

	
	
	public static DBModel generateDBModel(ConnectionDB c) throws SQLException {
		
		if(c == null)
			throw new IllegalArgumentException("Connection can be null");
		DBModel dbm = new DBModel();
		ExtractionMetaDataDB extdb = null;
		if(c.getEngine().equals("postgres"))
			extdb = new ExtractionMetaDataPSQLDB(c);
		
		if(c.getEngine().equals("mysql"))
			extdb = new ExtractionMetaDataMSQLDB(c);
		
		ArrayList<Table> l = extdb.extractTables();
		dbm.setTables(l);
		dbm.setProcedures(extdb.extractStoredProcedure());
		
		
		return dbm;
		
		
	}
}
