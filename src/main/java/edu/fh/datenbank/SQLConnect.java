/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.datenbank;

import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.fh.netzplanModell.Betriebsmittelgruppe;
import edu.fh.netzplanModell.Netzplan;
import edu.fh.netzplanModell.Vorgang;

/**
 *
 * @author fseiler/ M.Ullmann /Anton Lutset
 */
public class SQLConnect {
    
    // MySQL Parameter
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/netzplan";
    static final String USER = "root";
    static final String PWD = "asdfghjk1";
    
    private Connection conn;
    private LinkedList<Vorgang> vorgaenge;
    private boolean netzPlanCheck;

    public SQLConnect() {
        this.conn = null;  
 
    }
    
    /**
     * Funktion zum Starten der Verbindung zur Datenbank.
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
     * Funktion zum Schließen der Verbindung zur Datenbank.
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
     * Funktion um alle Netzplaene zu laden.
     * @return  netzplaene
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
     * Funktion um mit Hilfe der Id-Angabe einen Netzplan zu laden.
     * @param id -> Angabe der Netzplan id
     * @return netplan 
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
    * Fuktion um ein en neuen Netzplan in der Datenbank anzulegen.
    * @param netzplanId -> Id des Netzplans
    * @param netzplanName -> Name des Netzplans
    * @param startZeit -> Startzeit des Netzplans
    * @param endZeit -> Endzeit des Netzplans
    * @throws SQLException 
    */
    public void insertNetzplan(String netzplanName, Double startZeit, Double endZeit) throws SQLException {
        this.startConnection();
        String sqlQuery = "SELECT * FROM Netzplan;";
        ResultSet rsNetzplan = this.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sqlQuery);
        rsNetzplan.next();
        rsNetzplan.moveToInsertRow();
        
