/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.application;

import datenbank.SQLConnect;
import java.awt.GridBagConstraints;
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
    private int refBackInitSize;
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
    private double minSt;
    private double refSaz;
    
    private LinkedList<Vorgang> vorgangList;
    private LinkedList<Vorgang> initVorgang;
    private LinkedList<Vorgang> backInit;
    private LinkedList<Vorgang> sortierteVorgaengeRef = new LinkedList<Vorgang>();
    private LinkedList<Vorgang> sortierteVorgaenge = new LinkedList<Vorgang>();
    private LinkedList<Vorgang> vorgaengerCash = new LinkedList<Vorgang>();
    
    //Default Konstruktor

    
    public Netzplanung(int idNetzplan) throws SQLException {
        
        if(con.checkNetzplanId(idNetzplan)==true) {
            this.netz = con.ladeNetzplan(idNetzplan);
            vorgangList = this.con.ladeVorgaenge(idNetzplan);
            //Update Puffer Funktion muss noch implementiert werden!!!
            this.netz.setGesamtPuffer(vorgangList.size());
            this.netz.setFreierPuffer(MAX-vorgangList.size());
            this.vorgangList = new LinkedList<Vorgang>(con.ladeVorgaenge(idNetzplan));
            this.initVorgang = new LinkedList<Vorgang>();
            this.backInit = new LinkedList<Vorgang>();
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
        this.defineOrdersFirst(vorgangList);
        this.refInitSize = initVorgang.size(); 
        
        for(int i=0; i< this.initVorgang.size(); ++i) {
            if(this.listIdCheck(initVorgang.get(i).getVorgangId())==false) {    
                sortierteVorgaenge.add(initVorgang.get(i));
                System.out.println("Oberste Init For-Schleife! \t");
                System.out.println("Id des Vorgangs: "+ sortierteVorgaenge.get(i).getVorgangId());
                System.out.println("Name des Vorgangs: "+ sortierteVorgaenge.get(i).getName());
                System.out.println("Dauer des Vorgangs: "+ sortierteVorgaenge.get(i).getDauer()+"\n");
            }
        }
        
        for(int j=0; j<sortierteVorgaenge.size(); ++j) {
            System.out.println("Obere For-Schleife!\t");
            sortierteVorgaenge.get(j).setNachf(con.ladeAlleNachf(sortierteVorgaenge.get(j).getVorgangId()));
            for(int k=0; k< sortierteVorgaenge.get(j).getNachf().size(); ++k) {
                if(this.listIdCheck(sortierteVorgaenge.get(j).getNachf().get(k).getVorgangId())==false) {
                    sortierteVorgaenge.add(sortierteVorgaenge.get(j).getNachf().get(k));
                     System.out.println("Untere For-Schleife! \t");
                     System.out.println("Id des Vorgangs: "+ sortierteVorgaenge.get(j).getNachf().get(k).getVorgangId());
                     System.out.println("Name des Vorgangs: "+ sortierteVorgaenge.get(j).getNachf().get(k).getName());
                     System.out.println("Dauer des Vorgangs: "+ sortierteVorgaenge.get(j).getNachf().get(k).getDauer()+"\n");
                }
            }
        }
        
        for(int l=0; l<sortierteVorgaenge.size(); ++l) {
            if(l<this.refInitSize) {
                sortierteVorgaenge.get(l).setFez(sortierteVorgaenge.get(l).getDauer()); 
            } 
            if(l>=refInitSize) {
                System.out.println("Gehen wir hier rein?!-> Zweite IF-Abfrage");
                System.out.println("Id des aktuelle zu Verarbeitenden Vorgangs:" +sortierteVorgaenge.get(l).getVorgangId());
                //Fehler hier bei ladeAleVorg, außerdem dem wird beim setLadeZeit nicht in die DB geschrieben
                //Dabei muss entschieden werden ob die Vorgang.java auch noch eine DB-Connection bekommt oder
                //in der SQLConnection noch Methoden zu adden von Zeiten in die Datenbank implementiert werden (2ter Vorschlag besser! morgen!) 
                /**
                 * Funzt Immer noch nicht richtig!!
                 */
                vorgaengerCash = con.ladeAlleVorg(sortierteVorgaenge.get(l).getVorgangId());  
                for(int z=0; z<vorgaengerCash.size(); ++z) {
                    for(int y=0; y<sortierteVorgaenge.size(); ++y) {
                        if(vorgaengerCash.get(z).getVorgangId() == sortierteVorgaenge.get(y).getVorgangId()) {
                            vorgaengerCash.get(z).setFez(sortierteVorgaenge.get(y).getFez());
                        }
                    }
                }
                this.minSt = vorgaengerCash.get(0).getFez();
                
                for(int p=0; p<vorgaengerCash.size(); ++p) {
                    double minRef = vorgaengerCash.get(p).getFez();
                    //System.out.println("ReferenzWert zum SchleifenStart: "+minSt);
                    //System.out.println("ReferenzWert beim Durchlaufen der VorgaengerCash-Liste: "+minRef+"");
                    
                    if(minRef < minSt) {
                        minSt = minRef;
                        System.out.println("Sind wir nach den Startwerten auch in der 3te.IF-Anweisung?!\n");
                    }
                    System.out.println("Der Wert der Dauer des aktuellen Vorgangs lautet: "+ sortierteVorgaenge.get(l).getDauer());
                    sortierteVorgaenge.get(l).setFaz(minSt);
                    System.out.println("Der Wert der Anfangszeit beträgt:" + sortierteVorgaenge.get(l).getFaz());
                    
                    sortierteVorgaenge.get(l).setFez(sortierteVorgaenge.get(l).getFaz() + sortierteVorgaenge.get(l).getDauer());
                    System.out.println("Der Wert für die früheste Endzeit beträgt:" + sortierteVorgaenge.get(l).getFez()+"\n\n");
                   vorgaengerCash.clear();
                }
            }
        }
        System.out.println("===============================================================");
        System.out.println("START DER BERECHNUNG DER SPAETESTEN ANFANGSZEITEN UND ENDZEITEN");
        System.out.println("===============================================================");
        this.sortierteVorgaengeRef = sortierteVorgaenge;
        this.defineOrdersLast(sortierteVorgaenge);
        this.refBackInitSize = backInit.size();
        for(int n=0; n<backInit.size(); ++n) {
            for(int o=0; o<sortierteVorgaenge.size(); ++o) {
                if(backInit.get(n).getVorgangId()==sortierteVorgaenge.get(o).getVorgangId()) {
                    sortierteVorgaenge.get(o).setSez(netz.getEnde());
                    sortierteVorgaenge.get(o).setSaz(netz.getEnde() - sortierteVorgaenge.get(o).getDauer());
                    for(int x=0; x<sortierteVorgaengeRef.size(); ++x) {
                        if(sortierteVorgaengeRef.get(x).getVorgangId()==backInit.get(n).getVorgangId()) {
                           //sortierteVorgaengeRef.remove(x);
                        }
                    }
                }
            }
        }    
                
        for(int u=sortierteVorgaengeRef.size()-1; u>-1; --u) {
            vorgaengerCash = con.ladeAlleVorg(sortierteVorgaengeRef.get(u).getVorgangId());
                    
            if(vorgaengerCash.size() != 0) {
                for(int y=0; y < vorgaengerCash.size(); ++y) {
                    for(int c=0; c<sortierteVorgaenge.size(); ++c) {
                        if(vorgaengerCash.get(y).getVorgangId()== sortierteVorgaenge.get(c).getVorgangId()) {
                            for(int v=0; v<sortierteVorgaenge.size(); ++v) {
                                if(sortierteVorgaengeRef.get(u).getVorgangId()== sortierteVorgaenge.get(v).getVorgangId()) {
                                   if(this.refSaz > sortierteVorgaenge.get(c).getSez() - sortierteVorgaenge.get(c).getDauer() || sortierteVorgaenge.get(c).getSaz()==0) {
                                    sortierteVorgaenge.get(c).setSez(sortierteVorgaenge.get(v).getSaz());
                                    sortierteVorgaenge.get(c).setSaz(sortierteVorgaenge.get(c).getSez() - sortierteVorgaenge.get(c).getDauer());
                                    this.refSaz = sortierteVorgaenge.get(c).getSez();
                                   }
                               }
                            }  
                        }
                    }
                }
            }
            vorgaengerCash.clear();
        }
        
        for(int d=0; d<sortierteVorgaenge.size(); ++d) {
            System.out.println("VorgangId: "+sortierteVorgaenge.get(d).getVorgangId());
            System.out.println("VorgangsName: "+sortierteVorgaenge.get(d).getName());
            System.out.println("VorgangsDauer: "+sortierteVorgaenge.get(d).getDauer());
            System.out.println("VorgangsFaz: "+sortierteVorgaenge.get(d).getFaz());
            System.out.println("VogangsFez:" +sortierteVorgaenge.get(d).getFez());
            System.out.println("VorgangsSaz: "+sortierteVorgaenge.get(d).getSaz());
            System.out.println("VorgangsSez: "+sortierteVorgaenge.get(d).getSez()+"\n\n\n");
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
    
    /**
     * 
     * @param vorgang
     * @return
     * @throws SQLException 
     */
    public LinkedList<Vorgang> defineOrdersLast(LinkedList<Vorgang> vorgang) throws SQLException {
        if(vorgang.size() != 0) {
            for(int i=0; i<vorgang.size(); ++i) {
                if(con.ladeAlleNachf(vorgang.get(i).getVorgangId()).size()==0) {
                    vorgang.get(i).setSaz(netz.getEnde());
                    this.backInit.add(vorgang.get(i));
                }
            }
        } 
        return backInit;
    }


        
}
