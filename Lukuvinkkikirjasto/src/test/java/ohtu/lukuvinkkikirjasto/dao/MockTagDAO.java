/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import ohtu.lukuvinkkikirjasto.tag.Tag;

/**
 *
 * @author kape
 */
public class MockTagDAO implements TagDAO {

    private Map<Integer, String> tags = new HashMap<>();
    private Map<String, Integer> tagsInverse = new HashMap<>();
    private int idCounter = 0;

    @Override
    public void delete(int id) throws Exception {
        String value=tags.get(id);
        tags.remove(id);
        tagsInverse.remove(value);
    }

    @Override
    public List<Tag> findAll() throws Exception {
        List<Tag> palautettava=new ArrayList<>();
        Iterator it=tags.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry t=(Map.Entry)it.next();
            Tag tag=new Tag(t.getKey().hashCode(), t.getValue().toString());
            palautettava.add(tag);
        }
        it.remove();
        return palautettava;
    
    }

    @Override
    public Tag findOne(int id) throws Exception {
        Tag t = new Tag(id, tags.get(id));
        return t;
    }

    @Override
    public int insert(Tag object) throws Exception {
        int id = idCounter++;
        tags.put(id, object.getTag());
        tagsInverse.put(object.getTag(), id);
        return id;
    }

    @Override
    public Tag insertOrGet(Tag object) throws Exception {
        String value = object.getTag();
        Tag palautettava;
        if (tagsInverse.keySet().contains(object.getTag())) {
            palautettava = new Tag(tagsInverse.get(object.getTag()), object.getTag());

        } else {
            int id = idCounter++;
            tags.put(id, object.getTag());
            tagsInverse.put(object.getTag(), id);
            palautettava=new Tag(id, object.getTag());
        }
 
        return palautettava;
    }

}
