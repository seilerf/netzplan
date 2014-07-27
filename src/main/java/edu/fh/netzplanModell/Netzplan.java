/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzplanModell;

import java.util.LinkedList;

/**
 *
 * @author Florian Seiler / M.Ullmann
 */
public class Netzplan{
    private String name;
    private int id;
    // Anzahl Vorgänge
    private int anzahl;
    private double start;
    private double ende;
    private double gesamtPuffer;
    private double freierPuffer;
    private LinkedList<Vorgang> vorgaenge;
    
    /**
     * Default Konstruktor
     */
    public Netzplan() {
        vorgaenge = new LinkedList<Vorgang>();
        vorgaenge.addFirst(null);
    }
    
    /**
     * Konstruktur für den Netzplan
     */
    public Netzplan(int id, String name, double startZeit, double endZeit){
        this.id = id;
        this.name = name;
        this.start = startZeit;
        this.ende = endZeit;
        this.gesamtPuffer = 0;
        this.freierPuffer = 50;
        vorgaenge = new LinkedList<Vorgang>();
        vorgaenge.addFirst(null);
    }
    
    public Netzplan(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    /**
     * Gibt die ID des Netzplans wieder. 
     * @return Int - NetzplanID
     */
    public int getId(){
        return this.id;
    }
    
    public double getGesamtPuffer() {
        return gesamtPuffer;
    }

    public void setGesamtPuffer(double gesamtPuffer) {
        this.gesamtPuffer = gesamtPuffer;
    }

    public double getFreierPuffer() {
        return freierPuffer;
    }

    public void setFreierPuffer(double freierPuffer) {
        this.freierPuffer = freierPuffer;
    }
    
    /**
     * Gibt den Namen des Netzplans als String zurück.
     * @return String - Name des Netzplans
     */
    public String getName() {
        return name;
    }
    
    /**
     * Ändert den Namen des Netzplans.
     * @param name - String
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gibt die Anzahl der möglichen Vorgänge im Netzplan wieder. 
     * @return Int - Anzahl möglicher Vorgänge
     */
    public int getAnzahl() {
        return anzahl;
    }

    /**
     * Setzt die Anzahl der maximal möglichen Vorgänge im Netzplan.
     * @param anzahl - Int
     */
    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnde() {
        return ende;
    }

    public void setEnde(double ende) {
        this.ende = ende;
    }

    /**
     * Methode, mit der ein Vorgang zu einem Netzplan hinzugefügt werden kann.
     * @author Florian Seiler
     * @param vorgang
     */
    public void addVorgang(Vorgang vorgang){
        if(vorgaenge.getFirst() == null){
            vorgaenge.removeFirst();
            vorgaenge.addFirst(vorgang);
        }
        else{
            vorgaenge.add(vorgang);
        }
    }
    
    /**
     * Gibt die Vorgänge des Netzplans wieder. 
     * @return LinkedList<Vorgang> - Vorgänge im Netzplan
     */
    public LinkedList<Vorgang> getVorgaenge() {
        return vorgaenge;
    }
    
    /**
     * Setzt eine neue Vorgangsliste im Netzplan.
     * @param vorgaenge - LinkedList<Vorgang>
     */
    public void setVorgaenge(LinkedList<Vorgang> vorgaenge) {
        this.vorgaenge = vorgaenge;
    }
}
