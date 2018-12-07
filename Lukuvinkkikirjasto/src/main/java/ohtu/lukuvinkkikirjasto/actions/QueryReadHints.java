
package ohtu.lukuvinkkikirjasto.actions;

import java.util.List;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.hint.HintClass;


public class QueryReadHints extends Action {
    
    private HintDAO hdao;
    
    public QueryReadHints(HintDAO hdao) {
        this.hdao = hdao;
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
    @Override
    public String getHint() {
        return "Listaa luetut vinkit";
    }

    @Override
    public void run(IO io) {
        io.printLine("Luetut vinkit:");
        try {
            List<HintClass> tagit =hdao.findAll();
            for (int i = 0; i < tagit.size(); i++) {
                if(tagit.get(i).getTimestamp()!=null){
                    io.printLine("");
                    io.printLine(tagit.get(i));
                }
            }
        } catch (Exception ex) {
            io.printLine("Vinkkien lukeminen epäonnistui: "+ex.getMessage());
        }
    }
}
