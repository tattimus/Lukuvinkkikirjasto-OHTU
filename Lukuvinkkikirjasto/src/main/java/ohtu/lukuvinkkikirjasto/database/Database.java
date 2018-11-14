/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.database;

import java.sql.Connection;

/**
 *
 * @author jaakko
 */
public interface Database {
    Connection getConnection() throws Exception;
}
