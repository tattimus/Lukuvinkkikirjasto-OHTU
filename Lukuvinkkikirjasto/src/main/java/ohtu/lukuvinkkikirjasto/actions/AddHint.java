/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.SQLTagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.tag.Tag;

/**
 *
 * @author kape
 */
public class AddHint extends Action {

    private HintDAO hdao;
    private TagDAO tdao;
    private HintClass hint;
    private TagHintAssociationTable tagHint;

    public AddHint(HintDAO hdao, TagDAO tdao, TagHintAssociationTable tagHint) {
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
        return "Lisää vinkki";
    }

    @Override
    public void run(IO io) {
        try {
            String title = io.readString("Vinkin otsikko: ");
            String comment = io.readString("Vinkin kommentti: ");
            String tagit = io.readString("Vinkin tagit(pilkulla eroteltuina) :");

            HintClass hint = new HintClass(null, title, comment);
            int hintId = hdao.insert(hint);

            if (tagit.length() > 0) {
                for (String newT : tagit.split(",")) {
                    String newTag= newT.trim();
                    Tag t = tdao.insertOrGet(new Tag(null, newTag));
                    tagHint.associate(t, new HintClass(hintId, null, null));
                }
            }

            io.printLine("Lisätty vinkki \"" + hint.getTitle() + "\"");
        } catch (Exception ex) {
            io.printLine("Vinkin lisääminen epäonnistui: " + ex.getMessage());
        }
    }

}
