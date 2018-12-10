package ohtu.lukuvinkkikirjasto;

import ohtu.lukuvinkkikirjasto.IO.CommandLineIO;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.UI.App;
import ohtu.lukuvinkkikirjasto.actions.AddByISBN;
import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.dao.MakerDAO;
import ohtu.lukuvinkkikirjasto.dao.MakerHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.SQLHintDAO;
import ohtu.lukuvinkkikirjasto.dao.SQLMakerDAO;
import ohtu.lukuvinkkikirjasto.dao.SQLMakerHintAssociationTable;
import ohtu.lukuvinkkikirjasto.database.Database;
import ohtu.lukuvinkkikirjasto.database.SQLiteDatabase;
import ohtu.lukuvinkkikirjasto.actions.AddHint;
import ohtu.lukuvinkkikirjasto.actions.DeleteHint;
import ohtu.lukuvinkkikirjasto.actions.ModifyHint;
import ohtu.lukuvinkkikirjasto.actions.QueryHints;
import ohtu.lukuvinkkikirjasto.actions.SearchByAttributes;
import ohtu.lukuvinkkikirjasto.actions.SearchByTag;
import ohtu.lukuvinkkikirjasto.actions.ShowHint;
import ohtu.lukuvinkkikirjasto.dao.SQLTagDAO;
import ohtu.lukuvinkkikirjasto.dao.SQLTagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.dao.TagDAO;
import ohtu.lukuvinkkikirjasto.dao.TagHintAssociationTable;
import ohtu.lukuvinkkikirjasto.isbn.ISBNFetcher;
import ohtu.lukuvinkkikirjasto.isbn.ISBNValidator;
import ohtu.lukuvinkkikirjasto.isbn.OpenLibraryISBNFetcher;
import okhttp3.OkHttpClient;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        OkHttpClient httpClient = new OkHttpClient();
        ISBNFetcher isbnFetcher = new OpenLibraryISBNFetcher(httpClient);

        Database database = new SQLiteDatabase(System.getProperty("user.dir")+"/lukuvinkkikirjasto.db");
        HintDAO hdao = new SQLHintDAO(database);

        TagDAO tdao=new SQLTagDAO(database);
        MakerDAO mdao = new SQLMakerDAO(database);
        MakerHintAssociationTable makerAssociation = new SQLMakerHintAssociationTable(database);
        TagHintAssociationTable tagAssociation=new SQLTagHintAssociationTable(database);


        IO io = new CommandLineIO();
        App app = new App(io, new AddHint(hdao, tdao, mdao, tagAssociation, makerAssociation),
                new QueryHints(hdao),
                new SearchByTag(hdao, tdao, tagAssociation),
                new ShowHint(hdao, tdao, mdao,tagAssociation, makerAssociation),
                new DeleteHint(hdao),
                new SearchByAttributes(hdao, mdao, makerAssociation),
                new AddByISBN(hdao, tdao, mdao, tagAssociation, makerAssociation, isbnFetcher),
                new ModifyHint(hdao, tdao, mdao, tagAssociation, makerAssociation));
        app.start();

        app.join();
    }

}
