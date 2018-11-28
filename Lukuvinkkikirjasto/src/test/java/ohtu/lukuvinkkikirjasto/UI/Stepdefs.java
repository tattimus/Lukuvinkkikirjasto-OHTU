package ohtu.lukuvinkkikirjasto.UI;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import ohtu.lukuvinkkikirjasto.IO.AsyncStubIO;
import ohtu.lukuvinkkikirjasto.dao.MockHintDAO;
import ohtu.lukuvinkkikirjasto.actions.AddHint;
import ohtu.lukuvinkkikirjasto.actions.QueryHints;
import ohtu.lukuvinkkikirjasto.actions.SearchByTag;
import ohtu.lukuvinkkikirjasto.dao.MockTagDAO;
import ohtu.lukuvinkkikirjasto.dao.MockTagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.tag.Tag;
import static org.junit.Assert.*;

public class Stepdefs {

    AsyncStubIO stubIO = new AsyncStubIO();
    MockHintDAO mockDao = new MockHintDAO();
    MockTagDAO tagDAO = new MockTagDAO();

    MockTagHintAssociationTable connect = new MockTagHintAssociationTable();

    AddHint addHint;
    QueryHints queryHints;
    SearchByTag searchTag;
    

    App app;

    @Given("^Ohjelma on käynnistetty$")
    public void ohjelma_on_käynnistetty() throws Throwable {
        addHint = new AddHint(mockDao, tagDAO, connect);
        queryHints = new QueryHints(mockDao);
        searchTag = new SearchByTag(mockDao,tagDAO,connect);
        app = new App(stubIO, addHint, queryHints,searchTag);
        app.start();
    }

    @When("^Käyttäjä valitsee vinkin lisäämisen ja syöttää otsikoksi \"([^\"]*)\" ja kommentiksi \"([^\"]*)\" ja jättää tagit ja URLin tyhjäksi$")
    public void käyttäjä_valitsee_vinkin_lisäämisen_ja_syöttää_otsikoksi_ja_kommentiksi(String otsikko, String kommentti) throws Throwable {
        //Valitse vinkin lisääminen
        stubIO.pushInt(app.findAction(addHint.getHint()));
        wait(500);
        //Syötä otsikko ja kommentti
        stubIO.pushString(otsikko);
        stubIO.pushString(kommentti);
        stubIO.pushString("\n"); //ei tageja
        stubIO.pushString("\n"); //ei URLia
        wait(500);
    }
    
    @When("^Käyttäjä valitsee vinkin lisäämisen ja syöttää otsikoksi \"([^\"]*)\" ja kommentiksi \"([^\"]*)\" ja tageiksi \"([^\"]*)\"$")
    public void käyttäjä_valitsee_vinkin_lisäämisen_ja_syöttää_otsikon_ja_kommentin_ja_tagit(String otsikko, String kommentti, String tagit) throws Throwable{
        stubIO.pushInt(app.findAction(addHint.getHint()));
        wait(500);
        stubIO.pushString(otsikko);
        stubIO.pushString(kommentti);
        stubIO.pushString(tagit); 
        stubIO.pushString("\n");
        wait(500);
    }

    @Then("^Kirjastoon on lisätty vinkki, jolla on otsikkona \"([^\"]*)\" ja kommenttina \"([^\"]*)\"$")
    public void kirjastoon_on_lisätty_vinkki_jolla_on_otsikkona_ja_kommenttina(String otsikko, String kommentti) throws Throwable {
        boolean added = mockDao.findAll().stream().anyMatch(hint -> hint.getTitle().equals(otsikko) && hint.getComment().equals(kommentti));

        assertTrue(added);
    }

    @Then("^Kirjastoon on lisätty vinkki, jolla on otsikkona \"([^\"]*)\" ja kommenttina \"([^\"]*)\" ja ei tageja$")
    public void kirjastoon_on_lisatty_vinkki_jolla_on_otsikkona_ja_kommenttina_ilman_tageja(String otsikko, String kommentti) throws Throwable {
         Optional<HintClass> h=mockDao.findAll().stream().filter(hint -> hint.getTitle().equals(otsikko) && hint.getComment().equals(kommentti)).findAny();
         assertTrue(stubIO.getOutput().contains("Lisätty vinkki \"" + otsikko + "\""));
         assertTrue(h.isPresent());
         assertTrue(connect.findAForB(h.get()).isEmpty());
    }

    @Then("^Kirjastoon on lisätty vinkki, jolla on otsikkona \"([^\"]*)\" ja kommenttina \"([^\"]*)\" ja tagina \"([^\"]*)\"$")
    public void kirjastoon_on_lisätty_vinkki_jolla_on_otsikkona_ja_kommenttina_ja_tageina(String otsikko, String kommentti, String tagit) throws Throwable {
        Optional<HintClass> h=mockDao.findAll().stream().filter(hint -> hint.getTitle().equals(otsikko) && hint.getComment().equals(kommentti)).findAny();
        List<String> tags =extractTags(tagit);
        List<Tag> savedTags=tagDAO.findAll().stream().filter(tag->tags.contains(tag.getTag())).distinct().collect(Collectors.toList());
        assertTrue(stubIO.getOutput().contains("Lisätty vinkki \"" + otsikko + "\""));
        assertTrue(h.isPresent());
        for(Tag t: savedTags) {
            assertTrue(connect.findBForA(t).stream().anyMatch(hint->hint.getID().equals(h.get().getID())));
        }

    }


