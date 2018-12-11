/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.MakerDAO;
import ohtu.lukuvinkkikirjasto.dao.MakerHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.isbn.ISBNBook;
import ohtu.lukuvinkkikirjasto.isbn.ISBNFetcher;
import ohtu.lukuvinkkikirjasto.isbn.ISBNValidator;
import ohtu.lukuvinkkikirjasto.maker.Maker;
import ohtu.lukuvinkkikirjasto.tag.Tag;
import org.json.JSONException;

/**
 *
 * @author jaakko
 */
public class AddByISBN extends Action {
    private HintDAO hintDao;
    private TagDAO tagDao;
    private MakerDAO makerDao;
    private TagHintAssociationTable tagHintAssociationTable;
    private MakerHintAssociationTable makerHintAssociationTable;

    private ISBNFetcher isbnFetcher;
    
    public AddByISBN(HintDAO hintDao, TagDAO tagDao, MakerDAO makerDao, TagHintAssociationTable tagHintAssociationTable, MakerHintAssociationTable makerHintAssociationTable, ISBNFetcher isbnFetcher) {
        this.hintDao = hintDao;
        this.tagDao = tagDao;
        this.makerDao = makerDao;
        this.tagHintAssociationTable = tagHintAssociationTable;
        this.makerHintAssociationTable = makerHintAssociationTable;
        this.isbnFetcher = isbnFetcher;
    }
    
    @Override
    public String getHint() {
        return "Lisää ISBN:n perusteella";
    }

    @Override
    public void run(IO io) {
        String isbn = io.readString("Syötä ISBN: ");
        
        if (ISBNValidator.validate(isbn)) {
            io.printLine("Ladataan kirjan tietoja...");
            
            try {
                ISBNBook book = isbnFetcher.fetchByISBN(isbn);
                
                io.printLine("");
                io.printLine("ISBN:llä "+isbn+" löytyi kirja "+book.getTitle());
                
                String title = book.getTitle();
                io.printLine("Otsikko: "+book.getTitle());
                
                String comment = io.readString("Kirjalla ei ole kommenttia, syötä kommentti: ");
                
                String author = null;
                if (book.getAuthor() != null && !book.getAuthor().isEmpty()) {
                    io.printLine("Tekijä: "+book.getAuthor());
                    author = book.getAuthor();
                } else {
                    author = io.readString("Kirjalla ei ole tekijää, syötä tekijä: ");
                }
                
                String url = null;
                if (book.getUrl() != null && !book.getUrl().isEmpty()) {
                    io.printLine("URL: "+book.getUrl());
                    url = book.getUrl();
                } else {
                    url = io.readString("Kirjalla ei ole URLia, syötä URL: ");
                }
                
                List<String> tags = null;
                if (book.getTags() != null && !book.getTags().isEmpty()) {
                    io.printLine("Tagit: "+String.join(", ", book.getTags()));
                    tags = book.getTags();
                } else {
                    String userTags = io.readString("Kirjalla ei ole tageja, syötä tagit (pilkulla erotettuna): ");
                    tags = Arrays.stream(userTags.split(",")).map(tag -> tag.trim()).filter(s -> !s.isEmpty()).collect(Collectors.toList());
                }
                
                HintClass hint = new HintClass(null, title, comment, url);
                hint.setID(hintDao.insert(hint));
                
                Maker maker = makerDao.insertOrGet(new Maker(null, author));
                makerHintAssociationTable.associate(maker, hint);
                
                tags.stream().map(tag -> {
                    try {
                        return tagDao.insertOrGet(new Tag(null, tag));
                    } catch (Exception ex) {
                        io.printLine("Tagin "+tag+" lisääminen epäonnistui: "+ex.getMessage());
                        return null;
                    }
                }).filter(Objects::nonNull).forEach(tag -> {
                    try {
                        tagHintAssociationTable.associate(tag, hint);
                    } catch (Exception ex) {
                        io.printLine("Tagin "+tag.getTag()+" lisääminen epäonnistui: "+ex.getMessage());
                    }
                });
                
                io.printLine("");
                io.printLine("Lisätty vinkki \""+hint.getTitle()+"\" ISBN:n "+isbn+" perusteella");
            } catch (Exception e) {
                if (e instanceof JSONException) {
                    io.printLine("ISBN:llä "+isbn+" ei löytynyt kirjaa.");
                } else if (e instanceof IOException) {
                    io.printLine("Kirjan tietojen lataaminen epäonnistui, tarkista verkkoyhteytesi.");
                } else if (e instanceof SQLException) {
                    io.printLine("Vinkin tallennus epäonnistui: "+e.getMessage());
                }
                e.printStackTrace();
            }
        } else {
            io.printLine("Virheellinen ISBN: "+isbn);
        }
    }
    
}
