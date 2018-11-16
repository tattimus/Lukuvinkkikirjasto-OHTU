package UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import ohtu.lukuvinkkikirjasto.UI.App;
import ohtu.lukuvinkkikirjasto.IO.CommandLineIO;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.IO.StubIO;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
import ohtu.lukuvinkkikirjasto.requests.AddHintStub;
import ohtu.lukuvinkkikirjasto.requests.CreateRequest;
import ohtu.lukuvinkkikirjasto.requests.QueryHintsStub;
import ohtu.lukuvinkkikirjasto.requests.QueryRequest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kape
 */
public class AppTest {

    private int[] numbers;
    private String[] texts;
    ArrayList<Hint> testList;
    AddHintStub creator;
    QueryHintsStub querier;

    public AppTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Hint hint = new HintClass(i, "book" + i, "No comment");
            testList.add(hint);
        }

        creator = new AddHintStub();
        querier = new QueryHintsStub(testList);

    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
    }

    @Test
    public void applicationStarts() {
        numbers = new int[10];
        numbers[0] = 3;
        StubIO io=createApp(numbers, texts);
//        StubIO io = new StubIO(numbers, texts);
//        App app = new App(io, creator, querier);
//        app.run();
        assertEquals("My links and reads\n", io.getOutputs().get(0));

    }

    @Test
    public void hintCanBeInserted() {
        numbers = new int[10];
        numbers[0] = 1;
        numbers[1] = 3;
        texts = new String[3];
        texts[0] = "my first hint";

        texts[1] = "Comment for my first hint";
        StubIO io=createApp(numbers, texts);

//        StubIO io = new StubIO(numbers, texts);
//        App app = new App(io, creator, querier);
//
//        app.run();
        ArrayList<String> outputs = io.getOutputs();

        assertEquals(outputs.get(1), "New item created");
        assertEquals(creator.getTitle(), "my first hint");
        assertEquals(creator.getComment(), "Comment for my first hint");
    }

    @Test
    public void SeveralHintsCanBeInserted() {
        numbers = new int[10];
        numbers[0] = 1;
        numbers[1] = 1;
        numbers[2] = 3;
        texts = new String[4];
        texts[0] = "My first book";

        texts[1] = "Comment for my first book";
        texts[2] = "My second book";

        texts[3] = "Comment for my second book";
        StubIO io=createApp(numbers, texts);

//        StubIO io = new StubIO(numbers, texts);
//        App app = new App(io, creator, querier);
//
//        app.run();

        ArrayList<String> outputs = io.getOutputs();

        assertEquals(outputs.get(1), "New item created");
        assertEquals(outputs.get(3), "New item created");
        assertEquals(creator.getTitle(), "My second book");
        assertEquals(creator.getComment(), "Comment for my second book");

    }

    @Test
    public void hintsCanBeListed() {
        numbers = new int[10];
        numbers[0] = 2;
        numbers[1] = 3;
        StubIO io=createApp(numbers, texts);
//        StubIO io = new StubIO(numbers, texts);
//        App app = new App(io, creator, querier);
//        app.run();
        ArrayList<String> outputs = io.getListOutputs();
        assertEquals(5, outputs.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(outputs.get(i), "book" + i + " No comment");
        }

    }

    public void hintsCanBeAddedAndlisted() {
        numbers = new int[10];
        numbers[0] = 1;
        numbers[1] = 2;
        numbers[2] = 3;
        texts = new String[3];
        texts[0] = "my first hint";
        texts[1] = "Comment for my first hint";
        StubIO io=createApp(numbers, texts);
//        StubIO io = new StubIO(numbers, texts);
//        App app = new App(io, creator, querier);
//        app.run();
        ArrayList<String> outputs = io.getOutputs();
        assertEquals(outputs.get(1), "New item created");
        assertEquals(creator.getTitle(), "my first hint");
        assertEquals(creator.getComment(), "Comment for my first hint");
        ArrayList<String> outputList = io.getListOutputs();
        assertEquals(5, outputList.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(outputList.get(i), "book" + i + " No comment");
        }
    }

    private StubIO createApp(int[] numbers, String[] texts) {
        StubIO io = new StubIO(numbers, texts);
        App app = new App(io, creator, querier);
        app.run();
        return io;
    }
}
