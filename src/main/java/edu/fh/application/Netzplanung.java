/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.application;

import datenbank.SQLConnect;
import java.sql.SQLException;
import java.util.LinkedList;
import netzplan.Netzplan;
import netzplan.Vorgang;

/**
 *
 * @author M.Ullmann
 */
public class Netzplanung {
    //Alle Netzplaene sind auf maximal 50 Vorgänge beschränkt
    final int MAX = 50;
    private final SQLConnect con = new SQLConnect();
    //Vorgangsdauer
    private double[] dauer;
    //frühester Vorgangsbeginn
    private double[] fAnf;
    //spätestes Vorgangsende
    private double[] sEnde;
    private boolean[][] nachf;
    
    private Netzplan netz;
    private double startZeit;
    private double endZeit;
    private int anzahl;
    
    LinkedList<Vorgang> vorgangList;
    
    public Netzplanung(int idNetzplan) throws SQLException {
        int refId = con.checkNetzplanId();
        
        if(idNetzplan <= refId) {
            this.netz = con.ladeNetzplan(refId);
            this.netz.setGesamtPuffer(vorgangList.size());
            this.netz.setFreierPuffer(MAX-vorgangList.size());
            this.vorgangList = new LinkedList<Vorgang>(con.ladeVorgaenge(idNetzplan));
            this.anzahl = vorgangList.size();
            this.dauer = new double[MAX];
            this.fAnf = new double[MAX];
            this.sEnde = new double[MAX];
            this.startZeit = this.netz.getStart();
            this.endZeit = this.netz.getEnde();
            
            
            
            for(int i = 0;i < this.anzahl; ++i) {
                this.dauer[i] = this.vorgangList.get(i).getDauer();
            }
            
            for(int g = 0;g < anzahl; ++g) {
                this.fAnf[g] = 0.0;
                this.sEnde[g] = 0.0;
                
                for(int h = 0;h < anzahl; ++h) {
                    nachf[g][h] = false;
                }
            }
        } else {
            System.out.println("Die Netzplanung kann nicht durchgeführt werden!\n");
        } 
    }
    
    /**
     * 
     * @throws SQLException 
     */
    public void netzPlanBerechnung() throws SQLException {
        // Abhängigkeiten setzen
        for(int i = 0;i < anzahl; ++i) {
            int fieldOne = this.vorgangList.get(i).getVorgangId();
            LinkedList<Vorgang> nachFolger = new LinkedList<Vorgang>(this.con.ladeAlleNachf(fieldOne));
            for(int j = 0;j <  this.vorgangList.get(i).getNachf().length; ++j) {
                int fieldTwo = nachFolger.get(j).getVorgangId();
                this.nachf[fieldOne][fieldTwo] = true;
            }
        }
        
        //fruehster Anfangszeitpunkt
        for(int k = 0;k < anzahl; ++k) {
            this.vorgangList.get(k).setFaz(this.startZeit);
            for(int l = 0;l < anzahl; ++l) {
                if(true == nachf[k][l]) {
                    
                }
            }
        }
        
        //
    }


        
}
