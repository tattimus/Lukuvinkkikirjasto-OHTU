/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.hint.HintClass;

/**
 *
 * @author kape
 */
public class SearchByAttributes extends Action {

    private HintDAO hdao;
    private IO io;

    public SearchByAttributes(HintDAO hdao) {

        this.hdao = hdao;
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
        this.io=io;
        String attribute = io.readString("Syötä hakusana: ");
        List<HintClass> list = new ArrayList<>();
        list=searchByWord(attribute.toLowerCase());
        list.forEach(hint-> {
          io.printLine("");
          io.printLine(hint);
        });

    }

    private List<HintClass> searchByWord(String attribute) {

        List<String> fields = Arrays.asList("otsikko", "kommentti");
        List<HintClass> hints = new ArrayList<>();
        try {
            for (String s : fields) {
               List<HintClass> list = hdao.search(s, attribute);
               list.forEach(hint -> {
                   if(!hints.contains(hint)) hints.add(hint);
               });
            }
        } catch (Exception e) {
            io.printLine("virhe haussa");
            

        }
        return hints;
    }

}
