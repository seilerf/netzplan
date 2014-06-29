/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.datenbank;

import datenbank.SQLConnect;
import junit.framework.TestCase;
import netzplan.Netzplan;

/**
 *
 * @author AdminMax
 */
public class datenbankTest extends TestCase {
    private SQLConnect sql = new SQLConnect();
    private Netzplan netz = new Netzplan();
    private int testId = 1;

    public datenbankTest(String testName) throws Exception {
        super(testName);
        //this.testLadeAlleNetzplaene();
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        netz = sql.ladeNetzplan(1);
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testLadeAlleNetzplaene()throws Exception {
        this.setUp();
        sql.ladeAlleNetzplaene();
        
    }
}
