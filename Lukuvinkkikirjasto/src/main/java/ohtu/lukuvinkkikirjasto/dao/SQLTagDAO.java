/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ohtu.lukuvinkkikirjasto.database.Database;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.tag.Tag;

/**
 *
 * @author jaakko
 */
public class SQLTagDAO implements TagDAO {
    private Database database;
    
    public SQLTagDAO(Database database) throws Exception {
        this.database = database;
        
        try (Connection connection = this.database.getConnection()) {
            connection
                .prepareStatement("CREATE TABLE IF NOT EXISTS Tagi (id INTEGER NOT NULL PRIMARY KEY, tagi TEXT NOT NULL UNIQUE)")
                .execute();
                
        }
    }
    
    @Override
    public Tag insertOrGet(Tag object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, tagi FROM Tagi WHERE tagi = ?");
            stmt.setString(1, object.getTag());            
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Tag(rs.getInt("id"), rs.getString("tagi"));
            } else {
                int id = insert(object);
                object.setID(id);
                
                return object;
            }
        }
    }
 
    @Override
    public int insert(Tag object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Tagi (tagi) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, object.getTag());            
            
            stmt.execute();
            
            if (stmt.getGeneratedKeys().next()) {
                return stmt.getGeneratedKeys().getInt(1);
            } else {
                //Nothing was inserted
                return -1;
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Tagi WHERE id = ?");
            stmt.setInt(1, id);
            
            stmt.execute();
        }
    }

    @Override
    public Tag findOne(int id) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, tagi FROM Tagi WHERE id = ?");
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Tag(rs.getInt("id"), rs.getString("tagi"));
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Tag> findAll() throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, tagi FROM Tagi");

            ResultSet rs = stmt.executeQuery();
            
            List<Tag> results = new ArrayList<>();
            while (rs.next()) {
                results.add(new Tag(rs.getInt("id"), rs.getString("tagi")));
            }
            
            return results;
        }
    }

    @Override
    public void update(Tag object) throws Exception {
        //Tagin päivitys ei ole tarpeellinen operaatio
        throw new UnsupportedOperationException("Tags cannot be updated");
    }
}
