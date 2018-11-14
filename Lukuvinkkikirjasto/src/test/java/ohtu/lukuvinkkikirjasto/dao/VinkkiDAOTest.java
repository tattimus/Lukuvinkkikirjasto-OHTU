/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.sql.SQLException;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.vinkki.VinkkiClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

/**
 *
 * @author jaakko
 */
public class VinkkiDAOTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    private VinkkiDAO vinkkiDao;
    
    @Before
    public void setup() throws Exception {
        vinkkiDao = new VinkkiDAO(new SQLiteDatabase(folder.newFile().getAbsolutePath()));
    }
    
    @Test
    public void testInsert() throws Exception {
        VinkkiClass vinkki = new VinkkiClass(null, "testi", "testi");
        vinkkiDao.insert(vinkki);
        
        assertFalse(vinkkiDao.findAll().isEmpty());
    }
    
    @Test(expected = SQLException.class)
    public void testInsertWithNullValues() throws Exception {
        VinkkiClass vinkki = new VinkkiClass(null, null, null);
        vinkkiDao.insert(vinkki);
    }
    
    @Test
    public void testDelete() throws Exception {
        VinkkiClass vinkki = new VinkkiClass(null, "testi", "testi");
        int id = vinkkiDao.insert(vinkki);
        
        assertFalse(vinkkiDao.findAll().isEmpty());
        
        vinkkiDao.delete(id);
        
        assertTrue(vinkkiDao.findAll().isEmpty());
    }
    
    @Test
    public void testFindOne() throws Exception {
        VinkkiClass vinkki = new VinkkiClass(null, "testi", "testi");
        int id = vinkkiDao.insert(vinkki);
        
        assertEquals("testi", vinkkiDao.findOne(id).getOtsikko());
    }
}
