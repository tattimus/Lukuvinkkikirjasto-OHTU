/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;
import java.util.List;
import ohtu.lukuvinkkikirjasto.maker.Maker;
/**
 *
 * @author lindradi
 */

public interface MakerDAO extends DAO<Maker> {
    @Override
    void delete(int id) throws Exception;

    @Override
    List<Maker> findAll() throws Exception;
    
    @Override
    Maker findOne(int id) throws Exception;

    @Override
    int insert(Maker object) throws Exception;

    @Override
    void update(Maker object) throws Exception;
    
    Maker insertOrGet(Maker object) throws Exception;
}