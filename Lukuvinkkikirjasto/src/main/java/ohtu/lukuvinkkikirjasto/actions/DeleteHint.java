
package ohtu.lukuvinkkikirjasto.actions;

import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.hint.Hint;

/**
 *
 * @author lindradi
 */
public class DeleteHint extends Action {
    
    private HintDAO hdao;
    
    public DeleteHint(HintDAO hdao) {
        this.hdao = hdao;
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
    @Override
    public String getHint() {
        return "Poista vinkki";
    }

    @Override
    public void run(IO io) {
        Integer id = null;
        try {
            id = Integer.parseInt(io.readString("Poistettavan vinkin ID"));
        } catch (Exception e){
            io.printLine("Virheellinen id ");
            return;
        }
        try {
            if (hdao.findOne(id) == null) {
                io.printLine("Virheellinen id");
                return;
            }                                
        } catch (Exception ex) {
            io.printLine("Vinkin poistaminen epäonnistui "+ex.getLocalizedMessage());
        }
        try {
            Hint hint = hdao.findOne(id);
            io.printLine("Haluatko varmasti poistaa vinkin:\n\n"+hint+"\n\n(Y/N)?");
            if (io.readString("").toUpperCase().equals("Y")) {
                hdao.delete(id);
                io.printLine("Vinkki poistettu");
            } else {
                io.printLine("Vinkkiä ei poistettu");
            }            
        } catch (Exception ex) {
            io.printLine("vinkin poistaminen epäonnistui "+ex.getLocalizedMessage());            
        }    
    }
    
}
