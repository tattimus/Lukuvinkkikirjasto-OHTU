package ohtu.lukuvinkkikirjasto.UI;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import ohtu.lukuvinkkikirjasto.IO.AsyncStubIO;
import ohtu.lukuvinkkikirjasto.dao.MockHintDAO;
import ohtu.lukuvinkkikirjasto.actions.AddHint;
import ohtu.lukuvinkkikirjasto.actions.DeleteHint;
import ohtu.lukuvinkkikirjasto.actions.ModifyHint;
import ohtu.lukuvinkkikirjasto.actions.QueryHints;
import ohtu.lukuvinkkikirjasto.actions.SearchByTag;
import ohtu.lukuvinkkikirjasto.actions.ShowHint;
import ohtu.lukuvinkkikirjasto.dao.MockTagDAO;
import ohtu.lukuvinkkikirjasto.dao.MockTagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.hint.Hint;
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
    DeleteHint deleteHint;
    ShowHint showHint;
    ModifyHint modifyHint;


    App app;

    @Given("^Ohjelma on käynnistetty$")
    public void ohjelma_on_käynnistetty() throws Throwable {
        addHint = new AddHint(mockDao, tagDAO, connect);
        queryHints = new QueryHints(mockDao);
        searchTag = new SearchByTag(mockDao, tagDAO, connect);
        deleteHint = new DeleteHint(mockDao);

        showHint = new ShowHint(mockDao, tagDAO, connect);
        modifyHint = new ModifyHint(mockDao,tagDAO,connect);
        app = new App(stubIO, addHint, queryHints,searchTag, showHint, deleteHint,modifyHint);

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
    public void käyttäjä_valitsee_vinkin_lisäämisen_ja_syöttää_otsikon_ja_kommentin_ja_tagit(String otsikko, String kommentti, String tagit) throws Throwable {
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
        Optional<HintClass> h = mockDao.findAll().stream().filter(hint -> hint.getTitle().equals(otsikko) && hint.getComment().equals(kommentti)).findAny();
        assertTrue(stubIO.getOutput().contains("Lisätty vinkki \"" + otsikko + "\""));
        assertTrue(h.isPresent());
        assertTrue(connect.findAForB(h.get()).isEmpty());
    }

    @Then("^Kirjastoon on lisätty vinkki, jolla on otsikkona \"([^\"]*)\" ja kommenttina \"([^\"]*)\" ja tagina \"([^\"]*)\"$")
    public void kirjastoon_on_lisätty_vinkki_jolla_on_otsikkona_ja_kommenttina_ja_tageina(String otsikko, String kommentti, String tagit) throws Throwable {
        Optional<HintClass> h = mockDao.findAll().stream().filter(hint -> hint.getTitle().equals(otsikko) && hint.getComment().equals(kommentti)).findAny();
        List<String> tags = extractTags(tagit);
        List<Tag> savedTags = tagDAO.findAll().stream().filter(tag -> tags.contains(tag.getTag())).distinct().collect(Collectors.toList());
        assertTrue(stubIO.getOutput().contains("Lisätty vinkki \"" + otsikko + "\""));
        assertTrue(h.isPresent());
        for (Tag t : savedTags) {
            assertTrue(connect.findBForA(t).stream().anyMatch(hint -> hint.getID().equals(h.get().getID())));
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

    @When("^Käyttäjä valitsee vinkin  näyttämisen ja antaa id (\\d+) ja antaa syötteen \"([^\"]*)\"$")
    public void Kayttaja_valitsee_vinkin_nayttamisen_ja_antaa_id_ja_syote(int id, String syote) throws Throwable {
        stubIO.pushInt(app.findAction(showHint.getHint()));
        wait(500);
        stubIO.pushInt(id);
        wait(500);
        stubIO.pushString(syote);
        wait(500);
    }

    @When("^Käyttäjä valitsee vinkin  näyttämisen ja antaa id (\\d+)$")
    public void Kayttaja_valitsee_vinkin_nayttamisen_ja_antaa_id(int id) throws Throwable {
        stubIO.pushInt(app.findAction(showHint.getHint()));
        wait(500);
        stubIO.pushInt(id);
        wait(500);

    }

    @Then("^Vinkillä id (\\d+) on aikaleima$")
    public void vinkilla_on_aikaleima(int id) throws Throwable {
        HintClass hint = mockDao.findOne(id);
        assertTrue(hint.getTimestamp() != null);
    }

    @Then("^Vinkillä id (\\d+) ei ole aikaleimaa$")
    public void vinkilla_ei_ole_aikaleimaa(int id) throws Throwable {
        HintClass hint = mockDao.findOne(id);
        assertTrue(hint.getTimestamp() == null);
    }
    @Then("Vinkin (\\d+) lukukuittaus tulostuu oikein$")
    public void Vinkin_lukukuittaus_tulostuu(int id) throws Throwable {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s=sdf.format(mockDao.findOne(id).getTimestamp());

        assertTrue(stubIO.getOutput().stream().filter(line -> line.contains("luettu: "+ s)).findAny().isPresent());
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

    @Given("^Tietokantaan on tallennettu vinkki id:llä (\\d+), otsikolla \"([^\"]*)\", kuvauksella \"([^\"]*)\", tagilla \"([^\"]*)\" ja urlilla \"([^\"]*)\"$")
    public void tietokantaan_on_tallennettu_vinkki_otsikkolla_kuvauksella_ja_id_llä(int id, String otsikko, String kuvaus, String tag, String url) throws Throwable {
        HintClass h = new HintClass(id, otsikko, kuvaus, url);
        mockDao.insert(h);
        Tag t = new Tag(0, tag);
        tagDAO.insert(t);
        this.connect.associate(t, h);
    }

    @When("^Käyttäjä valitsee vinkkien listauksen$")
    public void käyttäjä_valitsee_vinkkien_listauksen() throws Throwable {
        stubIO.pushInt(app.findAction(queryHints.getHint()));
        wait(500);
    }

    @Given("^Tietokantaan on tallennettu vinkki otsikkolla \"([^\"]*)\", kuvauksella \"([^\"]*)\" ja tagilla \"([^\"]*)\" ja urlilla \"([^\"]*)\"$")
    public void tietokantaa_on_tallennettu_vinkki_otsikkolla_kuvauksella_tagilla_urlilla(String otsikko, String kuvaus, String tag, String url) throws Throwable {
        stubIO.pushInt(app.findAction(addHint.getHint()));
        wait(500);
        stubIO.pushString(otsikko);
        stubIO.pushString(kuvaus);
        stubIO.pushString("");
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
        tagDAO.insert(new Tag(null, tag));
    }

    @When("^Käyttäjä valitsee Vinkin poistamisen, antaa väärän ID \"([^\"]*)\"$")
    public void käyttäjä_valitsee_Vinkin_poistamisen_antaa_väärän_ID(String id) throws Throwable {
        stubIO.pushInt(app.findAction(deleteHint.getHint()));
        wait(500);
        stubIO.pushString(id);
        wait(500);
    }

    @Given("^Tietokantaan on tallennettu vinkki otsikkolla \"([^\"]*)\", kuvauksella \"([^\"]*)\" ja id:llä (\\d+)$")
    public void tietokantaan_on_tallennettu_vinkki_otsikkolla_kuvauksella_ja_id_llä(String otsikko, String kuvaus, int id) throws Throwable {
        mockDao.insert(new HintClass(null, otsikko, kuvaus, ""));
    }

    @When("^Käyttäjä valitsee Vinkin poistamisen, antaa ID \"([^\"]*)\" ja vahvistaa että haluaa poistaa vinkin$")
    public void käyttäjä_valitsee_Vinkin_poistamisen_antaa_ID_ja_vahvistaa_että_haluaa_poistaa_vinkin(String id) throws Throwable {
        stubIO.pushInt(app.findAction(deleteHint.getHint()));
        wait(500);
        stubIO.pushString(id);
        wait(500);
        stubIO.pushString("y");
        wait(500);
    }

    @Then("^Kirjastosta ei enään löydy vinkkiä id:llä (\\d+)$")
    public void kirjastosta_ei_enään_löydy_vinkkiä_id_llä(int id) throws Throwable {
        assertTrue(mockDao.findOne(id) == null);
    }

    @When("^Käyttäjä valitsee Vinkin poistamisen, antaa ID \"([^\"]*)\" ja ei vahvista poistamista$")
    public void käyttäjä_valitsee_Vinkin_poistamisen_antaa_ID_ja_ei_vahvista_poistamista(String id) throws Throwable {
        stubIO.pushInt(app.findAction(deleteHint.getHint()));
        wait(500);
        stubIO.pushString(id);
        wait(500);
        stubIO.pushString("n");
        wait(500);
    }

    @Then("^Kirjastosta löytyy id:llä (\\d+) vinkki jonka otsikko \"([^\"]*)\" ja kuvaus \"([^\"]*)\"$")
    public void kirjastosta_löytyy_id_llä_vinkki_jonka_otsikko_ja_kuvaus(int id, String otsikko, String kommentti) throws Throwable {
        assertTrue(mockDao.findOne(id).getTitle().equals(otsikko) && mockDao.findOne(id).getComment().equals(kommentti));
    }

    @When("^Käyttäjä valitsee vinkin muokkaamisen$")
    public void käyttäjä_valitsee_vinkin_muokkaamisen() throws Throwable {
        stubIO.pushInt(app.findAction(modifyHint.getHint()));
    }

    @When("^Syöttää muokattavan vinkin ID:ksi (\\d+) ja otsikoksi \"([^\"]*)\" ja jättää muut kentät tyhjäksi$")
    public void syöttää_muokattavan_vinkin_ID_ksi_ja_otsikoksi_ja_jättää_muut_kentät_tyhjäksi(int id, String title) throws Throwable {
        stubIO.pushInt(id);
        stubIO.pushString(title);

        stubIO.pushString("");
        stubIO.pushString("");
        stubIO.pushString("");

        wait(500);
    }

    @When("^Varmistaa muutokset valitsemalla \"([^\"]*)\"$")
    public void varmistaa_muutokset_valitsemalla(String selection) throws Throwable {
        stubIO.pushString(selection);

        wait(500);
    }

    @Then("^Vinkin (\\d+) otsikko on \"([^\"]*)\"$")
    public void vinkin_otsikko_on(int id, String title) throws Throwable {
        assertEquals(title, mockDao.findOne(id).getTitle());
    }
    
    @Then("^Vinkin ID (\\d+) Aikaleima tulostuu$")
    public void vinkin_ID_Aikaleima_tulostuu(int id) throws Throwable {
        ohjelma_tulostaa(mockDao.findOne(id).getTimestamp().toString().substring(0,16));
    }
    
    @Then("^Vinkin ID (\\d+) Aikaleima ei tulostuu$")
    public void vinkin_ID_Aikaleima_ei_tulostuu(int id) throws Throwable {
        assertFalse(stubIO.getOutput().stream().filter(line -> line.contains("luettu")).findAny().isPresent());
    }


    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
        }
    }

    private List<String> extractTags(String tags) {
        List<String> ret = new ArrayList<>();
        for (String s : tags.split(",")) {
            String str = s.trim();
            ret.add(str);
        }
        return ret;
    }
}
