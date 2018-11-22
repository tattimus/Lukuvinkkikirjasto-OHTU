/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.actions;

import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;

/**
 *
 * @author kape
 */
public class AddHint extends Action {
    
    private HintDAO hdao;
    private HintClass hint;
    
    public AddHint(HintDAO hdao) {
        this.hdao=hdao;
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
    @Override
    public String getHint() {
        return "Lisää vinkki";
    }

    @Override
    public void run(IO io) {
        try {
            String title = io.readString("Vinkin otsikko: ");
            String comment = io.readString("Vinkin kommentti: ");
            
            HintClass hint = new HintClass(null, title, comment);
            hdao.insert(hint);
            
            io.printLine("Lisätty vinkki \""+hint.getTitle()+"\"");
        } catch (Exception ex) {
            io.printLine("Vinkin lisääminen epäonnistui: "+ex.getMessage());
        }
    }
    
}
