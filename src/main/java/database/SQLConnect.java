/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.sql.*;

/**
 *
 * @author fseiler
 */
public class SQLConnect {
    
    // MySQL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/test";
    static final String USER = "oop13";
    static final String PWD = "1234";

    public SQLConnect() {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL,USER,PWD);
            stmt = conn.createStatement();
	
        } 
        catch (ClassNotFoundException e) {
            System.out.println("JDBC_DRIVER wohl nicht gefunden, .jar eingebunden?");
            e.printStackTrace();
        } 
        catch (SQLException e) {
            System.out.println("Probleme mit SQL. Syntax korrekt? Passwort korrekt?");
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
                conn.close();
            } 
            catch (SQLException e) {
                System.out.println("Problem beim Schliessen der Verbindung");
                e.printStackTrace();
            }
        }
    }
}
