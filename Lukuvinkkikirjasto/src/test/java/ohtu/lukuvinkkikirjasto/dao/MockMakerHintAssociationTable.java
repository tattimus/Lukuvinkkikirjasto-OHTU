

package ohtu.lukuvinkkikirjasto.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.maker.Maker;

/**
 *
 * @author kape
 */
public class MockMakerHintAssociationTable implements MakerHintAssociationTable{
    
    private ArrayList<Pair> makerToHint=new ArrayList<>();
    private ArrayList<Pair> hintToMaker=new ArrayList<>();
    private Map<Integer,Maker> makers=new HashMap<>();
    private Map<Integer, HintClass> hints=new HashMap<>();


    @Override
    public void associate(Maker maker, HintClass hint) throws Exception {
        Pair p=new Pair(maker.getID(), hint.getID());
        makerToHint.add(p);
        Pair o=new Pair(hint.getID(), maker.getID());
        hintToMaker.add(o);
        makers.put(maker.getID(), maker);
        hints.put(hint.getID(), hint);
        
    }

    @Override
    public List<Maker> findAForB(HintClass object) throws Exception {
        List<Maker> palautettava=new ArrayList<>();
        for(Pair p: hintToMaker) {
            if(p.getKey().equals(object.getID())) palautettava.add(makers.get(p.getValue()));
        }
        return palautettava;
    }

    @Override
    public List<HintClass> findBForA(Maker object) throws Exception {
        List<HintClass> palautettava=new ArrayList<>();
        for(Pair p: makerToHint) {
            if(p.getKey().equals(object.getID())) palautettava.add(hints.get(p.getValue()));
        }
        return palautettava;
    }

    @Override
    public void unassociate(Maker maker, HintClass hint) throws Exception {
        makerToHint.removeIf(p -> {
            return p.getKey().equals(maker.getID()) && p.getValue().equals(hint.getID());
        }); 
        hintToMaker.removeIf(p -> {
            return p.getKey().equals(hint.getID()) && p.getValue().equals(maker.getID());
        });
    }
    

}