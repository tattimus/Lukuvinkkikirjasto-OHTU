/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.util.List;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.tag.Tag;

/**
 *
 * @author jaakko
 */
public interface TagDAO extends DAO<Tag> {
    @Override
    void delete(int id) throws Exception;

    @Override
    List<Tag> findAll() throws Exception;
    
    @Override
    Tag findOne(int id) throws Exception;

    @Override
    int insert(Tag object) throws Exception;
}
