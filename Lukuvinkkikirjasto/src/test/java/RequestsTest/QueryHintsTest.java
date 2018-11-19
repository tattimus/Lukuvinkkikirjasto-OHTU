

package RequestsTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.SQLHintDAO;
import ohtu.lukuvinkkikirjasto.database.Database;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.requests.QueryHints;
import ohtu.lukuvinkkikirjasto.requests.QueryRequest;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lindradi
 */
public class QueryHintsTest {
    

    HintDAO daoStub = new HintDAO() {
        public List<HintClass> findAll() {
            List<HintClass> hints = new ArrayList<>();
            hints.add(new HintClass(1,"testiKirja","testi1"));
            hints.add(new HintClass(1,"testBook","test2"));
            hints.add(new HintClass(3,"abc","123"));
                
            return hints;
        }

        @Override
        public void delete(int id) throws Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public HintClass findOne(int id) throws Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int insert(HintClass object) throws Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    
    QueryHints gh;
    String check;
    
   @Before
   public void setUp() throws Exception {
       gh = new QueryHints(daoStub);
   }
   
   @Test
   public void returnsCorrectTitle(){
       assertEquals("testBook",gh.getAllRequests().get(1).getTitle());
   }
   
   @Test
   public void returnsCorrectComment(){
       assertEquals("testi1",gh.getAllRequests().get(0).getComment());
   }
   
   
   @Test
   public void returnsrightsizedArray(){
       assertEquals(3,gh.getAllRequests().size());
   }
}
