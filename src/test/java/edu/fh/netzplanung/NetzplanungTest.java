/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzplanung;

import datenbank.SQLConnect;
import edu.fh.application.Netzplanung;
import java.sql.SQLException;
import netzplan.Netzplan;
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
public class NetzplanungTest {
    private SQLConnect sql;
    private Netzplan netz;
    private Netzplanung netzPl; 
    private int testInt;
    
    public NetzplanungTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.testInt = 1;
        this.sql = new SQLConnect();
        this.netz = new Netzplan(); 
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testNetzplanBerechnnung() throws SQLException {
        this.netz = sql.ladeNetzplan(testInt);
        this.netzPl = new Netzplanung(netz.getId()); 
        assertTrue(this.netzPl.netzPlanBerechnung());
    }
}
