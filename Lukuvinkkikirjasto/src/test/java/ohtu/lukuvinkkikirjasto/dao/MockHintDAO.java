/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;

/**
 *
 * @author jaakko
 */
public class MockHintDAO implements HintDAO {
    private HashMap<Integer, HintClass> map = new HashMap<>();
    private int idCounter = 0;

    @Override
    public void delete(int id) throws Exception {
        map.remove(id);
    }

    @Override
    public List<HintClass> findAll() throws Exception {
        return new ArrayList<>(map.values());
    }

    @Override
    public HintClass findOne(int id) throws Exception {
        return map.get(id);
    }

    @Override
    public int insert(HintClass object) throws Exception {
        int id = idCounter++;
        map.put(id, new HintClass(id, object.getTitle(), object.getComment(), object.getUrl()));
        return id;
    }

@Override
    public void update(HintClass object) throws Exception {
        map.put(object.getID(), object);

    }

}
