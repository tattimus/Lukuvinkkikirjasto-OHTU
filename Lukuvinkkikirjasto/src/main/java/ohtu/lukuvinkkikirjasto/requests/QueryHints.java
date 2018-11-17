
package ohtu.lukuvinkkikirjasto.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
/**
 *
 * @author lindradi
 */
public class QueryHints implements QueryRequest {
    
    private HintDAO hdao;
    
    public QueryHints(HintDAO hdao){
        this.hdao = hdao;
    }
    public ArrayList<Hint> getAllRequests() {
        ArrayList<Hint> hints = new ArrayList<>();
        try {
            for (HintClass hc : hdao.findAll()){
                Hint h = hc;
                hints.add(h);
            }
        } catch (Exception ex) {
        }
        return hints;
    }  
}
