package ohtu.lukuvinkkikirjasto;

import java.util.ArrayList;
import ohtu.lukuvinkkikirjasto.IO.CommandLineIO;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.UI.App;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.requests.AddHintStub;
import ohtu.lukuvinkkikirjasto.requests.CreateRequest;
import ohtu.lukuvinkkikirjasto.requests.QueryHintsStub;
import ohtu.lukuvinkkikirjasto.requests.QueryRequest;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList testList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Hint hint = new HintClass(i, "book" + i, "No comment");
            testList.add(hint);
        }
        CreateRequest creator = new AddHintStub();
        QueryRequest querier = new QueryHintsStub(testList);

        IO io = new CommandLineIO();
        App app = new App(io, creator, querier);
        app.run();
    }

}
