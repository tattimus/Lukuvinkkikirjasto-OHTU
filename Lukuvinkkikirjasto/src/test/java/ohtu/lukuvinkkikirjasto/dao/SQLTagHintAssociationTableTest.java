/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.sql.SQLException;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
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
public class SQLTagHintAssociationTableTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    private SQLHintDAO hintDao;
    private SQLTagDAO tagDao;
    private SQLTagHintAssociationTable association;
    
    @Before
    public void setup() throws Exception {
        String db = folder.newFile().getAbsolutePath();
        
        hintDao = new SQLHintDAO(new SQLiteDatabase(db));
        tagDao = new SQLTagDAO(new SQLiteDatabase(db));
        association = new SQLTagHintAssociationTable(new SQLiteDatabase(db));
    }
    
    @Test
    public void testAssociation() throws Exception {
        HintClass hint = new HintClass(null, "testi", "testi", "www.example.com");
        hint.setID(hintDao.insert(hint));
        
        Tag tag = new Tag(null, "testi");
        tag.setID(tagDao.insert(tag));
        
        association.associate(tag, hint);
        
        assertFalse(association.findAForB(hint).isEmpty());
        assertFalse(association.findBForA(tag).isEmpty());
    }
    
    @Test
    public void testUnassociation() throws Exception {
        HintClass hint = new HintClass(null, "testi", "testi", "www.example.com");
        hint.setID(hintDao.insert(hint));
        
        Tag tag = new Tag(null, "testi");
        tag.setID(tagDao.insert(tag));
        
        association.associate(tag, hint);
        
        assertFalse(association.findAForB(hint).isEmpty());
        assertFalse(association.findBForA(tag).isEmpty());
        
        association.unassociate(tag, hint);
        
        assertTrue(association.findAForB(hint).isEmpty());
        assertTrue(association.findBForA(tag).isEmpty());
    }
}
