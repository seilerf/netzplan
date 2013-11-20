/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzplan;

import java.util.Observable;

/**
 *
 * @author fseiler
 */
public class Vorgang extends Observable {
    
    private String name;
    private double dauer;
    private double fAnfang;
    private double sEnde;
    private Vorgang nachf;

    public Vorgang(String name, double dauer, double fAnfang, double sEnde) {
        this.name = name;
        this.dauer = dauer;
        this.fAnfang = fAnfang;
        this.sEnde = sEnde;
        this.nachf = null;
    }
    
    public Vorgang(){
        this.name = "";
        this.dauer = 0.0;
        this.fAnfang = 0.0;
        this.sEnde = 0.0;
        this.nachf = null;
    }
    
    public Vorgang(String name){
        this.name = name;
        this.dauer = 0.0;
        this.fAnfang = 0.0;
        this.sEnde = 0.0;
        this.nachf = null;
    }

    public String getName() {
        return name;
    }

    public double getDauer() {
        return dauer;
    }

    public double getfAnfang() {
        return fAnfang;
    }

    public double getsEnde() {
        return sEnde;
    }

    public Vorgang getNachf() {
        return nachf;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setDauer(double dauer) {
        this.dauer = dauer;
    }

    public void setfAnfang(double fAnfang) {
        this.fAnfang = fAnfang;
    }

    public void setsEnde(double sEnde) {
        this.sEnde = sEnde;
    }

    public void setNachf(Vorgang nachf) {
        this.nachf = nachf;
    }
    
    public double getFruehestEnde() {
        // Evtl. Fehlberechnung abfangen, wenn noch kein fAnfang initialisiert
        return (this.fAnfang + this.dauer);
    }
    
}
