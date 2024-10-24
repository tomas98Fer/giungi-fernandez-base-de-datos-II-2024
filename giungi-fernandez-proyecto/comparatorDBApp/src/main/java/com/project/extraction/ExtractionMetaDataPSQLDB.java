package com.project.extraction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.connection.ConnectionDB;
import com.project.structure.Trigger;

public class ExtractionMetaDataPSQLDB extends ExtractionMetaDataDB {

	public ExtractionMetaDataPSQLDB(ConnectionDB c) throws SQLException {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Trigger> getTriggers(String tableName) throws SQLException {
		ArrayList<Trigger> trgList = new ArrayList<>();
		Trigger t = null;
		String query = "SELECT \n"
				+ "  tg.tgname as trigger_name, \n"
				+ "  COALESCE(\n"
				+ "    CASE WHEN (tgtype::int::bit(7) & b'0000010')::int = 0 THEN NULL ELSE 'BEFORE' END,\n"
				+ "    CASE WHEN (tgtype::int::bit(7) & b'0000010')::int = 0 THEN 'AFTER' ELSE NULL END,\n"
				+ "    CASE WHEN (tgtype::int::bit(7) & b'1000000')::int = 0 THEN NULL ELSE 'INSTEAD' END,\n"
				+ "    ''\n"
				+ "  )::text as cond_timing, \n"
				+ "    (CASE WHEN (tgtype::int::bit(7) & b'0000100')::int = 0 THEN '' ELSE 'INSERT' END) ||\n"
				+ "    (CASE WHEN (tgtype::int::bit(7) & b'0001000')::int = 0 THEN '' ELSE 'DELETE' END) ||\n"
				+ "    (CASE WHEN (tgtype::int::bit(7) & b'0010000')::int = 0 THEN '' ELSE 'UPDATE' END) ||\n"
				+ "    (CASE WHEN (tgtype::int::bit(7) & b'0100000')::int = 0 THEN '' ELSE 'TRUNCATE' END)\n"
				+ "  as cond_event\n"
				+ "FROM pg_catalog.pg_trigger tg\n"
				+ "JOIN  pg_catalog.pg_class tab ON  tg.tgname NOT LIKE 'RI_Constraint_%' and tab.oid = tg.tgrelid and tab.relname = '"+ tableName +"' ;";
		Statement s = this.getConnectionDB().getConnection().createStatement();
		ResultSet rs = s.executeQuery(query);
		while(rs.next()) {
			t = new Trigger(rs.getString("trigger_name"), rs.getString("cond_timing") , rs.getString("cond_event"));
			trgList.add(t);
		}
		return trgList;
		
	}

}