    @When("^Käyttäjä valitsee vinkin lisäämisen ja jättää otsikon tyhjäksi ja jättää kommentin tyhjäksi ja jättää tagit tyhjäksi ja jättää URLin tyhjäksi$")
    public void käyttäjä_valitsee_vinkin_lisäämisen_ja_jättää_otsikon_tyhjäksi_ja_jättää_kommentin_tyhjäksi_ja_jättää_tagit_tyhjäksi_ja_jättää_URLin_tyhjäksi() throws Throwable {
        //Valitse vinkin lisääminen
        stubIO.pushInt(app.findAction(addHint.getHint()));
        wait(500);
        //Jätä kaikki kentät tyhjiksi
        stubIO.pushString(null);
        stubIO.pushString(null);
        stubIO.pushString(null);
        stubIO.pushString(null);
        wait(500);
    }

    @Then("^Ohjelma tulostaa \"([^\"]*)\"$")
    public void ohjelma_tulostaa(String output) throws Throwable {
        assertTrue(stubIO.getOutput().stream().filter(line -> line.contains(output)).findAny().isPresent());
    }


    @Given("^Ohjelma pysähtyy$")
    public void ohjelma_pysähtyy() throws Throwable {
        stubIO.pushInt(app.findAction("Lopeta"));
        app.join(500);
    }
    
    
    @When("^Käyttäjä valitsee tagilla hakemisen ja antaa tagin \"([^\"]*)\"$")
    public void käyttäjä_valitsee_tagilla_hakemisen_ja_antaa_tagin(String tag) throws Throwable {
        stubIO.pushInt(app.findAction(searchTag.getHint()));
        wait(500);
        stubIO.pushString(tag);
        wait(500);
    }
    
    
    @Given("^Tietokantaan on tallennettu vinkki otsikkolla \"([^\"]*)\", kuvauksella \"([^\"]*)\" ja tagilla \"([^\"]*)\"$")
    public void tietokantaa_on_tallennettu_vinkki_otsikkolla_kuvauksella_tagilla(String otsikko, String kuvaus, String tag) throws Throwable {
        stubIO.pushInt(app.findAction(addHint.getHint()));
        wait(500);
        stubIO.pushString(otsikko);
        stubIO.pushString(kuvaus);
        stubIO.pushString(tag);
        stubIO.pushString(null);
        wait(500);
    }
    
    @When("^Käyttäjä valitsee vinkkien listauksen$")
    public void käyttäjä_valitsee_vinkkien_listauksen() throws Throwable {
        stubIO.pushInt(app.findAction(queryHints.getHint()));
        wait(500);
    }
    
     @Given("^Tietokantaan on tallennettu vinkki otsikkolla \"([^\"]*)\", kuvauksella \"([^\"]*)\" ja tagilla \"([^\"]*)\" ja urlilla \"([^\"]*)\"$")
    public void tietokantaa_on_tallennettu_vinkki_otsikkolla_kuvauksella_tagilla_urlilla(String otsikko, String kuvaus, String tag,String url) throws Throwable {
        stubIO.pushInt(app.findAction(addHint.getHint()));
        wait(500);
        stubIO.pushString(otsikko);
        stubIO.pushString(kuvaus);
        stubIO.pushString(tag);
        stubIO.pushString(url);
        wait(500);
    }
    //    Then Kirjastoon on lisätty vinkki, jolla on otsikkona "otsikko" ja kommenttina "kommentti" ja urlina "url"
    @Then("^Kirjastoon on lisätty vinkki, jolla on otsikkona \"([^\"]*)\" ja kommenttina \"([^\"]*)\" ja urlina \"([^\"]*)\"$")
    public void kirjastoon_on_lisätty_vinkki_jolla_url(String otsikko, String kommentti, String url) throws Throwable {
        boolean added = mockDao.findAll().stream().anyMatch(hint -> hint.getTitle().equals(otsikko)
                && hint.getComment().equals(kommentti)
                && hint.getUrl().equals(url));

        assertTrue(added);
    }
    
    
    @Given("^Tietokantaan on tallenettu tagi \"([^\"]*)\"$")
    public void tietokantaan_on_tallenettu_tagi(String tag) throws Throwable {
        tagDAO.insert(new Tag(null,tag));
    }


    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
        }
    }
    private List<String> extractTags(String tags) {
        List<String> ret=new ArrayList<>();
        for(String s: tags.split(",")) {
            String str=s.trim();
            ret.add(str);
        }
        return ret;
    }
}
