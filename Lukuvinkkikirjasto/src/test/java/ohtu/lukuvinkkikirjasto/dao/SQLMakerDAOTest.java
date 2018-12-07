

package ohtu.lukuvinkkikirjasto.dao;

import java.sql.SQLException;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.maker.Maker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


import static org.junit.Assert.*;

public class SQLMakerDAOTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    private SQLMakerDAO dao;
    
    @Before
    public void setup() throws Exception {
        dao = new SQLMakerDAO(new SQLiteDatabase(folder.newFile().getAbsolutePath()));
    }
    
    @Test
    public void testInsert() throws Exception {
        Maker tekija = new Maker(null, "Prof. Varavirta");
        dao.insert(tekija);
        
        assertFalse(dao.findAll().isEmpty());
    }
    
    @Test(expected = SQLException.class)
    public void testInsertWithNullValues() throws Exception {
        Maker tekija = new Maker(null, null);
        dao.insert(tekija);
    }
    
    @Test
    public void testDelete() throws Exception {
        Maker tekija = new Maker(null, "Prof. Varavirta");
        int id = dao.insert(tekija);
        
        assertFalse(dao.findAll().isEmpty());
        
        dao.delete(id);
        
        assertTrue(dao.findAll().isEmpty());
    }
    
    @Test
    public void testFindOne() throws Exception {
        Maker tekija = new Maker(null, "Prof. Varavirta");
        int id = dao.insert(tekija);
        
        assertEquals("Prof. Varavirta", dao.findOne(id).getMaker());
    }
    
    @Test(expected = SQLException.class)
    public void testInsertSameTagTwiceThrowsError() throws Exception {
        Maker tekija1 = new Maker(null, "Prof. Varavirta");
        Maker tekija2 = new Maker(null, "Prof. Varavirta");
        
        dao.insert(tekija1);
        dao.insert(tekija2);
    }
    
    @Test
    public void testInsertOrGetReturnsPreviouslyInserted() throws Exception {
        Maker tekija1 = new Maker(null, "Prof. Varavirta");
        Maker tekija2 = new Maker(null, "Prof. Varavirta");
        
        int id = dao.insertOrGet(tekija1).getID();
        
        assertEquals(id, (int)dao.insertOrGet(tekija2).getID());
    }
}
