/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.requests;

import ohtu.lukuvinkkikirjasto.IO.AsyncStubIO;
import ohtu.lukuvinkkikirjasto.actions.SearchByAttributes;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.MockHintDAO;
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
public class SearchByAttributesTest {

    private SearchByAttributes openSearch;
    private HintDAO hdao;
    private AsyncStubIO io;

    private HintClass hint1;
    private HintClass hint2;
    private HintClass hint3;
    private HintClass hint4;

    public SearchByAttributesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        this.io = new AsyncStubIO();
        this.hdao = new MockHintDAO();
        this.openSearch = new SearchByAttributes(hdao);

        hint1 = new HintClass(null, "Tietoliikenteen perusteet", "Johdatus tietoverkkoihin", "www.cs.helsinki.fi/kirja");
        int id = hdao.insert(hint1);
        hint1.setID(id);
        hint2 = new HintClass(null, "Laskennan mallit", "Tietojenkäsittelytieteen teoreettinen puoli", "www.cs.helsinki.fi/kirja");
        id = hdao.insert(hint2);
        hint2.setID(id);

        hint3 = new HintClass(null, "Tietorakenteet ja algoritmit", "Tärkeimpien algoritmien toiminta", "www.cs.helsinki.fi/kirja");
        id = hdao.insert(hint3);
        hint3.setID(id);
        hint4 = new HintClass(null, "Tietokoneen toiminta", "tietokoneen toiminnan perusteita", "www.cs.helsinki.fi/kirja");
        id = hdao.insert(hint4);
        hint4.setID(id);

    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void returnsCorrectHintWithFullTitle() throws Exception {
        io.pushString(hint1.getTitle());
        this.openSearch.run(io);
        assertTrue(io.getOutput().stream().filter(s -> s.contains("Otsikko:")).allMatch(s -> s.contains(hint1.getTitle())));

    }

    @Test
    public void returnsCorrectHintWithFullComment() throws Exception {
        io.pushString(hint2.getComment());
        this.openSearch.run(io);
        assertTrue(io.getOutput().stream().filter(s -> s.contains("Kommentti:")).allMatch(s -> s.contains(hint2.getComment())));
    }

    @Test
    public void ReturnsCorrectHintswithPartialAttributes() throws Exception {
        io.pushString("Laskenn");
        this.openSearch.run(io);
        assertTrue(io.getOutput().stream().filter(s -> s.contains("Otsikko:")).allMatch(s -> s.contains(hint2.getTitle())));
    }

    @Test
    public void ReturnsHintsInCorrectOrder() throws Exception {
        io.pushString("tieto");
        this.openSearch.run(io);
        assertTrue(io.getOutput().toString().indexOf(hint1.getTitle()) < io.getOutput().toString().indexOf(hint2.getTitle()));
        assertTrue(io.getOutput().toString().indexOf(hint3.getTitle()) < io.getOutput().toString().indexOf(hint2.getTitle()));
        assertTrue(io.getOutput().toString().indexOf(hint4.getTitle()) < io.getOutput().toString().indexOf(hint2.getTitle()));
    }

    @Test
    public void ReturnsHintsInCorrectOrder2() throws Exception {
        io.pushString("toiminta");
        this.openSearch.run(io);
        assertTrue(io.getOutput().toString().indexOf(hint4.getTitle()) < io.getOutput().toString().indexOf(hint3.getTitle()));
    }
}
