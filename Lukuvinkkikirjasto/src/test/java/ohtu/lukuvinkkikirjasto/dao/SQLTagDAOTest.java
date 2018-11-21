/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.sql.SQLException;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.tag.Tag;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

/**
 *
 * @author jaakko
 */
public class SQLTagDAOTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    private SQLTagDAO dao;
    
    @Before
    public void setup() throws Exception {
        dao = new SQLTagDAO(new SQLiteDatabase(folder.newFile().getAbsolutePath()));
    }
    
    @Test
    public void testInsert() throws Exception {
        Tag tag = new Tag(null, "video");
        dao.insert(tag);
        
        assertFalse(dao.findAll().isEmpty());
    }
    
    @Test(expected = SQLException.class)
    public void testInsertWithNullValues() throws Exception {
        Tag tag = new Tag(null, null);
        dao.insert(tag);
    }
    
    @Test
    public void testDelete() throws Exception {
        Tag tag = new Tag(null, "video");
        int id = dao.insert(tag);
        
        assertFalse(dao.findAll().isEmpty());
        
        dao.delete(id);
        
        assertTrue(dao.findAll().isEmpty());
    }
    
    @Test
    public void testFindOne() throws Exception {
        Tag tag = new Tag(null, "video");
        int id = dao.insert(tag);
        
        assertEquals("video", dao.findOne(id).getTag());
    }
    
    @Test(expected = SQLException.class)
    public void testInsertSameTagTwiceThrowsError() throws Exception {
        Tag tag1 = new Tag(null, "video");
        Tag tag2 = new Tag(null, "video");
        
        dao.insert(tag1);
        dao.insert(tag2);
    }
    
    @Test
    public void testInsertOrGetReturnsPreviouslyInserted() throws Exception {
        Tag tag1 = new Tag(null, "video");
        Tag tag2 = new Tag(null, "video");
        
        int id = dao.insertOrGet(tag1).getID();
        
        assertEquals(id, (int)dao.insertOrGet(tag2).getID());
    }
}
