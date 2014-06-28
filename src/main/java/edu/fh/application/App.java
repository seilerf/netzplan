package edu.fh.application;

import datenbank.SQLConnect;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import netzcontroller.NetzplanController;
import netzplan.Netzplan;
import netzview.StartView;


public class App 
{
    public static void main(String[] args) {
        
        SQLConnect sql = new SQLConnect();
        Netzplan netz = new Netzplan();
        Netzplanung netzPl; 
        
        
        
       try {
           netz = sql.ladeNetzplan(1);
           netzPl = new Netzplanung(netz.getId());
           netzPl.netzPlanBerechnung();
           
           
            /**Test 1: Funktion ==> ladeAlleNetzplaene()
             * sql.ladeAlleNetzplaene();  
             * ->   Done
             */
            
           
            /**Test 2: Funktion ==> ladeNetzplan(int id)
             * int id = 1;
             * sql.ladeNetzplan(id);
             * ->   Done
             */
           
           
            /**Test 3: Funktion ==> insertNetzplan()
             *  Integer netzplanId = 3;
             *  String netzName = "TestPlan3";
             *  double gPuffer = 500;
             *  double fPuffer = 500;
             *  sql.insertNetzplan(netzplanId, netzName, gPuffer, fPuffer);
             *  ->  Done
             */
        
           
           /**Test 4: Funktion ==> ladeVorgaenge()
            * sql.ladeVorgaenge(1);
            * ->    Done
            */ 
        
           
           /**Test 5: Funktion ==> insertVorgang()
            *                    -> überprüft auch mit mit checkNetzplanId die Gültigkeit, der besagten
            * String name = "WALHALLA";
            * double testdauer = 500;
            * int netzRefId = 4;
            * sql.insertVorgang(name, testdauer, netzRefId);
            * ->    Done
            */ 
           
           
           /**Test 6: Funktion ==> ladeAlleBetriebsMittelGruppen()
            * sql.ladeAlleBetriebsMittelGruppen();
            * ->    Done
            */
           
           
           /**Test 7: Funktion ==> ladeAlleVorUndNachf()
            * String name = "WolterS";
            * double kapa = 200;
            * sql.insertBetriebsmittelgruppe(name, kapa);
            * ->    Done
            */ 
           
           
           /**Test 8: Funktion ==> ladeAlleVorUndNachf()
            * sql.ladeAlleVorUndNachf();
            * ->    Done
            */
           
           
           /**Test 9: Funktion ==> ladeAlleVorg()
            * sql.ladeAlleVorg(10);
            * ->    Done
            */
           
           
           /**Test 10: Funktion ==> ladeAlleNachf()
            * sql.ladeAlleNachf(1);
            * ->    Done
            */
           
           
           /**Test 11: Funktion ==> ladeAlleVorgZuBetr()
            * sql.ladeAlleVorgZuBetr(1001);
            * ->    Done
            */
           
           
           /**Test 12: Funktion ==> ladeAlleBetrZuVorg()
            * sql.ladeAlleBetrZuVorg(1);
            * ->    Done
            */
           
           /**Test 12: Funktion ==> getBetriebsmittelkapazitaetForId()
            * sql.getBetriebsmittelkapazitaetForId(2);
            * ->    Done
            */
           

            
       } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
            //NetzplanController netzplanCon = new NetzplanController(new Netzplan());
            // startView.pack();
            // startView.pack();

        
    }
}
