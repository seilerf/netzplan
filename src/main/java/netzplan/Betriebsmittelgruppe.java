/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzplan;

import java.util.Observable;

/**
 *
 * @author AdminMax
 */
public class Betriebsmittelgruppe extends Observable {
    
    private int betrMittelGrId; 
    private String nameBetrMittelGr;
    private double betrMittelKapa;
    private int vorgangId; 


    public Betriebsmittelgruppe() {   
    }
    
    public Betriebsmittelgruppe(int betrMGId) {
        this.betrMittelGrId = betrMGId;
    }
    
    public Betriebsmittelgruppe(String name, double kapa) {
        this.nameBetrMittelGr = name;
        this.betrMittelKapa = kapa;
    }
    
    public Betriebsmittelgruppe(int id, String name, double kapa, int vorgangId) {
        this.betrMittelGrId = id;
        this.nameBetrMittelGr = name;
        this.betrMittelKapa = kapa;
        this.vorgangId = vorgangId;
    }
    
     public Betriebsmittelgruppe(int id, String name, double kapa) {
        this.betrMittelGrId = id;
        this.nameBetrMittelGr = name;
        this.betrMittelKapa = kapa;
    }
            
    
     
    public int getVorgangId() {
        return vorgangId;
    }

    public void setVorgangId(int vorgangId) {
        this.vorgangId = vorgangId;
    }
    
    public int getBetrMittelGrId() {
        return betrMittelGrId;
    }

    public void setBetrMittelGrId(int betrMittelGr) {
        this.betrMittelGrId = betrMittelGr;
    }

    public String getNameBetrMittelGr() {
        return nameBetrMittelGr;
    }

    public void setNameBetrMittelGr(String nameBetrMittelGr) {
        this.nameBetrMittelGr = nameBetrMittelGr;
    }

    public double getBetrMittelKapa() {
        return betrMittelKapa;
    }

    public void setBetrMittelKapa(double betrMittelKapa) {
        this.betrMittelKapa = betrMittelKapa;
    }
    
    public void discountBetrMittelKapa(double downRate) {
        this.betrMittelKapa -= downRate;
    }
}