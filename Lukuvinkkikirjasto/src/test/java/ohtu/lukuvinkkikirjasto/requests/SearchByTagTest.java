
package ohtu.lukuvinkkikirjasto.requests;

import ohtu.lukuvinkkikirjasto.IO.AsyncStubIO;
import ohtu.lukuvinkkikirjasto.actions.QueryHints;
import ohtu.lukuvinkkikirjasto.actions.SearchByTag;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.MockHintDAO;
import ohtu.lukuvinkkikirjasto.dao.MockTagDAO;
import ohtu.lukuvinkkikirjasto.dao.MockTagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.tag.Tag;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author lindradi
 */
public class SearchByTagTest {  
    private HintDAO dao;
    private TagDAO tdao;
    private AsyncStubIO io;
    private TagHintAssociationTable asso;
    private SearchByTag sbt;
    
    private HintClass hint1;
    private HintClass hint2;
    private HintClass hint3;
    
    @Before
    public void setUp() throws Exception {
        dao = new MockHintDAO();
        tdao = new MockTagDAO();
        asso = new MockTagHintAssociationTable();
        io = new AsyncStubIO();
        sbt = new SearchByTag(dao,tdao,asso);
        
        Tag kirja = new Tag(0,"kirja");
        Tag classic = new Tag(1,"classic");
        tdao.insertOrGet(kirja);
        tdao.insertOrGet(classic);
        
        hint1 = new HintClass(0,"test testbok","testing material", "www.example.com");
        hint2 = new HintClass(1,"klassikko kirja","kuuluisa", "www.example.com");
        hint3 = new HintClass(2,"legandary pasta","yum", "www.example.com");
        dao.insert(hint1);
        dao.insert(hint2);
        dao.insert(hint2);
        
        asso.associate(kirja, hint1);
        asso.associate(kirja, hint2);
        asso.associate(classic, hint2);
        asso.associate(classic, hint3);
    }

    @Test
    public void findsCorrectHintsWithTag() throws Exception {
        io.pushString("kirja");
        sbt.run(io);
        assertTrue(io.getOutput().contains(hint1.toString()));
        assertTrue(io.getOutput().contains(hint2.toString()));
    }
    @Test
    public void findsHintswithMultipleTags() throws Exception {
        io.pushString("kirja");
        sbt.run(io);
        assertTrue(io.getOutput().contains(hint2.toString()));
        io.pushString("classic");
        sbt.run(io);
        assertTrue(io.getOutput().contains(hint2.toString()));      
    }
    @Test
    public void givesErrorwhenWrongTag() throws Exception{
        io.pushString("bad");
        sbt.run(io);
        assertTrue(io.getOutput().contains("Haettua tagia ei ole olemassa"));
    }

}
