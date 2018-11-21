/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import ohtu.lukuvinkkikirjasto.database.Database;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.tag.Tag;

/**
 *
 * @author jaakko
 */
public class SQLTagHintAssociationTable implements TagHintAssociationTable {
    private Database database;
    
    public SQLTagHintAssociationTable(Database database) throws Exception {
        this.database = database;
        
        try (Connection connection = this.database.getConnection()) {
            connection
                .prepareStatement("CREATE TABLE IF NOT EXISTS TagiVinkki (tagi_id INTEGER REFERENCES Tagi (id), vinkki_id INTEGER REFERENCES Vinkki (id), PRIMARY KEY (tagi_id, vinkki_id))")
                .execute();
                
        }
    }

    @Override
    public List<Tag> findAForB(HintClass object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, tagi FROM Tagi, TagiVinkki WHERE TagiVinkki.vinkki_id = ? AND TagiVinkki.tagi_id = Tagi.id");
            stmt.setInt(1, object.getID());

            ResultSet rs = stmt.executeQuery();
            
            List<Tag> results = new ArrayList<>();
            while (rs.next()) {
                results.add(new Tag(rs.getInt("id"), rs.getString("tagi")));
            }
            
            return results;
        }
    }
    
    @Override
    public List<HintClass> findBForA(Tag object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, otsikko, kommentti FROM Vinkki, TagiVinkki WHERE TagiVinkki.tagi_id = ? AND TagiVinkki.vinkki_id = Vinkki.id");
            stmt.setInt(1, object.getID());

            ResultSet rs = stmt.executeQuery();
            
            List<HintClass> results = new ArrayList<>();
            while (rs.next()) {
                results.add(new HintClass(rs.getInt("id"), rs.getString("otsikko"), rs.getString("kommentti")));
            }
            
            return results;
        }
    }

    @Override
    public void associate(Tag tag, HintClass hint) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO TagiVinkki (tagi_id, vinkki_id) VALUES (?, ?)");
            stmt.setInt(1, tag.getID());
            stmt.setInt(2, hint.getID());
            
            stmt.execute();
        }
    }

    @Override
    public void unassociate(Tag tag, HintClass hint) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM TagiVinkki WHERE tagi_id = ? AND vinkki_id = ?");
            stmt.setInt(1, tag.getID());
            stmt.setInt(2, hint.getID());
            
            stmt.execute();
        }
    }
}
