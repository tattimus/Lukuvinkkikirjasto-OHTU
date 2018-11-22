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
public interface AssociationTable<A extends ObjectWithID, B extends ObjectWithID> {
    List<A> findAForB(B object) throws Exception;
    List<B> findBForA(A object) throws Exception;
    void associate(A a, B b) throws Exception;
    void unassociate(A a, B b) throws Exception;
}
