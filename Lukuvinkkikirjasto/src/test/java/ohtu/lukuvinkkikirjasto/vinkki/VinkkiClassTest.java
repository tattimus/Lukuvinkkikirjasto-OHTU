/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.vinkki;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author y50u
 */
public class VinkkiClassTest {

    private VinkkiClass vinkki;

    @Before
    public void setUp() {
        vinkki = new VinkkiClass(0, null, null);
    }

    @Test
    public void vinkkiSetOtsikko() {
        vinkki.setOtsikko("otsikko");
        assertEquals(vinkki.getOtsikko(), "otsikko");
    }
    @Test
    public void vinkkiSetKommentti() {
        vinkki.setKommentti("kommentti");
        assertEquals(vinkki.getKommentti(), "kommentti");
    }

}
