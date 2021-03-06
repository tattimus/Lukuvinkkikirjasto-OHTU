/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

/**
 *
 * @author jaakko
 */
public class SQLHintDAOTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    private SQLHintDAO vinkkiDao;
    
    @Before
    public void setup() throws Exception {
        vinkkiDao = new SQLHintDAO(new SQLiteDatabase(folder.newFile().getAbsolutePath()));
    }
    
    @Test
    public void testInsert() throws Exception {
        HintClass vinkki = new HintClass(null, "testi", "testi", "www.example.com");
        vinkkiDao.insert(vinkki);
        
        assertFalse(vinkkiDao.findAll().isEmpty());
    }
    
    @Test(expected = SQLException.class)
    public void testInsertWithNullValues() throws Exception {
        HintClass vinkki = new HintClass(null, null, null, null);
        vinkkiDao.insert(vinkki);
    }
    
    @Test
    public void testDelete() throws Exception {
        HintClass vinkki = new HintClass(null, "testi", "testi", "www.example.com");
        int id = vinkkiDao.insert(vinkki);
        
        assertFalse(vinkkiDao.findAll().isEmpty());
        
        vinkkiDao.delete(id);
        
        assertTrue(vinkkiDao.findAll().isEmpty());
    }
    
    @Test
    public void testFindOne() throws Exception {
        HintClass vinkki = new HintClass(null, "testi", "testi", "www.example.com");
        int id = vinkkiDao.insert(vinkki);
        
        assertEquals("testi", vinkkiDao.findOne(id).getTitle());
    }
    
    @Test
    public void testUpdate() throws Exception {
        HintClass vinkki = new HintClass(null, "testi", "testi", "www.example.com");
        vinkki.setID(vinkkiDao.insert(vinkki));
        
        assertEquals("testi", vinkkiDao.findOne(vinkki.getID()).getTitle());
        
        vinkki.setTitle("otsikko");
        vinkkiDao.update(vinkki);
        
        assertEquals("otsikko", vinkkiDao.findOne(vinkki.getID()).getTitle());
    }
    @Test
    public void testSearch() throws Exception {
        HintClass vinkki=new HintClass(null, "Computer", "kurssikirja", "https://www.adlibris.com/fi/e-kirja/computer-organization-and-architecture-global-edition-9781292096865");
        vinkki.setID(vinkkiDao.insert(vinkki));
        assertTrue(vinkkiDao.search("otsikko", "computer").stream().anyMatch(hint-> hint.getID().equals(vinkki.getID())));
        assertTrue(vinkkiDao.search("kommentti", "kurssikirja").stream().anyMatch(hint-> hint.getID().equals(vinkki.getID())));
        assertTrue(vinkkiDao.search("kommentti", "kurssi").stream().anyMatch(hint-> hint.getID().equals(vinkki.getID())));
        assertTrue(vinkkiDao.search("otsikko", "otsake").isEmpty());
        
    }

}
