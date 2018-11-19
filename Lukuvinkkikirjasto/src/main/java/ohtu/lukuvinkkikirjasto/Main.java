package ohtu.lukuvinkkikirjasto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ohtu.lukuvinkkikirjasto.IO.CommandLineIO;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.UI.App;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.SQLHintDAO;
import ohtu.lukuvinkkikirjasto.database.Database;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.requests.AddHint;
import ohtu.lukuvinkkikirjasto.requests.AddHintStub;
import ohtu.lukuvinkkikirjasto.requests.CreateRequest;
import ohtu.lukuvinkkikirjasto.requests.QueryHints;
import ohtu.lukuvinkkikirjasto.requests.QueryHintsStub;
import ohtu.lukuvinkkikirjasto.requests.QueryRequest;

public class Main {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws Exception {
        Database database = new SQLiteDatabase(System.getProperty("user.dir")+"/lukuvinkkikirjasto.db");
        HintDAO hdao = new SQLHintDAO(database);
        CreateRequest creator = new AddHint(hdao);
        QueryRequest querier = new QueryHints(hdao);


        IO io = new CommandLineIO();
        App app = new App(io, creator, querier);
        app.run();
    }

}
