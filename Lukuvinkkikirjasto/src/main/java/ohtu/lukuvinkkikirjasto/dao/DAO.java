/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.util.List;

/**
 *
 * @author jaakko
 */
public interface DAO<T extends ObjectWithID> {
    void insert(T object) throws Exception;
    void delete(int id) throws Exception;
    T findOne(int id) throws Exception;
    List<T> findAll() throws Exception;
}
