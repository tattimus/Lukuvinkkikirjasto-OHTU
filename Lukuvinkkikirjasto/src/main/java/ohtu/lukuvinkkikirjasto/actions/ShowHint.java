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
import ohtu.lukuvinkkikirjasto.dao.MakerDAO;
import ohtu.lukuvinkkikirjasto.dao.MakerHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.maker.Maker;
import ohtu.lukuvinkkikirjasto.tag.Tag;

/**
 *
 * @author kape
 */
public class ShowHint extends Action {

    private HintDAO hdao;
    private TagDAO tdao;
    private MakerDAO mdao;
    private MakerHintAssociationTable makerHint;
    private TagHintAssociationTable connect;

    public ShowHint(HintDAO h, TagDAO t, MakerDAO m, TagHintAssociationTable connect, MakerHintAssociationTable mH) {
        this.hdao = h;
        this.tdao = t;
        this.mdao = m;
        this.connect = connect;
        this.makerHint = mH;
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
            List<Maker> makers = makerHint.findAForB(hint);

            printData(hint, tags, makers, io);
            if (hint.getTimestamp() == null) {
                String r = io.readString("\nMerkitäänkö luetuksi(y/n)").toLowerCase();
                if (r.equals("y")) {

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

    private void printData(HintClass hint, List<Tag> tags, List<Maker> makers, IO io) {
        io.printLine(hint.printAll());
        String tagOutput = "\tTagit: ";
        for (Tag t : tags) {
            tagOutput += t.toString() + " ";
        }
        io.printLine(tagOutput);

        String makerOutput = "\tTekijät: ";
        for (Maker m : makers) {
            makerOutput += m.toString() + " ";
        }
        io.printLine(makerOutput);
    }

}
