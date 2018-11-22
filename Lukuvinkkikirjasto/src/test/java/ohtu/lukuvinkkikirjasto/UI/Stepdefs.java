package ohtu.lukuvinkkikirjasto.UI;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.lukuvinkkikirjasto.IO.AsyncStubIO;
import ohtu.lukuvinkkikirjasto.IO.StubIO;
import ohtu.lukuvinkkikirjasto.dao.MockHintDAO;
import ohtu.lukuvinkkikirjasto.actions.AddHint;
import ohtu.lukuvinkkikirjasto.actions.QueryHints;
import ohtu.lukuvinkkikirjasto.dao.MockTagDAO;
import ohtu.lukuvinkkikirjasto.dao.MockTagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import static org.junit.Assert.*;

public class Stepdefs {

    AsyncStubIO stubIO = new AsyncStubIO();
    MockHintDAO mockDao = new MockHintDAO();
    MockTagDAO tagDAO=new MockTagDAO();

    MockTagHintAssociationTable connect=new MockTagHintAssociationTable();
 
    App app;

    @Given("^Ohjelma on käynnistetty$")
    public void ohjelma_on_käynnistetty() throws Throwable {
        app = new App(stubIO, new AddHint(mockDao, tagDAO, connect), new QueryHints(mockDao));
        app.start();
    }

    @When("^Käyttäjä valitsee vinkin lisäämisen ja syöttää otsikoksi \"([^\"]*)\" ja kommentiksi \"([^\"]*)\" ja tageiksi \"([^\"]*)\"$")
    public void käyttäjä_valitsee_vinkin_lisäämisen_ja_syöttää_otsikoksi_ja_kommentiksi(String otsikko, String kommentti, String tagit) throws Throwable {
        //Valitse vinkin lisääminen
        stubIO.pushInt(1);
        wait(500);
        //Syötä otsikko ja kommentti
        stubIO.pushString(otsikko);
        stubIO.pushString(kommentti);
        stubIO.pushString(tagit);
        wait(500);
    }

    @Then("^Kirjastoon on lisätty vinkki, jolla on otsikkona \"([^\"]*)\" ja kommenttina \"([^\"]*)\"$")
    public void kirjastoon_on_lisätty_vinkki_jolla_on_otsikkona_ja_kommenttina(String otsikko, String kommentti) throws Throwable {
        boolean added = mockDao.findAll().stream().anyMatch(hint -> hint.getTitle().equals(otsikko) && hint.getComment().equals(kommentti));

        assertTrue(added);
    }

    @Given("^Ohjelma pysähtyy$")
    public void ohjelma_pysähtyy() throws Throwable {
        stubIO.pushInt(app.findAction("Lopeta"));
        app.join(500);
    }

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
        }
    }
}
