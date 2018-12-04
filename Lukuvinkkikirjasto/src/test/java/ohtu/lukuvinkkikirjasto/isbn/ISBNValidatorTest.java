/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.isbn;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jaakko
 */
public class ISBNValidatorTest {
    @Test
    public void testValidWithDashes() {
        assertTrue(ISBNValidator.validate("978-3-16-148410-0"));
    }
    
    @Test
    public void testValidWithoutDashes() {
        assertTrue(ISBNValidator.validate("9783161484100"));
    }
    
    @Test
    public void testInvalid() {
        assertFalse(ISBNValidator.validate("test"));
    }
    
    @Test
    public void testInvalidBecauseIncorrectCheckDigit() {
        assertFalse(ISBNValidator.validate("9783161484107"));
    }
}
