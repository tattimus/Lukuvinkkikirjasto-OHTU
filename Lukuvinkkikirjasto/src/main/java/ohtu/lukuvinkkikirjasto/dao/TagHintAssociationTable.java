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
public interface TagHintAssociationTable extends AssociationTable<Tag, HintClass> {

    void associate(Tag tag, HintClass hint) throws Exception;

    List<Tag> findAForB(HintClass object) throws Exception;

    List<HintClass> findBForA(Tag object) throws Exception;

    void unassociate(Tag tag, HintClass hint) throws Exception;
    
}
