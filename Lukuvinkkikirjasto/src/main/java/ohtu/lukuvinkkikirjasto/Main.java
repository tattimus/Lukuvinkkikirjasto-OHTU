package ohtu.lukuvinkkikirjasto;

import ohtu.lukuvinkkikirjasto.IO.CommandLineIO;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.UI.App;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.SQLHintDAO;
import ohtu.lukuvinkkikirjasto.database.Database;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.actions.AddHint;
import ohtu.lukuvinkkikirjasto.actions.QueryHints;

public class Main {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws Exception {
        Database database = new SQLiteDatabase(System.getProperty("user.dir")+"/lukuvinkkikirjasto.db");
        HintDAO hdao = new SQLHintDAO(database);

        IO io = new CommandLineIO();
        App app = new App(io, new AddHint(hdao), new QueryHints(hdao));
        app.start();
        
        app.join();
    }

}
