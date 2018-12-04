
package ohtu.lukuvinkkikirjasto.dao;

import java.sql.SQLException;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.maker.Maker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;


public class SQLMakerHintAssociationTableTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    private SQLHintDAO hintDao;
    private SQLMakerDAO makerDao;
    private SQLMakerHintAssociationTable association;
    
    @Before
    public void setup() throws Exception {
        String db = folder.newFile().getAbsolutePath();
        
        hintDao = new SQLHintDAO(new SQLiteDatabase(db));
        makerDao = new SQLMakerDAO(new SQLiteDatabase(db));
        association = new SQLMakerHintAssociationTable(new SQLiteDatabase(db));
    }
    
    @Test
    public void testAssociation() throws Exception {
        HintClass hint = new HintClass(null, "testi", "testi", "www.example.com");
        hint.setID(hintDao.insert(hint));
        
        Maker maker = new Maker(null, "testi");
        maker.setID(makerDao.insert(maker));
        
        association.associate(maker, hint);
        
        assertFalse(association.findAForB(hint).isEmpty());
        assertFalse(association.findBForA(maker).isEmpty());
    }
    
    @Test
    public void testUnassociation() throws Exception {
        HintClass hint = new HintClass(null, "testi", "testi", "www.example.com");
        hint.setID(hintDao.insert(hint));
        
        Maker maker = new Maker(null, "testi");
        maker.setID(makerDao.insert(maker));
        
        association.associate(maker, hint);
        
        assertFalse(association.findAForB(hint).isEmpty());
        assertFalse(association.findBForA(maker).isEmpty());
        
        association.unassociate(maker, hint);
        
        assertTrue(association.findAForB(hint).isEmpty());
        assertTrue(association.findBForA(maker).isEmpty());
    }
}