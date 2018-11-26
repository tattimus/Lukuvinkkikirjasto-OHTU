

package ohtu.lukuvinkkikirjasto.requests;

import ohtu.lukuvinkkikirjasto.IO.AsyncStubIO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.actions.QueryHints;
import ohtu.lukuvinkkikirjasto.dao.MockHintDAO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lindradi
 */
public class QueryHintsTest {
    private QueryHints qh;
    private HintDAO dao;
    private AsyncStubIO io;
    
    @Before
    public void setUp() throws Exception {
        dao = new MockHintDAO();
        qh = new QueryHints(dao);
        io = new AsyncStubIO();
    }

    @Test
    public void returnsCorrectTitle() throws Exception {
        HintClass hint = new HintClass(null, "otsikko", "kommentti", "www.example.com");
        dao.insert(hint);
        
        qh.run(io);
        assertTrue(io.getOutput().contains(hint.toString()));
    }
}
