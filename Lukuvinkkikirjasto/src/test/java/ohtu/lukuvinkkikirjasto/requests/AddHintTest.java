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
import ohtu.lukuvinkkikirjasto.dao.MakerDAO;
import ohtu.lukuvinkkikirjasto.dao.MakerHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.MockHintDAO;
import ohtu.lukuvinkkikirjasto.dao.MockTagDAO;
import ohtu.lukuvinkkikirjasto.dao.MockTagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.SQLHintDAO;
import ohtu.lukuvinkkikirjasto.dao.SQLTagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.database.Database;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.tag.Tag;
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
    TagDAO tagDAO;
    MakerDAO makerDao;
    TagHintAssociationTable connect;
    MakerHintAssociationTable makerHint;

    @Before
    public void setUp() throws Exception {
        hintDao = new MockHintDAO();
        tagDAO = new MockTagDAO();
        connect = new MockTagHintAssociationTable();
        this.newHint = new AddHint(hintDao, tagDAO, makerDao, connect,makerHint);

    }

    @Test
    public void hintCanBeAdded() throws Exception {
        AsyncStubIO io = new AsyncStubIO();
        io.pushString("otsikko");
        io.pushString("kommentti");
        io.pushString("");
        io.pushString("");
        io.pushString(null);

        newHint.run(io);

        assertTrue(hintDao.findAll().stream().anyMatch(hint -> hint.getTitle().equals("otsikko") && hint.getComment().equals("kommentti")));
    }

    @Test
    public void hintCanBeAddedWithSeveralTags() throws Exception {
        AsyncStubIO io = new AsyncStubIO();
        io.pushString("otsikko");
        io.pushString("kommentti");
        io.pushString("");
        io.pushString("tagi, tagi2");
        io.pushString(null);

        newHint.run(io);
        
        assertTrue(tagDAO.findAll().stream().allMatch(tag -> tag.getTag().equals("tagi") || tag.getTag().equals("tagi2")));

    }

    @Test
    public void addingHintWithSameTagManyTimesDoesNotCrash() throws InterruptedException {
        AsyncStubIO io = new AsyncStubIO();
        io.pushString("otsikko");
        io.pushString("kommentti");
        io.pushString("");
        io.pushString("video,video,video,video,video,video,video");
        io.pushString(null);
        
        newHint.run(io);
        
        assertTrue(io.getOutput().stream().anyMatch(s -> s.contains("Lisätty vinkki")));
    }
    
    @Test
    public void TagsAreAssignedToHints() throws Exception {
        AsyncStubIO io = new AsyncStubIO();
        io.pushString("otsikko");
        io.pushString("kommentti");
        io.pushString("");
        io.pushString("tagi, tagi2");
        io.pushString(null);

        newHint.run(io);

        io.pushString("toinen");
        io.pushString("toinen kommentti");
        io.pushString("");
        io.pushString("tagi, tagi4");
        io.pushString(null);

        newHint.run(io);
        ArrayList<HintClass> hints = new ArrayList<>(hintDao.findAll());
        for (HintClass h : hints) {
            if (h.getTitle().equals("otsikko")) {

                assertTrue(connect.findAForB(h).stream().allMatch(tag -> tag.getTag().equals("tagi") || tag.getTag().equals("tagi2")));

            }
            if (h.getTitle().equals("toinen")) {
                assertTrue(connect.findAForB(h).stream().allMatch(tag -> tag.getTag().equals("tagi") || tag.getTag().equals("tagi4")));
            }
        }

    }

    @Test
    public void MakersCanBeAssignedtoHints() throws Exception {

        AsyncStubIO io = new AsyncStubIO();
        io.pushString("otsikko");
        io.pushString("kommentti");
        io.pushString("tekija1,tekija2");
        io.pushString("");
        io.pushString(null);

        newHint.run(io);

        ArrayList<HintClass> hints = new ArrayList<>(hintDao.findAll());
        for (HintClass h : hints) {
                assertTrue(connect.findAForB(h).stream().allMatch(tag -> tag.getTag().equals("tekija") || tag.getTag().equals("tekija")));
        }
    }
}
