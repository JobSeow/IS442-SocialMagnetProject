package socialmagnet.dao;
import java.sql.*;

/**
 * This Class represents Database Controller which prepares the connection between the Database and the Application
 * @version 25th March 2020
 * @author Daryl Ang, Job Seow, Tan Li Zhen, Tiffany Tan
 */
public class DBController {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    private static final String DB_URL = "jdbc:mysql://localhost/magnet?autoReconnect=true&serverTimezone=UTC";
    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "";
    private static final DBController CONTROLLER = new DBController();
    private DBController(){}
    Connection conn = null;
        /**
     * Gets the Controller
     * @return A Controller object
     */

    public static DBController getController(){
        return CONTROLLER;
    }
    
    /**
     * Gets the Connection String
     * @return A String representing the connection string
     */
    public String getConnectionString() {
        return String.format("jdbc:mysql://localhost/magnet?autoReconnect=true&serverTimezone=UTC");
    }

    /**
     * Registers the JDBC Driver and opens a connectionto connect to the database
     * @return A Connection object 
     */
    public Connection getConnection() {
        

        try {
            if(conn==null||conn.isClosed()){
                    //Register JDBC Driver
                    Class.forName(JDBC_DRIVER);

                    // Open a connection
                    // System.out.println("Connecting to database...");
                    conn = DriverManager.getConnection(DB_URL,USER,PASS);


            }

        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ": " + e.getMessage());
            System.exit(0);
        }
        
        // System.out.println("Opened database successfully");
        return conn;
    }
}