/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.requests;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import ohtu.lukuvinkkikirjasto.IO.AsyncStubIO;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.actions.ShowHint;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.MockHintDAO;
import ohtu.lukuvinkkikirjasto.dao.MockTagDAO;
import ohtu.lukuvinkkikirjasto.dao.MockTagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
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
public class ShowHintTest {

    private HintDAO hdao;
    private TagDAO tdao;
    private TagHintAssociationTable connect;
    private AsyncStubIO io;
    private ShowHint showhint;
    HintClass hint1;
    HintClass hint2;
    HintClass hint3;
    Tag tag1;
    Tag tag2;

    public ShowHintTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        this.hdao = new MockHintDAO();
        this.tdao = new MockTagDAO();
        this.connect = new MockTagHintAssociationTable();
        this.io = new AsyncStubIO();

        hint1 = new HintClass(0, "otsikko1", "kommentti1", "url1");
        hint2 = new HintClass(1, "otsikko2", "kommentti2", "url2");
        hdao.insert(hint1);
        hdao.insert(hint2);

        tag1 = new Tag(0, "tagi");
        tag2 = new Tag(1, "tagi2");

        tdao.insert(tag1);
        tdao.insert(tag2);
        connect.associate(tag1, hint2);
        connect.associate(tag2, hint2);

        this.showhint = new ShowHint(hdao, tdao, connect);

    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void HintWithoutTagsIsShown() throws Exception {
        io.pushInt(0);
        io.pushString("n");
        showhint.run(io);
        assertTrue(io.getOutput().contains(hint1.printAll()));
        assertTrue(io.getOutput().toString().contains("Merkitäänkö luetuksi(y/n)"));
    }

    @Test
    public void HintWithTagsIsShown() throws Exception {
        io.pushInt(1);
        io.pushString("n");
        showhint.run(io);
        assertTrue(io.getOutput().contains(hint2.printAll()));
        assertTrue(io.getOutput().toString().contains(tag1.toString()) && io.getOutput().toString().contains(tag2.toString()));
        assertTrue(io.getOutput().toString().contains("Merkitäänkö luetuksi(y/n)"));
    }

    @Test
    public void TimestampIsSet() throws Exception {
        io.pushInt(1);
        io.pushString("y");
        showhint.run(io);

        assertTrue(io.getOutput().toString().contains("Vinkki on merkitty luetuksi"));
        assertFalse(hdao.findOne(1).getTimestamp() == null);

    }

    @Test
    public void TimestampIsShown() throws Exception {
        Date d=new Timestamp(System.currentTimeMillis());
        hdao.update(new HintClass(0, "otsikko1", "kommentti1", "url1", d));
        io.pushInt(0);
        showhint.run(io);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        assertTrue(io.getOutput().toString().contains("luettu: "+sdf.format(d)));
        assertFalse(io.getOutput().toString().contains("Merkitäänkö luetuksi(y/n)"));

    }
}
