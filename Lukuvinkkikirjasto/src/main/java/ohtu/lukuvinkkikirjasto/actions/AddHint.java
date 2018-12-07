/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.DAO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.MakerDAO;
import ohtu.lukuvinkkikirjasto.dao.MakerHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.SQLTagDAO;
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
public class AddHint extends Action {

    private HintDAO hdao;
    private TagDAO tdao;
    private HintClass hint;
    private TagHintAssociationTable tagHint;
    private MakerDAO mdao;
    private MakerHintAssociationTable makerHint;


    public AddHint(HintDAO hdao, TagDAO tdao, MakerDAO mdao, TagHintAssociationTable tagHint, MakerHintAssociationTable makerHint) {
        this.hdao = hdao;
        this.tdao = tdao;
        this.tagHint = tagHint;
        this.mdao = mdao;
        this.makerHint = makerHint;

    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getHint() {
        return "Lisää vinkki";
    }

    private void addTags(String input, HintClass hint, IO io) {

        List<String> tags = new ArrayList<>();

            Arrays.stream(input.split(",")).filter(s -> !s.isEmpty()).forEach(tags::add);
            
            if (hint.getUrl() != null && !hint.getUrl().isEmpty()) {
                if (hint.getUrl().contains("youtube.com")) {
                    tags.add("video");
                }
                if (hint.getUrl().contains("dl.acm.org")) {
                    tags.add("kirja");
                }
            }
            
            tags.stream().map(s -> s.trim()).distinct().forEach(tag -> {
                try {
                    Tag t = tdao.insertOrGet(new Tag(null, tag));
                    tagHint.associate(t, hint);
                } catch (Exception ex) {
                    io.printLine("Tagin "+tag+" lisääminen epäonnistui: "+ex.getMessage());
                }
        });
    }

    private void addMakers(String input, HintClass hint, IO io) {

        List<String> makers = new ArrayList<>();

        Arrays.stream(input.split(",")).filter(s -> !s.isEmpty()).forEach(makers::add);

            makers.stream().map(s -> s.trim()).distinct().forEach(maker -> {
                try {
                    Maker m = mdao.insertOrGet(new Maker(null, maker));
                    makerHint.associate(m, hint);
                } catch (Exception ex) {
                    io.printLine("Tekijän "+maker+" lisääminen epäonnistui: "+ex.getMessage());
                }
        });
    }

    @Override
    public void run(IO io) {
        try {
            String title = io.readString("Vinkin otsikko: ");
            String comment = io.readString("Vinkin kommentti: ");
            String tekijat = io.readString("Tekijät (pilkulla eroteltuina): ");
            String tagit = io.readString("Vinkin tagit (pilkulla eroteltuina): ");
            String url = io.readString("Vinkkiin liittyvä URL: ");

            HintClass hint = new HintClass(null, title, comment, url);
            int hintId = hdao.insert(hint);
            hint.setID(hintId);

            addTags(tagit, hint, io);
            addMakers(tekijat, hint, io);
            
            io.printLine("Lisätty vinkki \"" + hint.getTitle() + "\"");
        } catch (Exception ex) {
            io.printLine("Vinkin lisääminen epäonnistui: " + ex.getMessage());
        }
    }

}
