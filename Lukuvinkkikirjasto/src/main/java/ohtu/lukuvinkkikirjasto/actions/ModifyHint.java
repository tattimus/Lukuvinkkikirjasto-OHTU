/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.tag.Tag;

public class ModifyHint extends Action {

    private HintDAO hdao;
    private TagDAO tdao;
    private TagHintAssociationTable tagHint;

    public ModifyHint(HintDAO hdao, TagDAO tdao, TagHintAssociationTable tagHint) {
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
        return "Muokkaa vinkkiä";
    }

    @Override
    public void run(IO io) {
        try {
            int id = io.readInt("Muokattavan vinkin ID: ");
            HintClass hint = hdao.findOne(id);
            if (hint == null) {
                io.printLine("ID:llä " + id + " ei löytynyt vinkkiä");
                return;
            }
            
            io.printLine("Jos kenttä jätetään tyhjäksi, sitä ei päivitetä");
            
            String title = io.readString("Nykyinen otsikko \""+hint.getTitle()+"\", uusi otsikko: ").trim();
            String comment = io.readString("Nykyinen kommentti \""+hint.getComment()+"\", uusi kommentti: ").trim();
            String url = io.readString("Nykyinen URL \""+hint.getUrl()+"\", uusi URL: ").trim();

            List<Tag> tags = tagHint.findAForB(hint);
            if (!tags.isEmpty()) {
                io.printLine("Nykyiset tagit: "+String.join(", ", tags.stream().map(t -> t.getTag()).collect(Collectors.toList())));
            }
            
            List<Tag> removeTags = new ArrayList<>();
            tags.forEach(tag -> {
                String command = io.readString("Poistetaanko tagi "+tag.getTag()+"? (y/n)");
                if ("y".equalsIgnoreCase(command)) {
                    removeTags.add(tag);
                }
            });
            
            List<String> newTags = new ArrayList<>();
                    
            Arrays.stream(io.readString("Uudet tagit (pilkulla erotettuna): ").split(","))
                .map(tag -> tag.trim())
                .filter(tag -> !tag.isEmpty())
                .forEach(newTags::add);
            
            if (url != null && !url.isEmpty()) {
                if (url.contains("youtube.com")) {
                    newTags.add("video");
                }
                if (url.contains("dl.acm.org")) {
                    newTags.add("kirja");
                }
            }
            
            io.printLine("Muokataan vinkkiä "+hint.getID());
            if (title != null && !title.isEmpty() && !hint.getTitle().equals(title)) {
                io.printLine("Otsikko:\n\t\""+hint.getTitle()+"\" → \""+title+"\"");
            }
            if (comment != null && !comment.isEmpty() && !hint.getComment().equals(comment)) {
                io.printLine("Kommentti:\n\t\""+hint.getComment()+"\" → \""+comment+"\"");
            }
            if (url != null && !url.isEmpty() && !hint.getUrl().equals(url)) {
                io.printLine("URL:\n\t\""+hint.getUrl()+"\" → \""+url+"\"");
            }
            if (removeTags.size() > 0) {
                io.printLine("Poistettavat tagit:\n\t"+String.join(", ", removeTags.stream().map(tag -> tag.getTag()).collect(Collectors.toList())));
            }
            if (newTags.size() > 0) {
                io.printLine("Lisättävät tagit:\n\t"+String.join(", ", newTags));
            }
            String confirmation = io.readString("Tee muutokset? (y/n)");
            if ("y".equalsIgnoreCase(confirmation)) {
                if (title != null && !title.isEmpty() && !hint.getTitle().equals(title)) {
                    hint.setTitle(title);
                }
                if (comment != null && !comment.isEmpty() && !hint.getComment().equals(comment)) {
                    hint.setComment(comment);
                }
                if (url != null && !url.isEmpty() && !hint.getUrl().equals(url)) {
                    hint.setUrl(url);
                }
                hdao.update(hint);
                
                removeTags.forEach(tag -> {
                    try {
                        tagHint.unassociate(tag, hint);
                    } catch (Exception ex) {
                        io.printLine("Tagin "+tag.getTag()+" poistaminen epäonnistui: "+ex.getMessage());
                    }
                });
                newTags.forEach(tag -> {
                    try {
                        tagHint.associate(tdao.insertOrGet(new Tag(null, tag)), hint);
                    } catch (Exception ex) {
                        io.printLine("Tagin "+tag+" lisääminen epäonnistui: "+ex.getMessage());
                    }
                });
                
                io.printLine("Vinkin "+hint.getID()+" tiedot muokattu");
            } else {
                io.printLine("Vinkkiä "+hint.getID()+" ei muokattu");
            }
        } catch (Exception ex) {
            io.printLine("Vinkin muokkaaminen epäonnistui: " + ex.getMessage());
        }
    }

}
