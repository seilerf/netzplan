/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datenbank;

import java.sql.*;
import java.util.LinkedList;
import netzplan.Netzplan;
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
            
        } 
        catch (ClassNotFoundException e) {
            System.out.println("JDBC_DRIVER wohl nicht gefunden, .jar eingebunden?");
            e.printStackTrace();
        } 
        catch (SQLException e) {
            System.out.println("Probleme mit SQL. Syntax korrekt? Passwort korrekt?");
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        return this.conn;
    }
    
    public LinkedList<Vorgang> getVorgaenge() {
        return vorgaenge;
    }
    
    public LinkedList<Netzplan> ladeAlleNetzplaene() throws SQLException{
        LinkedList<Netzplan> netzplaene = new LinkedList<Netzplan>();
        String sqlQuery = "SELECT * FROM netzplan";
        System.out.println("Lese Netzpläne aus Datenbank...");
        ResultSet rsNetzplaene = this.getConnection().createStatement().executeQuery(sqlQuery);
        
        while(rsNetzplaene.next()){
            netzplaene.add(new Netzplan((Integer)rsNetzplaene.getObject("idNetzplan"), (String)rsNetzplaene.getObject("nameNetzplan")));
        }
        
        System.out.println("Schließe Datenbank-Verbindung...");
        this.conn.close();
        
        return netzplaene;
    }
    
    public ResultSet ladeNetzplan() throws SQLException {
        // Netzplan ID in der Abfrage dynamisch gestalten!!!
        String sqlQuery = "SELECT * FROM netzplan WHERE idNetzplan = 1";
        ResultSet rsNetzplan = this.getConnection().createStatement().executeQuery(sqlQuery);
        
        return rsNetzplan;
    }
    
    public LinkedList<Vorgang> ladeVorgaenge(int idNetzplan) throws SQLException {
        String sqlQuery = "SELECT * FROM vorgang WHERE Netzplan_idNetzplan = " + idNetzplan + ";";
        ResultSet rsVorgaenge = this.getConnection().createStatement().executeQuery(sqlQuery);
        
        LinkedList<Vorgang> vorgaenge = new LinkedList<Vorgang>();
        while(rsVorgaenge.next()){
            
            vorgaenge.add(new Vorgang((String) rsVorgaenge.getObject("nameVorgang"), (Double) rsVorgaenge.getObject("dauer")));
            System.out.println(rsVorgaenge.getObject("nameVorgang"));
        }
        
        /*Iterator<Vorgang> vorgIterator = vorgaenge.iterator();
        while(vorgIterator.hasNext()) {
            System.out.println(vorgIterator.next().getName());
        }*/
        
        return vorgaenge;
    }
   
}
