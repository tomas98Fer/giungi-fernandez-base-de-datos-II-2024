package com.project.structure;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;


public class TableTest {
    
    @Test
    public void containsColumNameTest(){
        Table table = new Table("table1");
        Column column = new Column("column1, int4");
        ArrayList<Column> columns = new ArrayList<>();
        columns.add(column);
        table.setColumns(columns);
        assertTrue(table.containsColumnName(column)>=0);
    }

    @Test
    public void notContainsColumNameTest(){
        Table table = new Table("table1");
        Column column = new Column("column1", "int4");
        ArrayList<Column> columns = new ArrayList<>();
        columns.add(column);
        table.setColumns(columns);
        Column column2 = new Column("column2", "varchar");
        assertTrue(table.containsColumnName(column2)==-1);
    }

}