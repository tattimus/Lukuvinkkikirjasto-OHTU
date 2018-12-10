/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.requests;

import java.sql.Timestamp;
import java.util.Date;
import ohtu.lukuvinkkikirjasto.IO.AsyncStubIO;
import ohtu.lukuvinkkikirjasto.actions.QueryReadHints;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.MockHintDAO;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author y50u
 */
public class QueryReadHintsTest {

    private QueryReadHints qrh;
    private HintDAO dao;
    private AsyncStubIO io;

    @Before
    public void setUp() throws Exception {
        dao = new MockHintDAO();
        qrh = new QueryReadHints(dao);
        io = new AsyncStubIO();
    }

    @Test
    public void returnsHintWithCorrectTimestamp() throws Exception {
        HintClass hint = new HintClass(null, "otsikko", "kommentti", "www.example.com");
        hint.setTimestamp(new Timestamp(System.currentTimeMillis()));
        
        int id = dao.insert(hint);
        hint.setID(id);
        
        dao.update(hint);
        
        qrh.run(io);  
        assertTrue(io.getOutput().contains(hint.listingAll()));
    }
    @Test
    public void doesntReturnHintWithNoTimestamp() throws Exception {
        HintClass hint = new HintClass(null, "otsikko", "kommentti", "www.example.com");
        
        int id = dao.insert(hint);
        hint.setID(id);
        
        dao.update(hint);
        
        qrh.run(io);  
        assertTrue(!io.getOutput().contains(hint.listingAll()));
    }
}
