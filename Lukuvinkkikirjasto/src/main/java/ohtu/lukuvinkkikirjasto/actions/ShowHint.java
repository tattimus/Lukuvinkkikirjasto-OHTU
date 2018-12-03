/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.actions;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.tag.Tag;

/**
 *
 * @author kape
 */
public class ShowHint extends Action {

    private HintDAO hdao;
    private TagDAO tdao;
    private TagHintAssociationTable connect;

    public ShowHint(HintDAO h, TagDAO t, TagHintAssociationTable connect) {
        this.hdao = h;
        this.tdao = t;
        this.connect = connect;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getHint() {
        return "Näytä vinkki";
    }

    @Override
    public void run(IO io) {
        int id = io.readInt("Näytettävän vinkin id: ");
        try {
            HintClass hint = hdao.findOne(id);
            List<Tag> tags = connect.findAForB(hint);
            printData(hint, tags, io);
            if (hint.getTimestamp() == null) {
                String r = io.readString("\nMerkitäänkö luetuksi(y/n)");
                if (r.equals("y") || r.equals("Y")) {

                    Date ts=new Timestamp(System.currentTimeMillis());
                    hint.setTimestamp(ts);
                    hdao.update(hint);
                    io.printLine("Vinkki on merkitty luetuksi");

                }
            }
        } catch (Exception e) {
            io.printLine("Virheellinen syöte");
        }

    }

    private void printData(HintClass hint, List<Tag> tags, IO io) {
        io.printLine(hint.printAll());
        String tagOutput = "\tTagit: ";
        for (Tag t : tags) {
            tagOutput += t.toString() + " ";
        }
        io.printLine(tagOutput);
    }

}
