/**
 * The Effusion program implements the use of sqlite and mysql library
 * for database operations.
 */
package org.techzenth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Andre_Bonner
 * @version 1.0
 * @since July 7, 2014
 * 
 * The main class of the Effusion program.
 */
public class Effusion {
	
	 // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/data_db";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "usbw";
	
	/**
	 * This is the main method class which does the loading.
	 * @param args
	 * @return Nothing
	 */
	public static void main(String[] args) {
		/****
		 * SQLite
		 * */
	    Connection c = null;
	    Statement stmt = null;
	    try {
	    	System.out.println("=====SQLite=====");
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:effusion.db");
		  System.out.println("Opened database successfully");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
	      stmt.executeUpdate(sql);
	      c.commit();

	      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
	      while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         int age  = rs.getInt("age");
	         String  address = rs.getString("address");
	         float salary = rs.getFloat("salary");
	         System.out.println( "ID = " + id );
	         System.out.println( "NAME = " + name );
	         System.out.println( "AGE = " + age );
	         System.out.println( "ADDRESS = " + address );
	         System.out.println( "SALARY = " + salary );
	         System.out.println();
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully\n");
	  
	/**********
	 * MySQL
	 * */
	    
	  
	    Connection conn = null;
	    stmt = null;
	    System.out.println("=====MySQL=====");
	    try{
	       //STEP 2: Register JDBC driver
	       Class.forName(JDBC_DRIVER);

	       //STEP 3: Open a connection
	       System.out.println("Connecting to database...");
	       conn = DriverManager.getConnection(DB_URL,USER,PASS);

	       //STEP 4: Execute a query
	       System.out.println("Creating statement...\n");
	       stmt = conn.createStatement();
	       String sql;
	       sql = "SELECT id, first_name, last_name, email FROM people";
	       ResultSet rs = stmt.executeQuery(sql);

	       //STEP 5: Extract data from result set
	       while(rs.next()){
	          //Retrieve by column name
	          int id  = rs.getInt("id");
	          String first = rs.getString("first_name");
	          String last = rs.getString("last_name");
	          String email = rs.getString("email");
	          
	          //Display values
	          System.out.print("ID: " + id);
	          System.out.print(", First: " + first);
	          System.out.print(", Last: " + last);
	          System.out.println(", Email: " + email);
	       }
	       
	       //STEP 6: Clean-up environment
	       rs.close();
	       stmt.close();
	       conn.close();
	       
	    }catch(SQLException se){
	       //Handle errors for JDBC
	       se.printStackTrace();
	    }catch(Exception e){
	       //Handle errors for Class.forName
	       e.printStackTrace();
	    }finally{
	       //finally block used to close resources
	       try{
	          if(stmt!=null)
	             stmt.close();
	       }catch(SQLException se2){
	       }// nothing we can do
	       try{
	          if(conn!=null)
	             conn.close();
	       }catch(SQLException se){
	          se.printStackTrace();
	       }//end finally try
	    }//end try
	    System.out.println("Goodbye!");

	}

}
