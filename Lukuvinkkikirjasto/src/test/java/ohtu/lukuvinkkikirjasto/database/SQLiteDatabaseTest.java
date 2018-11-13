/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

/**
 *
 * @author jaakko
 */
public class SQLiteDatabaseTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private SQLiteDatabase sqliteDatabase;
    
    @Before
    public void setup() throws IOException {
        sqliteDatabase = new SQLiteDatabase(folder.newFile().getAbsolutePath());
    }
    
    @Test
    public void testCreateDatabase() throws SQLException {
        try (Connection conn = sqliteDatabase.getConnection()) {
            conn.prepareStatement("CREATE TABLE Testi (id INT, testi VARCHAR(20), PRIMARY KEY(id))").execute();
            
            ResultSet resultSet = conn.prepareStatement("SELECT name FROM sqlite_master WHERE type='table'").executeQuery();
            
            assertTrue(resultSet.next());
        }
    }
}
