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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ohtu.lukuvinkkikirjasto.database.Database;
import ohtu.lukuvinkkikirjasto.hint.HintClass;

/**
 *
 * @author jaakko
 */
public class SQLHintDAO implements HintDAO {

    private Database database;

    public SQLHintDAO(Database database) throws Exception {
        this.database = database;

        try (Connection connection = this.database.getConnection()) {
            connection
                    .prepareStatement("CREATE TABLE IF NOT EXISTS Vinkki (id INTEGER NOT NULL PRIMARY KEY, otsikko TEXT NOT NULL, kommentti TEXT NOT NULL, url TEXT, luettu_aikaleima TEXT )")
                    .execute();

        }
    }

    @Override
    public int insert(HintClass object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Vinkki (otsikko, kommentti, url) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, object.getTitle());
            stmt.setString(2, object.getComment());
            stmt.setString(3, object.getUrl());

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
            PreparedStatement stmt = connection.prepareStatement("SELECT id, otsikko, kommentti, url, luettu_aikaleima FROM Vinkki WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Date sdf;
                if (rs.getString("luettu_aikaleima") == null) {
                    sdf = null;
                } else {
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("luettu_aikaleima"));
                }
                return new HintClass(rs.getInt("id"), rs.getString("otsikko"), rs.getString("kommentti"), rs.getString("url"), sdf);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<HintClass> findAll() throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, otsikko, kommentti, url, luettu_aikaleima  FROM Vinkki");

            ResultSet rs = stmt.executeQuery();

            List<HintClass> results = new ArrayList<>();
            while (rs.next()) {
                Date sdf;
                if (rs.getString("luettu_aikaleima") == null) {
                    sdf = null;
                } else {
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("luettu_aikaleima"));
                }
                results.add(new HintClass(rs.getInt("id"), rs.getString("otsikko"), rs.getString("kommentti"), rs.getString("url"), sdf));

            }

            return results;
        }

    }

    @Override
    public void setTimestamp(int id) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Vinkki SET luettu_aikaleima = CURRENT_TIMESTAMP WHERE id = ?");

            stmt.setInt(1, id);

            stmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    @Override
    public void update(HintClass object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Vinkki SET otsikko = ?, kommentti = ?, url = ? WHERE id = ?");
            stmt.setString(1, object.getTitle());
            stmt.setString(2, object.getComment());
            stmt.setString(3, object.getUrl());
            stmt.setInt(4, object.getID());

            stmt.execute();
        }

    }
}
