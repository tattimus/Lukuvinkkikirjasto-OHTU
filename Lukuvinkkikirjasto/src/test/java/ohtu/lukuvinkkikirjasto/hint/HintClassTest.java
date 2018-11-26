/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.hint;

import ohtu.lukuvinkkikirjasto.hint.HintClass;
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
public class HintClassTest {

    private HintClass hint;

    @Before
    public void setUp() {
        hint = new HintClass(null, null, null, null);
    }

    @Test
    public void vinkkiSetTitle() {
        hint.setTitle("title");
        assertEquals(hint.getTitle(), "title");
    }
    @Test
    public void vinkkiSetComment() {
        hint.setComment("comment");
        assertEquals(hint.getComment(), "comment");
    }

}
