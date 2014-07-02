/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datenbank;

import java.awt.GridBagConstraints;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import netzplan.Betriebsmittelgruppe;
import netzplan.Netzplan;
import netzplan.Vorgang;

/**
 *
 * @author fseiler/ M.Ullmann
 */
public class SQLConnect {
    
    // MySQL Parameter
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/netzplan";
    static final String USER = "root";
    static final String PWD = "testpw";
    
    private Connection conn;
    private LinkedList<Vorgang> vorgaenge;
    private boolean netzPlanCheck;

    public SQLConnect() {
        this.conn = null;  
 
    }
    
    /**
     * Funktion zum Starten der Verbindung zur Datenbank
     */
    public void startConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            this.conn = DriverManager.getConnection(DATABASE_URL,USER,PWD);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnect.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    /**
     * Funktion zum Schließen der Verbindung zur Datenbank
     */
    public void closeConnection() {
        try {
            getConnection().close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Getter für die Connection
     */
    public Connection getConnection() {
        return this.conn;
    }
    
    /**
     * Getter für alle Vorgaenge
     * @return vorgaenge
     */
    public LinkedList<Vorgang> getVorgaenge() {
        return vorgaenge;
    }
    
    /**
     * Funktion um alle Netzplaene zu laden
     * @return
     * @throws SQLException 
     */
    public LinkedList<Netzplan> ladeAlleNetzplaene() throws SQLException {
        LinkedList<Netzplan> netzplaene = new LinkedList<Netzplan>();
        this.startConnection();
        String sqlQuery = "SELECT * FROM netzplan";
        System.out.println("Lese Netzpläne aus Datenbank...");
        ResultSet rsNetzplaene = this.getConnection().createStatement().executeQuery(sqlQuery);
        
        while(rsNetzplaene.next()) {
        Netzplan netzplan = new Netzplan((Integer)rsNetzplaene.getObject("idNetzplan"), (String)rsNetzplaene.getObject("nameNetzplan"), (Double)rsNetzplaene.getObject("startZeit"), (Double)rsNetzplaene.getObject("endZeit"));
           netzplaene.add(netzplan);      
        }
        rsNetzplaene.close();
        this.closeConnection();
        
        return netzplaene;
    }
    
    /**
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    public Netzplan ladeNetzplan(int id) throws SQLException {
        this.startConnection();
        String sqlQuery = "SELECT * FROM netzplan WHERE idNetzplan = "+id+"";
        ResultSet rsNetzplan = this.getConnection().createStatement().executeQuery(sqlQuery);
        Netzplan netzplan = new Netzplan();
        
        while(rsNetzplan.next()){
            netzplan = new Netzplan((Integer)rsNetzplan.getObject("idNetzplan"), (String)rsNetzplan.getObject("nameNetzplan"), (Double)rsNetzplan.getObject("startZeit"), (Double)rsNetzplan.getObject("endZeit"));

        }
        rsNetzplan.close();
        this.closeConnection();
        return netzplan;
    }

    
   /**
    * Funktion zum Einfügen eines neuen Netzplanes in die Datenbank.
    * @param netzplanId
    * @param netzplanName
    * @param startZeit
    * @param endZeit
    * @throws SQLException 
    */
    public void insertNetzplan(Integer netzplanId, String netzplanName, Double startZeit, Double endZeit) throws SQLException {
        this.startConnection();
        String sqlQuery = "SELECT * FROM Netzplan;";
        ResultSet rsNetzplan = this.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sqlQuery);
        rsNetzplan.next();
        rsNetzplan.moveToInsertRow();
        rsNetzplan.updateInt("idNetzplan", netzplanId);
        rsNetzplan.updateString("nameNetzplan", netzplanName);
        rsNetzplan.updateDouble("startZeit", startZeit);
        rsNetzplan.updateDouble("endZeit", endZeit);
        rsNetzplan.insertRow();
        rsNetzplan.close();
        this.closeConnection();
    }
    
     
    /**
     * Funktion zum Laden aller Vorgaenge zu einem Netzplan
     * @param idNetzplan
     * @return
     * @throws SQLException 
     */
    public LinkedList<Vorgang> ladeVorgaenge(int idNetzplan) throws SQLException {
        this.startConnection();
        String sqlQuery = "SELECT * FROM vorgang WHERE Netzplan_idNetzplan = " + idNetzplan + ";";
        ResultSet rsVorgaenge = this.getConnection().createStatement().executeQuery(sqlQuery);
       
        LinkedList<Vorgang> vorgaenge = new LinkedList<Vorgang>();
        while(rsVorgaenge.next()){
            vorgaenge.add(new Vorgang((Integer) rsVorgaenge.getObject("idVorgang"), (String) rsVorgaenge.getObject("nameVorgang"), (Double) rsVorgaenge.getObject("dauer")));
        }
        rsVorgaenge.close();
        this.closeConnection();
        return vorgaenge;
    }
    
    /**
     * Funktion laden eines bestimmten Vorgangs auf der Datenbank
     * @param vorgangId
     * @return
     * @throws SQLException 
     */
    public Vorgang ladeVorgang(int vorgangId) throws SQLException {
        this.startConnection();
        String sqlQuery = "SELECT * FROM vorgang WHERE idVorgang = "+ vorgangId +";";
        ResultSet rsVorgang = this.getConnection().createStatement().executeQuery(sqlQuery);
        rsVorgang.next();
        Vorgang vorgang = new Vorgang((Integer) rsVorgang.getObject("idVorgang"), (String) rsVorgang.getObject("nameVorgang"), (Double) rsVorgang.getObject("dauer"));
      
        this.closeConnection();
        return vorgang;
    }
    
    /**
     * Funktion zum Hinzufügen eines Vorgangs in die Datenbank
     * @param name
     * @param dauer
     * @param netzRefId
     * @throws SQLException 
     */
    public void insertVorgang(String name, double dauer, int netzRefId) throws SQLException {

        if(this.checkNetzplanId(netzRefId)==true) {
            
            this.startConnection();
            String sqlQuery = "SELECT * FROM vorgang;";
            ResultSet rsVorgang = this.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sqlQuery);
        
            rsVorgang.next();
            rsVorgang.moveToInsertRow();
            rsVorgang.updateInt("Netzplan_idNetzplan", netzRefId);
            rsVorgang.updateString("nameVorgang", name);
            rsVorgang.updateDouble("dauer", dauer);
      
            rsVorgang.insertRow();
            rsVorgang.close();
        } else {
            System.out.println("Der Eingabewert zur Netzplan-Id exisitert nicht!\n");
        }
        this.closeConnection();
    }
    
