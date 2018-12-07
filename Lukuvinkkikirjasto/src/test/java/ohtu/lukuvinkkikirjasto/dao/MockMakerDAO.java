

package ohtu.lukuvinkkikirjasto.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import ohtu.lukuvinkkikirjasto.maker.Maker;


public class MockMakerDAO implements MakerDAO {

    private Map<Integer, String> makers = new HashMap<>();
    private Map<String, Integer> makersInverse = new HashMap<>();
    private int idCounter = 0;

    @Override
    public void delete(int id) throws Exception {
        String value=makers.get(id);
        makers.remove(id);
        makersInverse.remove(value);
    }

    @Override
    public List<Maker> findAll() throws Exception {
        List<Maker> palautettava=new ArrayList<>();
        Iterator it=makers.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry t=(Map.Entry)it.next();
            Maker maker=new Maker(t.getKey().hashCode(), t.getValue().toString());
            palautettava.add(maker);
        }
        it.remove();
        return palautettava;
    
    }

    @Override
    public Maker findOne(int id) throws Exception {
        Maker m = new Maker(id, makers.get(id));
        return m;
    }

    @Override
    public int insert(Maker object) throws Exception {
        int id = idCounter++;
        makers.put(id, object.getMaker());
        makersInverse.put(object.getMaker(), id);
        return id;
    }

    @Override
    public Maker insertOrGet(Maker object) throws Exception {
        String value = object.getMaker();
        Maker palautettava;
        if (makersInverse.keySet().contains(object.getMaker())) {
            palautettava = new Maker(makersInverse.get(object.getMaker()), object.getMaker());

        } else {
            int id = idCounter++;
            makers.put(id, object.getMaker());
            makersInverse.put(object.getMaker(), id);
            palautettava=new Maker(id, object.getMaker());
        }
 
        return palautettava;
    }

    @Override
    public void update(Maker object) throws Exception {
        throw new UnsupportedOperationException("Makers cannot be updated");
    }

}