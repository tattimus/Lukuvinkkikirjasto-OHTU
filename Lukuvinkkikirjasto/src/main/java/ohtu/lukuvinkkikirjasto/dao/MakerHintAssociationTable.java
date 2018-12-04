

package ohtu.lukuvinkkikirjasto.dao;

import java.util.List;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.maker.Maker;

/**
 *
 * @author lindradi
 */
public interface MakerHintAssociationTable extends AssociationTable<Maker, HintClass> {

    void associate(Maker maker, HintClass hint) throws Exception;

    List<Maker> findAForB(HintClass object) throws Exception;

    List<HintClass> findBForA(Maker object) throws Exception;

    void unassociate(Maker maker, HintClass hint) throws Exception;
    
}