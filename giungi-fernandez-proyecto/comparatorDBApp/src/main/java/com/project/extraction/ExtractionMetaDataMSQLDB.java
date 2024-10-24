package com.project.extraction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.connection.ConnectionDB;
import com.project.structure.Trigger;

public class ExtractionMetaDataMSQLDB extends ExtractionMetaDataDB {

	public ExtractionMetaDataMSQLDB(ConnectionDB c) throws SQLException {
		super(c);
	}

	@Override
	public ArrayList<Trigger> getTriggers(String tableName) throws SQLException {
		String dbN = this.getConnectionDB().getDatabaseName();
		ArrayList<Trigger> trgList = new ArrayList<>();
		Trigger t = null;
		String query = "SELECT TRIGGER_NAME AS NAME, ACTION_TIMING AS TIMING, EVENT_MANIPULATION AS EVENT \n"
				+ "FROM INFORMATION_SCHEMA.TRIGGERS \n"
				+ "WHERE TRIGGER_SCHEMA = " + "'" + dbN + "'" + " and EVENT_OBJECT_TABLE = " + "'" + tableName + " '" + ";";
		Statement s = this.getConnectionDB().getConnection().createStatement();
		ResultSet rs = s.executeQuery(query);
		while(rs.next()) {
			t = new Trigger(rs.getString("NAME"), rs.getString("TIMING") , rs.getString("EVENT"));
			trgList.add(t);
		}
		return trgList;
	}

}
