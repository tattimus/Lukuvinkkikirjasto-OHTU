/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.requests;

import java.sql.SQLException;
import ohtu.lukuvinkkikirjasto.actions.AddHint;
import java.util.ArrayList;
import java.util.List;
import ohtu.lukuvinkkikirjasto.IO.AsyncStubIO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.MockHintDAO;
import ohtu.lukuvinkkikirjasto.dao.SQLHintDAO;
import ohtu.lukuvinkkikirjasto.database.Database;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kape
 */
public class AddHintTest {
    AddHint newHint;
    HintDAO hintDao;

    @Before
    public void setUp() throws Exception {
        hintDao = new MockHintDAO();
        this.newHint = new AddHint(hintDao);
    }

    @Test
    public void hintCanBeAdded() throws Exception {
        AsyncStubIO io = new AsyncStubIO();
        io.pushString("otsikko");
        io.pushString("kommentti");
        
        newHint.run(io);
        
        assertTrue(hintDao.findAll().stream().anyMatch(hint -> hint.getTitle().equals("otsikko") && hint.getComment().equals("kommentti")));
    }
}
