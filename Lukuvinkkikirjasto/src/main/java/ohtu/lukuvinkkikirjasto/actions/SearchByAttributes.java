/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.MakerDAO;
import ohtu.lukuvinkkikirjasto.dao.MakerHintAssociationTable;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.maker.Maker;

/**
 *
 * @author kape
 */
public class SearchByAttributes extends Action {

    private HintDAO hdao;
    private MakerDAO mdao;
    private MakerHintAssociationTable maker_association;
    private IO io;
    private List<Integer> searchResults;

    public SearchByAttributes(HintDAO hdao, MakerDAO mdao, MakerHintAssociationTable m_association) {

        this.hdao = hdao;
        this.mdao = mdao;
        this.maker_association = m_association;
        this.searchResults = new ArrayList<>();
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getHint() {
        return "Vapaa haku";
    }

    @Override
    public void run(IO io) {
        this.io = io;
        String attribute = io.readString("Syötä hakusana: ");
        searchByWord(attribute.toLowerCase());
        printResults();

        this.searchResults.clear();

    }

    private void searchByWord(String attribute) {

        for (int i = 1; i <= 3; i++) {
            findHints(i, attribute);

        }
    }

    private void findHints(int prio, String attribute) {
        ArrayList<Integer> tulokset = new ArrayList<>();
        Map<Integer, String> fields = new HashMap<>();
        fields.put(1, "otsikko");
        fields.put(2, "tekija");
        fields.put(3, "kommentti");

        if (prio != 2) {
            searchFromHint(fields.get(prio), attribute);

        } else {
            searchFromMakers(attribute);
//            try {
//                List<Maker> makers = mdao.findByMaker(attribute);
//                makers.forEach(maker -> {
//                    try {
//                        List<HintClass> makersHints = this.maker_association.findBForA(maker);
//                        makersHints.stream().filter((h) -> (!this.searchResults.contains(h.getID()))).forEach((h) -> {
//                            this.searchResults.add(h.getID());
//                        });
//
//                    } catch (Exception ex) {
//                    }
//                });
//            } catch (Exception e) {
//            }
        }

    }

    private void printResults() {

        if (searchResults.isEmpty()) {
            io.printLine("\nHakusanalla ei löytynyt tuloksia");
        }
        this.searchResults.forEach(hint -> {
            io.printLine("");
            try {
                HintClass h = hdao.findOne(hint);
                io.printLine(h);
                List<Maker> makers = this.maker_association.findAForB(h);
                String tekijat = "\ttekijät: ";
                for (Maker m : makers) {
                    tekijat += m.toString() + " ";
                }
                io.printLine(tekijat);
            } catch (Exception ex) {

            }
        });
    }

    private void searchFromHint(String attribute, String attributeValue) {
        try {

            List<HintClass> list = hdao.search(attribute, attributeValue);
            list.stream().filter((h) -> (!this.searchResults.contains(h.getID()))).forEach((h) -> {
                this.searchResults.add(h.getID());
            });
        } catch (Exception e) {
            io.printLine("virhe haussa");

        }
    }

    private void searchFromMakers(String attribute) {
        try {
            List<Maker> makers = mdao.findByMaker(attribute);
            makers.forEach(maker -> {
                try {
                    List<HintClass> makersHints = this.maker_association.findBForA(maker);
                    makersHints.stream().filter((h) -> (!this.searchResults.contains(h.getID()))).forEach((h) -> {
                        this.searchResults.add(h.getID());
                    });

                } catch (Exception ex) {
                }
            });
        } catch (Exception e) {
        }
    }

}
