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

/**
 *
 * @author jaakko
 */
public class HintDAO implements DAO<HintClass> {
    private Database database;
    
    public HintDAO(Database database) throws Exception {
        this.database = database;
        
        try (Connection connection = this.database.getConnection()) {
            connection
                .prepareStatement("CREATE TABLE IF NOT EXISTS Vinkki (id INTEGER NOT NULL PRIMARY KEY, otsikko TEXT NOT NULL, kommentti TEXT NOT NULL)")
                .execute();
                
        }
    }
 
    @Override
    public int insert(HintClass object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Vinkki (otsikko, kommentti) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, object.getTitle());            
            stmt.setString(2, object.getComment());
            
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
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Vinkki WHERE id = ?");
            stmt.setInt(1, id);
            
            stmt.execute();
        }
    }

    @Override
    public HintClass findOne(int id) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, otsikko, kommentti FROM Vinkki WHERE id = ?");
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new HintClass(rs.getInt("id"), rs.getString("otsikko"), rs.getString("kommentti"));
            } else {
                return null;
            }
        }
    }

    @Override
    public List<HintClass> findAll() throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, otsikko, kommentti FROM Vinkki");

            ResultSet rs = stmt.executeQuery();
            
            List<HintClass> results = new ArrayList<>();
            while (rs.next()) {
                results.add(new HintClass(rs.getInt("id"), rs.getString("otsikko"), rs.getString("kommentti")));
            }
            
            return results;
        }
    }    
}
