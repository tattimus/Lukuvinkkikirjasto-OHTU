/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.requests;

import ohtu.lukuvinkkikirjasto.IO.AsyncStubIO;
import ohtu.lukuvinkkikirjasto.actions.ModifyHint;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.MockHintDAO;
import ohtu.lukuvinkkikirjasto.dao.MockTagDAO;
import ohtu.lukuvinkkikirjasto.dao.MockTagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author jaakko
 */
public class ModifyHintTest {
    HintDAO hintDao;
    TagDAO tagDao;
    TagHintAssociationTable association;
    AsyncStubIO io;
    
    ModifyHint action;
    
    @Before
    public void setup() {
        hintDao = new MockHintDAO();
        tagDao = new MockTagDAO();
        association = new MockTagHintAssociationTable();
        io = new AsyncStubIO();
        
        action = new ModifyHint(hintDao, tagDao, association);
    }
    
    @Test
    public void testCannotModifyNonexistentHint() throws InterruptedException {
        int hintId = 1;
        
        io.pushInt(hintId);
        action.run(io);
        
        assertTrue(io.getOutput().contains("ID:llä " + hintId + " ei löytynyt vinkkiä"));
    }
     
    @Test
    public void testCanModifyHintWithoutTags() throws InterruptedException, Exception {
        int id = hintDao.insert(new HintClass(null, "otsikko", "kommentti", "url"));
        
        assertNotNull(hintDao.findOne(id));
        
        io.pushInt(id);
        io.pushString("muokattu_otsikko");
        io.pushString("muokattu_kommentti");
        io.pushString("muokattu_url");
        //Ei tageja
        io.pushString("");
        //Tee muutokset
        io.pushString("y");
        
        action.run(io);
        
        HintClass updated = hintDao.findOne(id);
        assertEquals("muokattu_otsikko", updated.getTitle());
        assertEquals("muokattu_kommentti", updated.getComment());
        assertEquals("muokattu_url", updated.getUrl());
    }
}
