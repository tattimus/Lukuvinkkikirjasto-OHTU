
package ohtu.lukuvinkkikirjasto.actions;

import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
/**
 *
 * @author lindradi
 */
public class QueryHints extends Action {
    
    private HintDAO hdao;
    
    public QueryHints(HintDAO hdao) {
        this.hdao = hdao;
    }

    @Override
    public int getPriority() {
        return 0;
    }
    
    @Override
    public String getHint() {
        return "Listaa vinkit";
    }

    @Override
    public void run(IO io) {
        io.printLine("Vinkit:");
        try {
            hdao.findAll().forEach(hint -> {
                io.printLine("");
                io.printLine(hint);
            });
        } catch (Exception ex) {
            io.printLine("Vinkkien lukeminen epäonnistui: "+ex.getMessage());
        }
    }
}
