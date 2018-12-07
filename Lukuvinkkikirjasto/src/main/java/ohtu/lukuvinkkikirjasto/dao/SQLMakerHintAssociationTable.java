

package ohtu.lukuvinkkikirjasto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import ohtu.lukuvinkkikirjasto.database.Database;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.maker.Maker;


public class SQLMakerHintAssociationTable implements MakerHintAssociationTable {
    private Database database;
    
    public SQLMakerHintAssociationTable(Database database) throws Exception {
        this.database = database;
        
        try (Connection connection = this.database.getConnection()) {
            connection
                .prepareStatement("CREATE TABLE IF NOT EXISTS TekijaVinkki (tekija_id INTEGER REFERENCES Tekija (id), vinkki_id INTEGER REFERENCES Vinkki (id), PRIMARY KEY (tekija_id, vinkki_id))")
                .execute();
                
        }
    }

    @Override
    public List<Maker> findAForB(HintClass object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, tekija FROM Tekija, TekijaVinkki WHERE TekijaVinkki.vinkki_id = ? AND TekijaVinkki.tekija_id = Tekija.id");
            stmt.setInt(1, object.getID());

            ResultSet rs = stmt.executeQuery();
            
            List<Maker> results = new ArrayList<>();
            while (rs.next()) {
                results.add(new Maker(rs.getInt("id"), rs.getString("tekija")));
            }
            
            return results;
        }
    }
    
    @Override
    public List<HintClass> findBForA(Maker object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, otsikko, kommentti, url, luettu_aikaleima FROM Vinkki, TekijaVinkki WHERE TekijaVinkki.tekija_id = ? AND TekijaVinkki.vinkki_id = Vinkki.id");
            stmt.setInt(1, object.getID());

            ResultSet rs = stmt.executeQuery();
            
            List<HintClass> results = new ArrayList<>();
            while (rs.next()) {
                results.add(new HintClass(rs.getInt("id"), rs.getString("otsikko"), rs.getString("kommentti"), rs.getString("url"), rs.getTimestamp("luettu_aikaleima")));
            }
            
            return results;
        }
    }

    @Override
    public void associate(Maker maker, HintClass hint) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO TekijaVinkki (tekija_id, vinkki_id) VALUES (?, ?)");
            stmt.setInt(1, maker.getID());
            stmt.setInt(2, hint.getID());
            
            stmt.execute();
        }
    }

    @Override
    public void unassociate(Maker maker, HintClass hint) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM TekijaVinkki WHERE tekija_id = ? AND vinkki_id = ?");
            stmt.setInt(1, maker.getID());
            stmt.setInt(2, hint.getID());
            
            stmt.execute();
        }
    }
}