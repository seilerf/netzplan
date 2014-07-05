/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.datenbankTest;

import datenbank.SQLConnect;
import java.sql.SQLException;
import java.util.LinkedList;
import netzplan.Betriebsmittelgruppe;
import netzplan.Netzplan;
import netzplan.Vorgang;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AdminMax
 */
public class DatenbankTest {
    private SQLConnect sql;
    private LinkedList<Netzplan> netzList;
    boolean testRef;
    private Netzplan netz;
    private int testInt;
    private Vorgang vorg;
    private LinkedList<Vorgang> vorgList;
    private Betriebsmittelgruppe bmg;
    private LinkedList<Betriebsmittelgruppe> bmgList;
            
            
    public DatenbankTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.testRef = false;
        this.sql = new SQLConnect();
        this.netzList = new LinkedList<Netzplan>();
        this.netz = new Netzplan();
        this.testInt = 1;
        this.vorg = new Vorgang();
        this.vorgList = new LinkedList<Vorgang>();
        this.bmg = new Betriebsmittelgruppe();
        this.bmgList = new LinkedList<Betriebsmittelgruppe>();
    }
    
    @After
    public void tearDown() {
        this.netzList.clear();
        this.vorgList.clear();
        this.bmgList.clear();
        this.testInt = 0;
    }

    @Test
    public void testLadeAlleNetzplaene() throws SQLException {  
        this.netzList = sql.ladeAlleNetzplaene();
       if(this.netzList.size() != 0) {
            this.testRef = true;
       }
       assertTrue(this.testRef);
    }
    
    @Test
    public void testlaNetzplan() throws SQLException {
        this.netz = sql.ladeNetzplan(testInt);
       assertEquals(this.netz.getId(),testInt);
    }
    
    @Test
    public void testLadeVorgaenge() throws SQLException {
        this.vorgList = sql.ladeVorgaenge(testInt);
        assertNotNull(this.vorgList.size());
    }
    
    @Test
    public void testLadeAlleBmg() throws SQLException {
        this.bmgList = sql.ladeAlleBetriebsMittelGruppen();
        assertNotNull(this.bmgList.size());
    }
    
    @Test
    public void testLadeAlleNachfolger() throws SQLException {
        this.testInt = 10;
        this.vorgList = sql.ladeAlleNachf(testInt);
        assertEquals(0,this.vorgList.size());
    }
    
    @Test
    public void testLadeAlleVorgaenger() throws SQLException {
        this.vorgList = sql.ladeAlleVorg(testInt);
        assertEquals(0,this.vorgList.size());
    }
       
    @Test
    public void testLadeAlleBmgZuVorg() throws SQLException {
        this.bmgList = sql.ladeAlleBetrZuVorg(testInt, testInt);
        assertNotNull(this.bmgList.size());
    }
    
    @Test
    public void testLadeAlleVorgZuBmg() throws SQLException {
        this.vorgList = sql.ladeAlleVorgZuBetr(1001, testInt);
        assertNotNull(this.vorgList.size());
    }
    
    @Test
    public void testLade() throws SQLException {
        this.bmgList = sql.getBetriebsmittelkapazitaetForId(testInt, testInt);
        assertEquals(100,this.bmgList.getFirst().getBetrMittelKapa(),0);
    }
    
    @Test
    public void testCheckNetzplanId() throws SQLException {
        this.testRef = sql.checkNetzplanId(testInt);
        assertTrue(this.testRef);
    }
            
}
