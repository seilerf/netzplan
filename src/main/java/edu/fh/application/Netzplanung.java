/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.application;

import datenbank.SQLConnect;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
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
    private int refInitSize;
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
    
    //Default Konstruktor

    
    public Netzplanung(int idNetzplan) throws SQLException {
        
        if(con.checkNetzplanId(idNetzplan)==true) {
            this.netz = con.ladeNetzplan(idNetzplan);
            vorgangList = this.con.ladeVorgaenge(idNetzplan);
            this.netz.setGesamtPuffer(vorgangList.size());
            this.netz.setFreierPuffer(MAX-vorgangList.size());
            this.vorgangList = new LinkedList<Vorgang>(con.ladeVorgaenge(idNetzplan));
            this.initVorgang = new LinkedList<Vorgang>();
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
        this.refInitSize = initVorgang.size();
        System.out.println("Größe der refInitSize:" +initVorgang.size());
        
        for(int i=0; i< this.initVorgang.size(); ++i) {
            if(this.listIdCheck(initVorgang.get(i).getVorgangId())==false) {
                
                System.out.println("Kommen wir bei den Init-Vorgaengen rein?!");
                
                sortierteVorgaenge.add(initVorgang.get(i));
                System.out.println("Id des InitVorgang:"+initVorgang.get(i).getVorgangId());
                //Hier weitermachen morgen die Vorgaenge nach den Initvorgaengen bekommen nicht die richtige Dauer mitübergeben!!
                System.out.println("Dauer des InitVorgang:"+initVorgang.get(i).getDauer());
            }
        }
        
        
        for(int j=0; j<sortierteVorgaenge.size(); ++j) {
            sortierteVorgaenge.get(j).setNachf(con.ladeAlleNachf(sortierteVorgaenge.get(j).getVorgangId()));
            for(int k=0; k< sortierteVorgaenge.get(j).getNachf().size(); ++k) {
                if(this.listIdCheck(sortierteVorgaenge.get(j).getNachf().get(k).getVorgangId())==false) {
                    sortierteVorgaenge.add(sortierteVorgaenge.get(j).getNachf().get(k));
                    System.out.println("Der Vorgang mit der Id:"+sortierteVorgaenge.get(j).getNachf().get(k).getVorgangId()+" wurde hinzugefügt");
                     System.out.println("Der Vorgang hat die Dauer:"+sortierteVorgaenge.get(j).getNachf().get(k).getDauer()+" !!!");
                }
            }
        }
        
        for(int l=0; l<sortierteVorgaenge.size(); ++l) {
            if(l<this.refInitSize) {
                sortierteVorgaenge.get(l).setFez(sortierteVorgaenge.get(l).getDauer());
            } 
            if(l>refInitSize) {
                System.out.println("Gehen wir hier rein?!\n");
                LinkedList<Vorgang> vorgaengerCash = new LinkedList<Vorgang>();
                vorgaengerCash = con.ladeAlleVorg(sortierteVorgaenge.get(l).getVorgangId());  
                double minSt = vorgaengerCash.get(0).getFez();
                
                for(int p=0; p<vorgaengerCash.size(); ++p) {
                    double minRef = vorgaengerCash.get(p).getFez();
                    
                    if(minRef < minSt) {
                        minSt = minRef;
                    }
                    
                   /** if(vorgaengerCash.get(p).getFez() < vorgaengerCash.get(p+1).getFez()) {
 
                       sortierteVorgaenge.get(l).setFaz(vorgaengerCash.get(p).getFez());
                       System.out.println("Die Übergebene früheste Endzeit nach den Init-Vorgaengen lautet:" +sortierteVorgaenge.get(l).getFaz());
                    } else {
                        sortierteVorgaenge.get(l).setFez(vorgaengerCash.get(p+1).getFez());
                        System.out.println("Die Übergebene früheste Endzeit nach den Init-Vorgaengen lautet:" +sortierteVorgaenge.get(p).getFaz());
                    }*/
                    
                    System.out.println("Die früheste Anfangszeit wurde gesetzt!\n");
                    System.out.println("Der Wert der Anfangszeit beträgt:" + sortierteVorgaenge.get(l).getFaz());
                    sortierteVorgaenge.get(l).setFaz(minSt);
                    System.out.println("Die früheste Endzeit wurde gesetzt\n");
                    System.out.println("Der Wert für die früheste Endzeit beträgt:" + sortierteVorgaenge.get(l).getFez());
                    sortierteVorgaenge.get(l).setFez(sortierteVorgaenge.get(l).getFaz() + sortierteVorgaenge.get(l).getDauer());
                    
                }
                
                //sortierteVorgaenge.get(l).setFaz(sortierteVorgaenge.get(l-1).getFez());
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
    
    /**
     * 
     * @param vorgangId
     * @return 
     */
    public boolean listIdCheck(int vorgangId) {
        this.checkField = false;
        for(int i=0; i<sortierteVorgaenge.size(); ++i) {
            if(vorgangId == sortierteVorgaenge.get(i).getVorgangId()) {
                this.checkField = true;
            }
        } 
        return checkField;
    }
    
    /**
     * 
     * @param vorgang
     * @return 
     */
    public LinkedList<Vorgang> defineOrdersFirst(LinkedList<Vorgang> vorgang) throws SQLException {
        if(vorgang.size()!=0) {
        for(int i=0; i<vorgang.size(); ++i) {
            if(con.ladeAlleVorg(vorgang.get(i).getVorgangId()).size()==0) {
                vorgang.get(i).setFaz(0);
                this.initVorgang.add(vorgang.get(i));
            }
          }
        }
        return initVorgang;
    }


        
}
