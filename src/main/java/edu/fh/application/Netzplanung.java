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
    private boolean checkField;
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
    
    private LinkedList<Vorgang> vorgangList;
    private LinkedList<Vorgang> initVorgang;
    LinkedList<Vorgang> sortierteVorgaenge = new LinkedList<Vorgang>();
    
    public Netzplanung(int idNetzplan) throws SQLException {
        int refId = con.checkNetzplanId();
        
        if(idNetzplan <= refId) {
            this.netz = con.ladeNetzplan(refId);
            this.netz.setGesamtPuffer(vorgangList.size());
            this.netz.setFreierPuffer(MAX-vorgangList.size());
            this.vorgangList = new LinkedList<Vorgang>(con.ladeVorgaenge(idNetzplan));
            this.anzahl = vorgangList.size();
            
            //this.dauer = new double[MAX];
           // this.fAnf = new double[MAX];
            //this.sEnde = new double[MAX];
            //this.startZeit = this.netz.getStart();
           //this.endZeit = this.netz.getEnde();
            
            
            
           // for(int i = 0;i < this.anzahl; ++i) {
            //    this.dauer[i] = this.vorgangList.get(i).getDauer();
           // }
            
          /**  for(int g = 0;g < anzahl; ++g) {
                //this.fAnf[g] = 0.0;
               // this.sEnde[g] = 0.0;
                
                for(int h = 0;h < anzahl; ++h) {
                    nachf[g][h] = false;
                }
            }*/
        } else {
            System.out.println("Die Netzplanung kann nicht durchgeführt werden!\n");
        } 
    }
    
    /**
     * Anpassung nötig bezüglich VorgangsListe und sortierteVorgaenge!!!!!
     * @throws SQLException 
     */
    public void netzPlanBerechnung() throws SQLException {
        // Abhängigkeiten setzen
        
        //LinkedList<Vorgang> nachfolger = new LinkedList<Vorgang>();
       // LinkedList<Vorgang> vorgaenger = new LinkedList<Vorgang>();
        this.defineOrdersFirst(vorgangList);
        for(int i=0; i< this.initVorgang.size(); ++i) {
            if(this.listIdCheck(initVorgang.get(i).getVorgangId())==false) {
                sortierteVorgaenge.add(initVorgang.get(i));
            }
        }
        for(int j=0; j<sortierteVorgaenge.size(); ++j) {
            sortierteVorgaenge.get(j).setNachf(con.ladeAlleNachf(sortierteVorgaenge.get(j).getVorgangId()));
            for(int k=0; k< sortierteVorgaenge.get(j).getNachf().size(); ++k) {
                if(this.listIdCheck(sortierteVorgaenge.get(j).getNachf().get(k).getVorgangId())==false) {
                    sortierteVorgaenge.add(sortierteVorgaenge.get(j).getNachf().get(k));
                }
            }
        }
        /**
        for(int i = 0;i < anzahl; ++i) {
            int fieldOne = this.vorgangList.get(i).getVorgangId();
            LinkedList<Vorgang> nachfolgerForOne = new LinkedList<Vorgang>(this.con.ladeAlleNachf(fieldOne));
            nachfolger.addAll(nachfolgerForOne);
            LinkedList<Vorgang> vorgaengerForOne = new LinkedList<Vorgang>(this.con.ladeAlleNachf(fieldOne));
            vorgaenger.addAll(vorgaengerForOne);
            sortierteVorgaenge.add(this.vorgangList.get(i));
            
            for(int j = 0;j <  this.vorgangList.get(i).getNachf().length; ++j) {
                int fieldTwo = nachfolger.get(j).getVorgangId();
                sortierteVorgaenge.add(nachfolger.get(j));
                this.nachf[fieldOne][fieldTwo] = true;
            }
        }
        
        //Berechnung der fruehsten Anfangszeitpunkte
        for(int k = 0;k < anzahl; ++k) {
            this.vorgangList.get(k).setFaz(this.startZeit);
            for(int l = 0;l < anzahl; ++l) {
                if(true == nachf[k][l]) {
                    double fEnd = nachfolger.get(l).getDauer() + nachfolger.get(l).getFaz();
                    if(fEnd > vorgangList.get(k).getFaz()) {
                        vorgangList.get(k).setFaz(fEnd); 
                    }
                }
            }
        }
        
        //Berechnung der spaetesten Endzeitpunkte
        for(int n = anzahl-1;n > -1; --n) {
            this.vorgangList.get(n).setSez(this.endZeit);
           
            for(int m = 0;m < anzahl; ++m) {
                if(true == nachf[n][m]) {
                    double sAnf = vorgaenger.get(m).getSez() - vorgaenger.get(m).getDauer();
                    if(vorgangList.get(n).getSez() > sAnf) {
                        vorgangList.get(n).setSez(sAnf);
                    }
                }
            }
        }
        
        for(int f = 0;f < anzahl; ++f) {
            vorgangList.get(f).setFez(vorgangList.get(f).getFaz() + vorgangList.get(f).getDauer());
        }
        double refEndZeit = this.endZeit;
        for(int f = anzahl-1;f > -1; --f) {
            vorgangList.get(f).setSaz(refEndZeit - vorgangList.get(f).getDauer());
            refEndZeit -= vorgangList.get(f).getDauer();
        } */
    } 
    
    public boolean listIdCheck(int vorgangId) {
        this.checkField = false;
        for(int i=0; i<sortierteVorgaenge.size(); ++i) {
            if(vorgangId == sortierteVorgaenge.get(i).getVorgangId()) {
                this.checkField = true;
            }
        } 
        return checkField;
    }
    
    public LinkedList<Vorgang> defineOrdersFirst(LinkedList<Vorgang> vorgang) {
        for(int i=0; i<anzahl; ++i) {
            if(vorgangList.get(i).getVorgaenger().size()==0) {
                this.initVorgang.add(vorgangList.get(i));
            }
        }
        return initVorgang;
    }


        
}