        rsNetzplan.updateString("nameNetzplan", netzplanName);
        rsNetzplan.updateDouble("startZeit", startZeit);
        rsNetzplan.updateDouble("endZeit", endZeit);
        rsNetzplan.insertRow();
        rsNetzplan.close();
        this.closeConnection();
    }
    
     
    /**
     * Funktion zum Laden aller Vorgaenge aus einem Netzplan.
     * @param idNetzplan
     * @return vorgaenge -> LinkedList mit Vorgangen des Netzplanes
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
     * Funktion laden eines bestimmten Vorgangs auf der Datenbank.
     * @param vorgangId -> Id des Vorgangs
     * @return vorgang
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
     * Funktion zum Hinzufügen eines Vorgangs in die Datenbank.
     * @param name -> Name des Vorgangs
     * @param dauer -> Dauer es Vorgangs
     * @param netzRefId -> Referenzid auf den Netzplan
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
     * Funktion zum Überprüfen der Existenz der NetzplanId.
     * @param netzplanId -> Id des Netzplans
     * @return netzPlanCheck (true oder false)
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
     * Funktion zum Laden aller Betriebsmittelgruppen.
     * @return betriebsMittel
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
     * Funktion um eine bestimmte Betriebsmittelgruppe per Id zu finden und aus der Datenbank zu laden.
     * @param betrMittelGrId
     * @return betriebsMittelGr
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
     * Funktion zum Einfügen einer neuen Betriebsmittelgruppe.
     * @param name -> Name des Betriebsmittels
     * @param kapa -> Kapazitaet des Betriebsmittelgruppe
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
    
    
    
    public int updateVorgangBmg(int vorgangid,  int bmgid ) throws SQLException
    {
        this.startConnection();   
       Statement s=this.getConnection().createStatement();
        String sqlQuery =  "UPDATE vorgang_has_betriebsmittelgruppe SET Betriebsmittelgruppe_idBetriebsmittelgruppe = "+bmgid+"  WHERE Vorgang_idVorgang = "+vorgangid+"";
        int zeilen=s.executeUpdate(sqlQuery);
        this.closeConnection();
        return zeilen;
       
    }
    
    
      public void insertVorgangBmg(int vorgangid, int netzplanid, int bmgid ) throws SQLException
    {
        this.startConnection();   
        
        
        String sqlQuery = "SELECT * FROM vorgang_has_betriebsmittelgruppe;";
        ResultSet rsNetzplan = this.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sqlQuery);
        rsNetzplan.next();
        rsNetzplan.moveToInsertRow();
        rsNetzplan.updateInt("Vorgang_idVorgang", vorgangid);
        rsNetzplan.updateInt("Vorgang_Netzplan_idNetzplan", netzplanid);
        rsNetzplan.updateInt("Betriebsmittelgruppe_idBetriebsmittelgruppe", bmgid);
       
        rsNetzplan.insertRow();
        rsNetzplan.close();
       
        this.closeConnection();
        
       
    }
    
    
   
    /**
     * Funktion um alle Vorgänger zu einem bestimmten Vorgang zu erhalten.
     * @param vorgId -> Id des Vorgangs
     * @return vorg
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
     * Funktion zum Laden aller Nachfolger.
     * @param vorgId -> Id des Vorgangs
     * @return nachf
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
     * Funktion um alle Vorgaenge zu einer Betriebsmittelgruppe aus einem Netzplan zu laden.
     * @param betrId -> Id zu der Betriebsmittelgruppe
     * @param netzPlanId -> Id zum Netzplan
     * @return bezugVorg
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
     * Funktion um zu einem bestimmten Vorgang alle Betriebsmittel zu laden.
     * @param vorgId -> Id zum Vorgang
     * @param netzPlanId -> Id zum Netzplan
     * @return vorgBetr
     * @throws SQLException 
     */
    public LinkedList<Betriebsmittelgruppe> ladeAlleBetrZuVorg(int vorgId, int netzPlanId) throws SQLException {
        LinkedList<Betriebsmittelgruppe> vorgBetr = new LinkedList<Betriebsmittelgruppe>();
        this.startConnection();
        String sqlQuery = "SELECT  x.Betriebsmittelgruppe_idBetriebsmittelgruppe, b.nameBetriebsmittelgruppe FROM betriebsmittelgruppe b , ( Select c.Betriebsmittelgruppe_idBetriebsmittelgruppe from Vorgang_has_Betriebsmittelgruppe c where  c.Vorgang_idVorgang = "+ vorgId +" AND c.Vorgang_Netzplan_idNetzplan = "+ netzPlanId +") x where x.Betriebsmittelgruppe_idBetriebsmittelgruppe= b.idBetriebsmittelgruppe" ;
        
        ResultSet rsVorg = this.getConnection().createStatement().executeQuery(sqlQuery);
        
         while(rsVorg.next()) {
             Betriebsmittelgruppe betrMG = new Betriebsmittelgruppe((Integer) rsVorg.getObject("Betriebsmittelgruppe_idBetriebsmittelgruppe"), (String) rsVorg.getObject("nameBetriebsmittelgruppe"));
             vorgBetr.add(betrMG);
         }
         rsVorg.close();
         this.closeConnection();
       return vorgBetr;
    }
    
    /**
     * Funktion um die Betriebsmittelkapazitaet zu einem Vorgang zu laden.
     * @param vorgangId -> Id des Vorgangs
     * @param netzPlanId -> Id des Netzplanes 
     * @return betriebsMittel
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
    
    /**
     * Funktion um eine Änderung im Vorgang in die DB zu speichern.
     * @param name -> name des Vorgangs
     * @param dauer -> dauer des VOrgangs 
     * @param id -> Id des Vorgangs 
     * 
     * @throws SQLException 
     */
    public void updateVorgang(String name, double dauer, int id) throws SQLException {
        this.startConnection();
        String sqlQuery = "UPDATE vorgang SET nameVorgang = '"+name+"', dauer ="+dauer+"  WHERE idVorgang = "+id+"";
        this.getConnection().createStatement().executeUpdate(sqlQuery);
        this.closeConnection();
    }
    
     
    
}
