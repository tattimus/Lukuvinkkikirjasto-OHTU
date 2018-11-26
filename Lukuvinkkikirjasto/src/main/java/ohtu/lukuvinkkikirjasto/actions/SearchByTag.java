
package ohtu.lukuvinkkikirjasto.actions;

import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.tag.Tag;

/**
 *
 * @author lindradi
 */
public class SearchByTag extends Action {
    
    private HintDAO hdao;
    private TagDAO tdao;
    private TagHintAssociationTable tagHint;

    public SearchByTag(HintDAO hdao, TagDAO tdao, TagHintAssociationTable tagHint) {
        this.hdao = hdao;
        this.tdao = tdao;
        this.tagHint = tagHint;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getHint() {
        return "Hae tagilla";
    }
    
    @Override
    public void run(IO io) {
        Tag tag = tagSearch(io.readString("Etsittävä tagi"));
        if (tag == null) {
            io.printLine("Tagilla ei löytynyt vinkkejä");
            return;
        }
        io.printLine("Tagi: "+tag.getTag());
        io.printLine("Vinkit:");
        try {
            tagHint.findBForA(tag).forEach(hint -> {
                io.printLine("");
                io.printLine(hint);
            });            
        } catch (Exception e){
            io.printLine("vinkkien hakeminen epäonnistui "+e.getMessage());
        }
    }
    
    private Tag tagSearch(String tagname) {
        try {
            for (Tag tag : tdao.findAll()){
                if (tag.getTag().equals(tagname)) return tag;
            }
        } catch (Exception e){     
        }
        return null;
    }
}