    /**
     * Funktion zum Überprüfen der Existenz der NetzplanId
     * @param netzplanId
     * @return 
     * @throws java.sql.SQLException 
     */
    public boolean checkNetzplanId(int netzplanId) throws SQLException {
        this.netzPlanCheck = false;
        LinkedList<Netzplan> netzplanList = new LinkedList<Netzplan>();
        this.startConnection();
        String sqlQuery = "SELECT * FROM netzplan;";
        ResultSet rsNetzp = this.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sqlQuery);
        
        
        if(rsNetzp != null) {
            while(rsNetzp.next()==true) {
                Netzplan netzplan = new Netzplan ((Integer) rsNetzp.getObject("idNetzplan"), (String) rsNetzp.getObject("nameNetzplan"), (Double) rsNetzp.getObject("startZeit"), (Double) rsNetzp.getObject("endZeit"));
                netzplanList.add(netzplan);
            }
        }
        
        
        
        for(int i=0; i< netzplanList.size(); ++i) {
            if(netzplanId == netzplanList.get(i).getId()) {
               netzPlanCheck = true; 
            }
        }
        rsNetzp.close();
        this.closeConnection();
        return netzPlanCheck;
    }
    
    /**
     * Funktion zum Laden aller Betriebsmittelgruppen
     * @return
     * @throws SQLException 
     */
    public LinkedList<Betriebsmittelgruppe> ladeAlleBetriebsMittelGruppen() throws SQLException {
        LinkedList<Betriebsmittelgruppe> betriebsMittel = new LinkedList<Betriebsmittelgruppe>();
        this.startConnection();
        String sqlQuery = "SELECT * FROM Betriebsmittelgruppe";
        ResultSet rsBetrMittel = this.getConnection().createStatement().executeQuery(sqlQuery);
        
        while(rsBetrMittel.next()) {
            Betriebsmittelgruppe bsMittel = new Betriebsmittelgruppe((Integer) rsBetrMittel.getObject("idBetriebsmittelgruppe"),(String) rsBetrMittel.getObject("nameBetriebsmittelgruppe"), (Double) rsBetrMittel.getObject("betriebsmittelKapazitaet"));
            betriebsMittel.add(bsMittel);
        }
        rsBetrMittel.close();
        this.closeConnection();
        return betriebsMittel;
    }
    
    /*
     * Funktion um eine bestimmte Betriebsmittelgruppe per Id zu finden und aus der Datenbank zu laden
     * @param betrMittelGrId
     * @return
     * @throws SQLException 
     */
    public Betriebsmittelgruppe ladeBetriebsmittelgruppe(int betrMittelGrId) throws SQLException {
        this.startConnection();
        String sqlQuery = "SELECT * FROM Betriebsmittelgruppe WHERE idBetriebsmittelgruppe = "+ betrMittelGrId +"";
        ResultSet rsBetrMittel = this.getConnection().createStatement().executeQuery(sqlQuery);
        Betriebsmittelgruppe betriebsMittelGr = new Betriebsmittelgruppe();
        
        while(rsBetrMittel.next()) {
            betriebsMittelGr = new Betriebsmittelgruppe((Integer) rsBetrMittel.getObject("idBetriebsmittelgruppe"),(String) rsBetrMittel.getObject("nameBetriebsmittelgruppe"), (Double) rsBetrMittel.getObject("betriebsmittelKapazitaet"));
        }
        rsBetrMittel.close();
        this.closeConnection();
        return betriebsMittelGr;
    }
    
    /**
     * Funktion zum Einfügen einer neuen Betriebsmittelgruppe
     * @param name
     * @param kapa
     * @throws SQLException 
     */
    public void insertBetriebsmittelgruppe(String name, double kapa) throws SQLException {
        this.startConnection();
        String sqlQuery = "SELECT * FROM Betriebsmittelgruppe";
        ResultSet rsBetrMt = this.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sqlQuery);
        
        rsBetrMt.next();
        rsBetrMt.moveToInsertRow();
        rsBetrMt.updateString("nameBetriebsmittelgruppe", name);
        rsBetrMt.updateDouble("betriebsmittelKapazitaet", kapa);
        rsBetrMt.insertRow();
        
        rsBetrMt.close();
        this.closeConnection();
    }
    
    
    
    /**
     * Funktion zum Laden aller Vorgang_has_Vorgang Einträge
     * @param netzPlanId
     * @return
     * @throws SQLException 
     
    public LinkedList<Vorgang[]> ladeAlleVorUndNachf(int netzPlanId) throws SQLException {
        LinkedList<Vorgang[]> vorgUndNachf = new LinkedList<Vorgang[]>();
        this.startConnection();
        String sqlQuery = "SELECT * FROM Vorgang_has_Vorgang WHERE Vorgaenger_Netzplan_idNetzplan ="+ netzPlanId +"";
        ResultSet rsVorgUndNachf = this.getConnection().createStatement().executeQuery(sqlQuery);
        
         while(rsVorgUndNachf.next()) {
             Vorgang vorgangV = new Vorgang((Integer) rsVorgUndNachf.getObject("Vorgaenger_idVorgang"));
             Vorgang vorgangN = new Vorgang((Integer) rsVorgUndNachf.getObject("NachFolger_idVorgang"));
             Vorgang[] vorgArr = new Vorgang[2];
             vorgArr[0] = vorgangV;
             vorgArr[1] = vorgangN;
             vorgUndNachf.add(vorgArr);
         }
         rsVorgUndNachf.close();
         this.closeConnection();
       return vorgUndNachf;
    } */
   
    /**
     * Funktion um alle Vorgänger zu einem bestimmten Vorgang zu erhalten
     * @param vorgId
     * @return
     * @throws SQLException 
     */
    public LinkedList<Vorgang> ladeAlleVorg(int vorgId) throws SQLException {
        LinkedList<Vorgang> vorg = new LinkedList<Vorgang>();
        this.startConnection();
        //String sqlQuery = "SELECT * FROM Vorgang_has_Vorgang WHERE Nachfolger_idVorgang = "+ vorgId +"";
        String sqlQuery = "select vg.`idVorgang`, vg.`nameVorgang`, vg.dauer\n" +
                          "From vorgang vg, vorgang_has_vorgang nf Where vg.`idVorgang` = nf.`Vorgaenger_idVorgang` and nf.`Nachfolger_idVorgang`= " +vorgId+ ";";
        ResultSet rsVorg = this.getConnection().createStatement().executeQuery(sqlQuery);
        
         while(rsVorg.next()) {
             Vorgang vorgangV = new Vorgang((Integer) rsVorg.getObject("idVorgang"), (String) rsVorg.getObject("nameVorgang"), (Double) rsVorg.getObject("dauer"));
             vorg.add(vorgangV);
         }
         rsVorg.close();
         this.closeConnection();
       return vorg;
    }
    
    /**
     * Funktion zum Laden aller Nachfolger 
     * @param vorgId
     * @return
     * @throws SQLException 
     */
    public LinkedList<Vorgang> ladeAlleNachf(int vorgId) throws SQLException {
        LinkedList<Vorgang> nachf = new LinkedList<Vorgang>();
        this.startConnection();
        //String sqlQuery = "SELECT * FROM Vorgang_has_Vorgang WHERE Vorgaenger_idVorgang = "+ vorgId +"";
        //String sqlQuery = "select nf.`Nachfolger_idVorgang`, vg.nameVorgang, vg.dauer, vg.Netzplan_idNetzplan, nf.Vorgaenger_idVorgang\n" +
          //                 "From vorgang vg, vorgang_has_vorgang nf Where vg.`idVorgang` = "+ vorgId +" and nf.`Vorgaenger_idVorgang`= "+ vorgId +";";
        String sqlQuery = "select vg.`idVorgang`, vg.`nameVorgang`, vg.dauer\n" +
                          "From vorgang vg, vorgang_has_vorgang nf Where vg.`idVorgang` = nf.`Nachfolger_idVorgang` and nf.`Vorgaenger_idVorgang`= " +vorgId+ ";";
        ResultSet rsVorg = this.getConnection().createStatement().executeQuery(sqlQuery);
      
        
         while(rsVorg.next()) {
             Vorgang vorgangV = new Vorgang((Integer) rsVorg.getObject("idVorgang"), (String) rsVorg.getObject("nameVorgang"), (Double) rsVorg.getObject("dauer"));
             nachf.add(vorgangV);
         }
         rsVorg.close();
         this.closeConnection();
       return nachf;
    }
    
    /**
     * 
     * @param betrId
     * @param netzPlanId
     * @return
     * @throws SQLException 
     */
    public LinkedList<Vorgang> ladeAlleVorgZuBetr(int betrId, int netzPlanId) throws SQLException {
        LinkedList<Vorgang> bezugVorg = new LinkedList<Vorgang>();
        this.startConnection();
        String sqlQuery = "SELECT * FROM Vorgang_has_Betriebsmittelgruppe WHERE Betriebsmittelgruppe_idBetriebsmittelgruppe = "+ betrId +" AND Vorgang_Netzplan_idNetzplan = "+ netzPlanId +"";
        ResultSet rsBetr = this.getConnection().createStatement().executeQuery(sqlQuery);
        
         while(rsBetr.next()) {
             Vorgang vorgang = new Vorgang((Integer) rsBetr.getObject("Vorgang_idVorgang"));
             bezugVorg.add(vorgang);
         }
         rsBetr.close();
         this.closeConnection();
       return bezugVorg;
    }
    
    /**
     * Funktion um zu einem bestimmten Vorgang alle Betriebsmittel zu laden
     * @param vorgId
     * @param netzPlanId
     * @return
     * @throws SQLException 
     */
    public LinkedList<Betriebsmittelgruppe> ladeAlleBetrZuVorg(int vorgId, int netzPlanId) throws SQLException {
        LinkedList<Betriebsmittelgruppe> vorgBetr = new LinkedList<Betriebsmittelgruppe>();
        this.startConnection();
        String sqlQuery = "SELECT * FROM Vorgang_has_Betriebsmittelgruppe WHERE Vorgang_idVorgang = "+ vorgId +" AND Vorgang_Netzplan_idNetzplan = "+ netzPlanId +"";
        ResultSet rsVorg = this.getConnection().createStatement().executeQuery(sqlQuery);
        
         while(rsVorg.next()) {
             Betriebsmittelgruppe betrMG = new Betriebsmittelgruppe((Integer) rsVorg.getObject("Betriebsmittelgruppe_idBetriebsmittelgruppe"));
             vorgBetr.add(betrMG);
         }
         rsVorg.close();
         this.closeConnection();
       return vorgBetr;
    }
    
    /**
     * 
     * @param vorgangId
     * @param netzPlanId
     * @return
     * @throws SQLException 
     */
    public LinkedList<Betriebsmittelgruppe> getBetriebsmittelkapazitaetForId(int vorgangId, int netzPlanId) throws SQLException {
        this.startConnection();
        LinkedList<Betriebsmittelgruppe> betriebsMittel = new LinkedList<Betriebsmittelgruppe>();
        String sqlQuery = "select bmg.idBetriebsmittelgruppe, bmg.nameBetriebsmittelgruppe, bmg.betriebsmittelKapazitaet, vbmg.Vorgang_idVorgang\n" +
                           "From Betriebsmittelgruppe bmg, Vorgang_has_Betriebsmittelgruppe vbmg Where vbmg.Vorgang_idVorgang = "+ vorgangId+" and vbmg.Betriebsmittelgruppe_idBetriebsmittelgruppe = bmg.idBetriebsmittelgruppe and vbmg.`Vorgang_Netzplan_idNetzplan`= "+netzPlanId+""; 
        ResultSet rsBetrMt = this.getConnection().createStatement().executeQuery(sqlQuery);
        Betriebsmittelgruppe betriebsMittelGr = new Betriebsmittelgruppe();
        
        while(rsBetrMt.next()) {
            betriebsMittelGr = new Betriebsmittelgruppe((Integer) rsBetrMt.getObject("idBetriebsmittelgruppe"),(String) rsBetrMt.getObject("nameBetriebsmittelgruppe"), (Double) rsBetrMt.getObject("betriebsmittelKapazitaet"), (Integer) rsBetrMt.getObject("Vorgang_idVorgang"));
            betriebsMittel.add(betriebsMittelGr);
        }
        rsBetrMt.close();
        this.closeConnection();
        return betriebsMittel;
    }
}
