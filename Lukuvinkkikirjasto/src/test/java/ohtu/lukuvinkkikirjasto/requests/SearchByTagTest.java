
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
    
    @Before
    public void setUp() throws Exception {
        dao = new MockHintDAO();
        tdao = new MockTagDAO();
        asso = new MockTagHintAssociationTable();
        io = new AsyncStubIO();
        sbt = new SearchByTag(dao,tdao,asso);
    }

    @Test
    public void findsCorrectTag() throws Exception {
        Tag kirja = new Tag(1,"kirja");
        HintClass hint1 = new HintClass(6,"test testbok", "testing material");
        dao.insert(hint1);
        asso.associate(kirja, hint1);
        tdao.insertOrGet(kirja);
        io.pushString("kirja");
        sbt.run(io);
        assertTrue(io.getOutput().contains("Tagi: "+kirja.toString()));
    }
}
