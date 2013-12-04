/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import netzplan.Vorgang;

/**
 *
 * @author fseiler
 */
public class SQLConnect {
    
    // MySQL Parameter
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/netzplan";
    static final String USER = "oop13";
    static final String PWD = "1234";
    
    private Connection conn;
    private LinkedList<Vorgang> vorgaenge;

    public SQLConnect() {
        this.conn = null;
        Statement stmt = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL,USER,PWD);
            System.out.println("Verbindung hergestellt zu " + DATABASE_URL + "!");
            stmt = conn.createStatement();
            this.ladeVorgaenge();
            
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
    
    public Connection getConnection() {
        return this.conn;
    }
    
    public void ladeVorgaenge() throws SQLException {
        ResultSet rsVorgaenge = this.getConnection().createStatement().executeQuery("SELECT * FROM vorgang WHERE Netzplan_idNetzplan = 1");
        
        this.vorgaenge = new LinkedList<Vorgang>();
        while(rsVorgaenge.next()){
            
            vorgaenge.add(new Vorgang((String) rsVorgaenge.getObject("nameVorgang"), (Double) rsVorgaenge.getObject("dauer")));
            //System.out.println(rsVorgaenge.getObject("nameVorgang"));
        }
        
        
        /*Iterator<Vorgang> vorgIterator = vorgaenge.iterator();
        while(vorgIterator.hasNext()) {
            System.out.println(vorgIterator.next().getName());
        }*/
     }
}
