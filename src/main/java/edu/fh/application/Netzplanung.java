/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.application;

import edu.fh.datenbank.SQLConnect;
import java.sql.SQLException;
import java.util.LinkedList;
import edu.fh.netzplanModell.Betriebsmittelgruppe;
import edu.fh.netzplanModell.Netzplan;
import edu.fh.netzplanModell.Vorgang;

/**
 *
 * @author M.Ullmann
 */
public class Netzplanung {
    //Alle Netzplaene sind auf maximal 50 Vorgänge beschränkt
    final int MAX = 50;
    //Hilfsparameter zum Gueltigkeitscheck
    private boolean checkField;
    //Hilfsparamter fuer den Betriebsmittelgruppen-Check
    private boolean checkBmg = true;
    //Hilfsparamter fuer die Feststellung der Ausfuehrbarkeit eines Netzplanes
    private boolean netzDurabilityCheck;
    //Datenbank-Verbing
    private final SQLConnect con = new SQLConnect();
    //Hilfsreferenz auf die Listengroeße
    private int refInitSize;
    //Hilfsreferenz auf die Listengroeße
    private int idNetzplan;

    public int getIdNetzplan() {
        return idNetzplan;
    }
    //Hilfsparameter für die NetzplanID
    private int refBackInitSize;
    //Referenz auf den zu bearbeitenden Netzplan
    private Netzplan netz;
    //Referenzwert zum Zwischenspeichern von fruehsten Anfangszeiten
    private double minSt;
    //Referenzwert auf die spaetenste Anfangszeit
    private double refSaz;
    //Benoetigten LinkedListen von Vorgaengen zur Berechnung des Netzplanes
    private LinkedList<Vorgang> vorgangList;
    private LinkedList<Vorgang> initVorgang;
    private LinkedList<Vorgang> backInit;
    private LinkedList<Vorgang> sortierteVorgaengeRef = new LinkedList<Vorgang>();
    private LinkedList<Vorgang> sortierteVorgaenge = new LinkedList<Vorgang>();

    public LinkedList<Vorgang> getSortierteVorgaenge() {
        return sortierteVorgaenge;
    }
    
    
    private LinkedList<Vorgang> vorgaengerCash = new LinkedList<Vorgang>();
    //Benoetigten LinkedListen von Betriebsmittelgruppen zur Berechnung der Durchfuehrbarkeit
    private LinkedList<Betriebsmittelgruppe> betriebsmittelgruppe = new LinkedList<Betriebsmittelgruppe>();

  
    private LinkedList<Betriebsmittelgruppe> bmgCach = new LinkedList<Betriebsmittelgruppe>();
    private LinkedList<Betriebsmittelgruppe> bmgOutOfKapa = new LinkedList<Betriebsmittelgruppe>();
    
    private LinkedList<Betriebsmittelgruppe> vorghasbmg = new LinkedList<Betriebsmittelgruppe>();
    //Konstruktor mit Uebergabe des Netzplanes
    public Netzplanung(int idNetzplan) throws SQLException {
        //Checkt ob die uebergebene Netzplan-Id ueberhaupt existiert
        if(con.checkNetzplanId(idNetzplan)==true) {
            this.netz = con.ladeNetzplan(idNetzplan);
            vorgangList = this.con.ladeVorgaenge(idNetzplan);
            
            this.netz.setGesamtPuffer(vorgangList.size());
            this.netz.setFreierPuffer(MAX-vorgangList.size());
            this.vorgangList = new LinkedList<Vorgang>(con.ladeVorgaenge(idNetzplan));
            this.initVorgang = new LinkedList<Vorgang>();
            this.backInit = new LinkedList<Vorgang>();
            this.idNetzplan=idNetzplan;
            this.netzDurabilityCheck = false;
        } else {
            System.out.println("Die Netzplanung kann nicht durchgeführt werden!\n");
        } if(this.netz.getFreierPuffer()< 0) {
            System.out.println("Der Netzplan besitzt zu viele Vorgaenge! Das Maximum beträgt 50 Vorgaenge pro Netzplan!\n");
        }
    }
    
