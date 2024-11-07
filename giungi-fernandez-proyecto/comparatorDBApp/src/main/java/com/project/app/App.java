package com.project.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import com.project.connection.ConnectionDB;
import com.project.extraction.DBModelFactory;
import com.project.structure.DBModel;
import com.project.structure.Procedure;
import com.project.structure.Table;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
    	String [] dataUser1 = new String [6];
    	String [] dataUser2 = new String [6];
    	int response = -1;
        Scanner s = new Scanner(System.in);
    	response = menu(s);
        switch(response) {
        	case 1:
        		//load data manually;
        		System.out.println("DATOS DEL USUARIO 1");
        		dataUser1 = loadDataUser(s);
        		
        		System.out.println("DATOS DEL USUARIO 2");
        		dataUser2 = loadDataUser(s);
        		
        		s.close();
        		
        	break;
        	
        	case 2:
        		loadFromConfigFile(dataUser1,dataUser2);
        		
        	break;
        	
        	case 3:
        		System.out.println("Saliendo...");
        		return ;
        
        }
		
        
        //check if  information is incorrect
       /* if(dataUsers1 == null && response)
        	throw new IllegalArgumentException("Error loading config data.");
       */
        try {
        	
			ConnectionDB c1 = new ConnectionDB(dataUser1[0],dataUser1[1],dataUser1[2],dataUser1[3],dataUser1[4],dataUser1[5]);
			ConnectionDB c2 = new ConnectionDB(dataUser2[0],dataUser2[1],dataUser2[2],dataUser2[3],dataUser2[4],dataUser2[5]);
			DBModel dbm1 = DBModelFactory.generateDBModel(c1);
			DBModel dbm2 = DBModelFactory.generateDBModel(c2);
			
			System.out.println("Estructura de " + c1.getDatabaseName());
			for(Table t: dbm1.getTables()) {
				 System.out.println(t.toString());
			 }
			
			for(Procedure p : dbm1.getProcedures()) {
				System.out.println(p.toString());
			}
			
			System.out.println("Estructura de " + c2.getDatabaseName());
			for(Table t: dbm2.getTables()) {
				 System.out.println(t.toString());
			 }
			
			for(Procedure p : dbm2.getProcedures()) {
				System.out.println(p.toString());
			}
        
        
        } catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
      
    }
    
    
    private static void loadFromConfigFile( String [] a, String [] b) {
	   Properties properties = new Properties();
        try (InputStream input = App.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException ("Lo siento, no pude encontrar el archivo config.properties");
            }

            // Load data from config.properties file
            properties.load(input);
            a[0] = properties.getProperty("engine1");
            a[1] = properties.getProperty("host1");
            a[2] = properties.getProperty("dbname1");
            a[3] = properties.getProperty("username1");
            a[4] = properties.getProperty("password1");
            a[5] = properties.getProperty("schema1","");
            
            b[0] = properties.getProperty("engine2");
            b[1] = properties.getProperty("host2");
            b[2] = properties.getProperty("dbname2");
            b[3] = properties.getProperty("username2");
            b[4] = properties.getProperty("password2");
            b[5] = properties.getProperty("schema2","");
            if(!a[0].equals(b[0])) 
            	throw new IllegalStateException("Both data base must have the same engine.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    
    private static String[] loadDataUser(Scanner s) {
		String [] result = new String [6];
		String schema = null;
		
		System.out.println("INGRESE EL NOMBRE DEL MOTOR." 
				+ "PUEDE SER: 'postgres', 'mysql' o 'oracle11xe'.");
		result[0] = s.nextLine();
		System.out.println("INGRESE EL HOST.");
		result[1] = s.nextLine();
		System.out.println("INGRESE EL NOMBRE DE LA BASE DE DATOS.");
		result[2] = s.nextLine();
		System.out.println("INGRESE EL NOMBRE DE USUARIO.");
		result[3] = s.nextLine();
		System.out.println("INGRESE CONTRASEÑA.");
		result[4] = s.nextLine();
		System.out.println("\n\n");
		System.out.println("INGRESE NOMBRE DEL SCHEMA, CASO CONTRARIO, ENTER.");
		schema = s.nextLine();
		if(!schema.isEmpty()) {
			System.out.println("INGRESO UN NOMBRE DE SCHEMMA");
			result[5] = schema;
			
		}
		else {
			result[5] = null;
		}
		return result;
		
		
	}



	// menu
    private static int menu(Scanner s) {
    	System.out.println("DENTRO DEL METODO DEL MENU");
        int response = -1;
        while ( response < 1  || response > 3 ) {
            System.out.println("__________ MENÚ________________");
            System.out.println("\n");
            System.out.println("1) CARGAR USUARIOS MANUALMENTE");
            System.out.println("2) USAR DATOS  DEL ARCHIVO CONFIGURACIÓN");
            System.out.println("3) SALIR");
            System.out.println("\n");
            response = s.nextInt();
            s.nextLine();
        }
        return response;
    }
}


