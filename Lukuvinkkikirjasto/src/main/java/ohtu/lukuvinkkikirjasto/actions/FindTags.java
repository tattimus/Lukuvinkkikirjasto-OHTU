/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.actions;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.*;
import org.jsoup.nodes.*;

public class FindTags {

    public List<String> search(String url) throws Exception {
        List<String> tags = new ArrayList<>();
        Document doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
        if (doc.getElementById("authortags") != null) {
            tags = doc.getElementById("authortags").children().eachText();
        }

        return tags;
    }
}
