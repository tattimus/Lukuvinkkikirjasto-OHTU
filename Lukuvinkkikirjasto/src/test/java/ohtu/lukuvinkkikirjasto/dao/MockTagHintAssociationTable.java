/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.tag.Tag;

/**
 *
 * @author kape
 */
public class MockTagHintAssociationTable implements TagHintAssociationTable{
    
    private ArrayList<Pair> tagToHint=new ArrayList<>();
    private ArrayList<Pair> hintToTag=new ArrayList<>();
    private Map<Integer,Tag> tags=new HashMap<>();
    private Map<Integer, HintClass> hints=new HashMap<>();


    @Override
    public void associate(Tag tag, HintClass hint) throws Exception {
        Pair p=new Pair(tag.getID(), hint.getID());
        tagToHint.add(p);
        Pair o=new Pair(hint.getID(), tag.getID());
        hintToTag.add(o);
        tags.put(tag.getID(), tag);
        hints.put(hint.getID(), hint);
        
    }

    @Override
    public List<Tag> findAForB(HintClass object) throws Exception {
        List<Tag> palautettava=new ArrayList<>();
        for(Pair p: hintToTag) {
            if(p.getKey().equals(object.getID())) palautettava.add(tags.get(p.getValue()));
        }
        return palautettava;
    }

    @Override
    public List<HintClass> findBForA(Tag object) throws Exception {
        List<HintClass> palautettava=new ArrayList<>();
        for(Pair p: tagToHint) {
            if(p.getKey().equals(object.getID())) palautettava.add(hints.get(p.getValue()));
        }
        return palautettava;
    }

    @Override
    public void unassociate(Tag tag, HintClass hint) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