    /**
     * Berechnung der Netzplanes (Faz, Fez, Saz, Sez) und der Durchfuehrbarkeit.
     * @return netzDurabilityCheck -> true ==> durchfuehrbar; false ==> nicht durchfuehrbar
     * @throws SQLException 
     */
    public boolean netzPlanBerechnung() throws SQLException {
        if(this.netz.getFreierPuffer() > -1) {
        // Abhängigkeiten setzen
        this.defineOrdersFirst(vorgangList);
        this.refInitSize = initVorgang.size(); 
        
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
        
        for(int l=0; l<sortierteVorgaenge.size(); ++l) {
            if(l<this.refInitSize) {
                sortierteVorgaenge.get(l).setFez(sortierteVorgaenge.get(l).getDauer()); 
            } 
            if(l>=refInitSize) {
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
                    
                    if(minRef < minSt) {
                        minSt = minRef;
                    }
                    sortierteVorgaenge.get(l).setFaz(minSt);
                    
                    sortierteVorgaenge.get(l).setFez(sortierteVorgaenge.get(l).getFaz() + sortierteVorgaenge.get(l).getDauer());
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
            }
            vorgaengerCash.clear();
        }
        
        if(this.checkBetriebsmittelStatus(sortierteVorgaenge)==false) {
            this.netzDurabilityCheck = false;
            System.out.println("=================================================================================================");
            System.out.println("Es ist nicht möglich den Netzplan mit den vorhandenen Betriebsmittelkapazitäten auszufuehren!!!\n");
            System.out.println("================================================================================================="); 
            for(int n=0; n < bmgOutOfKapa.size(); ++n) {
                System.out.println("Dies liegt an dem Betriebsmittel mit der Id: "+bmgOutOfKapa.get(n).getBetrMittelGrId());
                System.out.println("Betriebsmittelname: "+bmgOutOfKapa.get(n).getNameBetrMittelGr());
                System.out.println("Der Vorgang, der die Kapazitaet der Betriebsmittelgruppe ueberschritten hat: "+bmgOutOfKapa.get(n).getVorgangId());
            } 
        } else {
            this.netzDurabilityCheck = true;
            System.out.println("=================================================================================================");
            System.out.println("Die Betriebsmittelkapazitaeten reichen aus um den Netzplan auszufuehren!!!\n");
            System.out.println("=================================================================================================");
             for(int d=0; d<sortierteVorgaenge.size(); ++d) {
            System.out.println("VorgangId: "+sortierteVorgaenge.get(d).getVorgangId());
            System.out.println("VorgangsName: "+sortierteVorgaenge.get(d).getName());
            System.out.println("VorgangsDauer: "+sortierteVorgaenge.get(d).getDauer());
            System.out.println("VorgangsFaz: "+sortierteVorgaenge.get(d).getFaz());
            System.out.println("VogangsFez:" +sortierteVorgaenge.get(d).getFez());
            System.out.println("VorgangsSaz: "+sortierteVorgaenge.get(d).getSaz());
            System.out.println("VorgangsSez: "+sortierteVorgaenge.get(d).getSez()+"\n\n\n");
          }
        } 
        return this.netzDurabilityCheck;
    } 
    
    /**
     * Methode um zu Ueberpruefen ob ein Vorgang schon in der sortierten Liste vorhanden ist.
     * @param vorgangId -> Referenz auf die Id des Vorgangs
     * @return checkField
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
     * Diese Methode definiert die Initliste d.h. die Liste mit den Anfangsvorgaengen des Netzplanes.
     * @param vorgang -> LinkedListe auf die Vorgaenge
     * @return initVorgang
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
     * Methode um die Endvorgaenge des Netzplanes zu finden fuer die Berechnung der spaetesten Start/End-Zeiten.
     * @param vorgang -> LinkedListe mit den Vorgaengen
     * @return backInit
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

    /**
     * Methode um zu Ueberpruefen ob die Betriebsmittel fuer die Durchfuehrung des Netzplanes ausreichen.
     * @param vorgaenge -> LinkedListe mit den Vorgaengen
     * @return  checkBmg -> true ==> ausreichend false ==> ungenuegend
     * @throws SQLException 
     */
    public boolean checkBetriebsmittelStatus(LinkedList<Vorgang> vorgaenge) throws SQLException {
        for(int i=0; i<vorgaenge.size(); ++i) {
          bmgCach = con.getBetriebsmittelkapazitaetForId(vorgaenge.get(i).getVorgangId(), this.netz.getId());
          for(int j=0; j<bmgCach.size(); ++j) {
              betriebsmittelgruppe.add(bmgCach.get(j));
          }
          bmgCach.clear();
        }
        for(int k=0; k<vorgaenge.size(); ++k) {
            for(int l=0; l<betriebsmittelgruppe.size(); ++l) {
                if(vorgaenge.get(k).getVorgangId()==betriebsmittelgruppe.get(l).getVorgangId()) {
                    betriebsmittelgruppe.get(l).discountBetrMittelKapa(vorgaenge.get(k).getDauer());
                    if(betriebsmittelgruppe.get(l).getBetrMittelKapa()<0) {
                        this.checkBmg = false;
                        this.bmgOutOfKapa.add(betriebsmittelgruppe.get(l));
                    } 
                }
            }
        }
        return checkBmg;
    }
        
    
    
      public LinkedList<Betriebsmittelgruppe> getBetriebsmittelgruppe() {
        return betriebsmittelgruppe;
    }
    
}
