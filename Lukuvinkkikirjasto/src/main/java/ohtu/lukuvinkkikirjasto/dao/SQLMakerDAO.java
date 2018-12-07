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
import ohtu.lukuvinkkikirjasto.maker.Maker;
/**
 *
 * @author lindradi
 */

public class SQLMakerDAO implements MakerDAO {
    private Database database;
    
    public SQLMakerDAO(Database database) throws Exception {
        this.database = database;
        
        try (Connection connection = this.database.getConnection()) {
            connection
                .prepareStatement("CREATE TABLE IF NOT EXISTS Tekija (id INTEGER NOT NULL PRIMARY KEY, tekija TEXT NOT NULL UNIQUE)")
                .execute();
                
        }
    }
    
    @Override
    public Maker insertOrGet(Maker object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, tekija FROM Tekija WHERE tekija = ?");
            stmt.setString(1, object.getMaker());            
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Maker(rs.getInt("id"), rs.getString("tekija"));
            } else {
                int id = insert(object);
                object.setID(id);
                
                return object;
            }
        }
    }
 
    @Override
    public int insert(Maker object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Tekija (tekija) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, object.getMaker());            
            
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
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Tekija WHERE id = ?");
            stmt.setInt(1, id);
            
            stmt.execute();
        }
    }

    @Override
    public Maker findOne(int id) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, tekija FROM Tekija WHERE id = ?");
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Maker(rs.getInt("id"), rs.getString("tekija"));
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Maker> findAll() throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, tekija FROM Tekija");

            ResultSet rs = stmt.executeQuery();
            
            List<Maker> results = new ArrayList<>();
            while (rs.next()) {
                results.add(new Maker(rs.getInt("id"), rs.getString("tekija")));
            }
            
            return results;
        }
    }

    @Override
    public void update(Maker object) throws Exception {
        //Tagin päivitys ei ole tarpeellinen operaatio
        throw new UnsupportedOperationException("Makers cannot be updated");
    }
}