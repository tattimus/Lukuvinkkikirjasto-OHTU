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
import java.time.Instant;
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
                    .prepareStatement("CREATE TABLE IF NOT EXISTS Vinkki (id INTEGER NOT NULL PRIMARY KEY, otsikko TEXT NOT NULL, kommentti TEXT NOT NULL, url TEXT, luettu_aikaleima INTEGER )")
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
            PreparedStatement stmt = connection.prepareStatement("SELECT id, otsikko, kommentti, url, datetime(luettu_aikaleima, 'unixepoch', 'localtime') FROM Vinkki WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            Date ts = parseTimestamp(rs.getString(5));

            if (rs.next()) {
                return new HintClass(rs.getInt("id"), rs.getString("otsikko"), rs.getString("kommentti"), rs.getString("url"), ts);
            } else {
                return null;
            }
        }
    }

    @Override
    public List<HintClass> findAll() throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, otsikko, kommentti, url, datetime(luettu_aikaleima, 'unixepoch', 'localtime')  FROM Vinkki");

            ResultSet rs = stmt.executeQuery();

            List<HintClass> results = new ArrayList<>();
            while (rs.next()) {
                Date ts = parseTimestamp(rs.getString(5));
                results.add(new HintClass(rs.getInt("id"), rs.getString("otsikko"), rs.getString("kommentti"), rs.getString("url"), ts));

            }

            return results;
        }

    }

    @Override
    public void update(HintClass object) throws Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Vinkki SET otsikko = ?, kommentti = ?, url = ?, luettu_aikaleima = ? WHERE id = ?");
            stmt.setString(1, object.getTitle());
            stmt.setString(2, object.getComment());
            stmt.setString(3, object.getUrl());
            stmt.setInt(4, convertDate(object.getTimestamp()));
            stmt.setInt(5, object.getID());

            stmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private int convertDate(Date date) {
        int ret = 0;
        if (date != null) {
            Instant instant = date.toInstant();
            ret = (int) instant.getEpochSecond();
        }
        return ret;
    }

    private Date parseTimestamp(String s) throws Exception {
        Date sdf = null;
        if (s != null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
        }
        return sdf;
    }
}
