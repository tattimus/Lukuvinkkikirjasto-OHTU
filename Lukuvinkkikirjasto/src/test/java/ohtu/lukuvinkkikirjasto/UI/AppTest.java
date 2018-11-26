package ohtu.lukuvinkkikirjasto.UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import ohtu.lukuvinkkikirjasto.IO.AsyncStubIO;
import ohtu.lukuvinkkikirjasto.UI.App;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.hint.HintClass;
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
    private App app;
    private AsyncStubIO io;

    @Before
    public void setUp() {
        io = new AsyncStubIO();
        app = new App(io);
        app.start();
    }
    
    @Test(timeout = 2000)
    public void appCanBeStopped() throws InterruptedException {
        io.pushInt(app.findAction("Lopeta"));
        
        //App stops within 500ms
        app.join(500);
    }

    @Test
    public void invalidActionsShowErrorMessage() throws InterruptedException {
        int action = 48932;
        io.pushInt(action);
        
        wait(500);
        
        assertTrue(io.getOutput().contains("Virheellinen komento: "+action));
    }
    
    @After
    public void tearDown() throws InterruptedException {
        if (app.isAlive()) {
            io.pushInt(app.findAction("Lopeta"));
            app.join();
        }
    }
    
    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
        }
    }
}
