/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.util.List;
import ohtu.lukuvinkkikirjasto.hint.HintClass;

/**
 *
 * @author jaakko
 */
public interface HintDAO extends DAO<HintClass> {

    void delete(int id) throws Exception;

    List<HintClass> findAll() throws Exception;

    HintClass findOne(int id) throws Exception;

    int insert(HintClass object) throws Exception;
    
    void update(HintClass object) throws Exception;
}
