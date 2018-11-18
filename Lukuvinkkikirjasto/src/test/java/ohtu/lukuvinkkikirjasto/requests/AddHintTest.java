/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.requests;

import java.util.ArrayList;
import java.util.List;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.SQLHintDAO;
import ohtu.lukuvinkkikirjasto.database.Database;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.hint.Hint;
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
public class AddHintTest {

    AddHint newHint;

    HintDAO daoStub = new HintDAO() {
        ArrayList<HintClass> hints = new ArrayList<>();
        int id = 1;


        public int insert(HintClass object) {
            hints.add(object);
            return this.id;
        }

        public List<HintClass> findAll() {

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
    };

    public AddHintTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {

        this.newHint = new AddHint(daoStub);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //


    @Test
    public void HintCanBeAdded() throws Exception {
        String s = newHint.createHint("myFirstHint", "Comment for MyFirstHint");
        List<HintClass> hints = new ArrayList<>();
        hints = daoStub.findAll();
        assertEquals(1, hints.size());

    }


    @Test
    public void StringReturnPrintsCorrect() {
        String s = newHint.createHint("mySecondHint", "Comment for MySecondHint");
        assertEquals("Hint mySecondHint with commment Comment for MySecondHint is created with id 1", s);

    }
            @Test
    public void SeveralHintsCanBeAdded() throws Exception {
        String s = newHint.createHint("myThirdHint", "Comment for MyThirdHint");
        s = newHint.createHint("myFouthHint", "Comment for MyFourthHint");
        List<HintClass> hints = new ArrayList<>();
        hints = daoStub.findAll();
        assertEquals(2, hints.size());

    }

}
