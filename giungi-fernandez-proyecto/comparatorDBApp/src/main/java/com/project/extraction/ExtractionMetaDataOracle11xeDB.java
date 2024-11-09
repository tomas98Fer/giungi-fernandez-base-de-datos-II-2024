/**
 * 
 */
package com.project.extraction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.connection.ConnectionDB;
import com.project.structure.Trigger;

/**
 * @author tomas
 *
 */
public class ExtractionMetaDataOracle11xeDB extends ExtractionMetaDataDB {

	/**
	 * @param c
	 * @throws SQLException
	 */
	public ExtractionMetaDataOracle11xeDB(ConnectionDB c) throws SQLException {
		super(c);
	}

	@Override
	public ArrayList<Trigger> getTriggers(String tableName) throws SQLException {
		ArrayList<Trigger> trgList = new ArrayList<>();
		Trigger t = null;
		String query = "SELECT TRIGGER_NAME,TRIGGER_TYPE,TRIGGERING_EVENT "
				+ "FROM USER_TRIGGERS "
				+ "WHERE TABLE_NAME = " + "'" + tableName + "';";
		Statement s = this.getConnectionDB().getConnection().createStatement();
		ResultSet rs = s.executeQuery(query);
		while(rs.next()) {
			t = new Trigger(rs.getString("trigger_name"), rs.getString("trigger_type") , rs.getString("triggering_event"));
			trgList.add(t);
		}
		return trgList;
	}

}
