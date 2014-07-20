/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzplanModell;

import java.util.Observable;

/**
 *
 * @author M.Ullmann
 */
public class Betriebsmittelgruppe extends Observable {
    //Parameter für eine Betriebsmittelgruppe
    //Id der Betriebsmittelgruppe
    private int betrMittelGrId; 
    //Name der Betriebsmittelgruppe
    private String nameBetrMittelGr;
    //Kapazitaet der Betriebsmittelgruppe
    private double betrMittelKapa;
    //Referenz fuer eine VorgangsId
    private int vorgangId; 

    //Default-Konstruktor
    public Betriebsmittelgruppe() {   
    }
    //Konstruktor mit Bmg-Id
    public Betriebsmittelgruppe(int betrMGId) {
        this.betrMittelGrId = betrMGId;
    }
    
    //Konstruktor mit allen Parametern
    public Betriebsmittelgruppe(int id, String name, double kapa, int vorgangId) {
        this.betrMittelGrId = id;
        this.nameBetrMittelGr = name;
        this.betrMittelKapa = kapa;
        this.vorgangId = vorgangId;
    }
    
    //Konstruktor ohne die Referenz auf die Vorgangs-Id
     public Betriebsmittelgruppe(int id, String name, double kapa) {
        this.betrMittelGrId = id;
        this.nameBetrMittelGr = name;
        this.betrMittelKapa = kapa;
    }
     
     //Konstruktor ohne die Referenz auf die Vorgangs-Id und die kapazität
     public Betriebsmittelgruppe(int id, String name) {
        this.betrMittelGrId = id;
        this.nameBetrMittelGr = name;
       
    }
            
    
     /**
      * Getter fuer die Vorgangs-Id.
      * @return vorgangId
      */
    public int getVorgangId() {
        return vorgangId;
    }

    /**
     * Setter fuer die Vorgangs-Id.
     * @param vorgangId -> Id des referenzierten Vorgangs
     */
    public void setVorgangId(int vorgangId) {
        this.vorgangId = vorgangId;
    }
    
    /**
     * Getter fuer die Betriebsmittelgruppen-Id.
     * @return betrMittelGrId
     */
    public int getBetrMittelGrId() {
        return betrMittelGrId;
    }

    /**
     * Setter fuer die Betriebsmittelgruppen-Id.
     * @param betrMittelGr 
     */
    public void setBetrMittelGrId(int betrMittelGr) {
        this.betrMittelGrId = betrMittelGr;
    }

    /**
     * Getter fuer den Namen der Betriebsmittelgruppe.
     * @return nameBetrMittelGr
     */
    public String getNameBetrMittelGr() {
        return nameBetrMittelGr;
    }

    /**
     * Setter fuer den Namen der Betriebsmittelgruppe.
     * @param nameBetrMittelGr -> Name der Betriebsmittelgruppe
     */
    public void setNameBetrMittelGr(String nameBetrMittelGr) {
        this.nameBetrMittelGr = nameBetrMittelGr;
    }

    /**
     * Getter fuer die Betriebsmittelkapazitaet.
     * @return betrMittelKapa
     */
    public double getBetrMittelKapa() {
        return betrMittelKapa;
    }

    /**
     * Setter fuer die Betriebsmittelkapazitaet.
     * @param betrMittelKapa -> Angabe der Betriebsmittelkapazitaet 
     */
    public void setBetrMittelKapa(double betrMittelKapa) {
        this.betrMittelKapa = betrMittelKapa;
    }
    
    /**
     * Zieht den uebergebenen downRate-Betrag von der Kapazitaet ab. 
     * @param downRate -> Wert der Subtrahiert werden muss
     */
    public void discountBetrMittelKapa(double downRate) {
        this.betrMittelKapa -= downRate;
    }
}
