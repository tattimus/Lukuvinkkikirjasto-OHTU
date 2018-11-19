
package ohtu.lukuvinkkikirjasto.hintTypes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {
    
   private Book book;
    
    @Before
    public void setUp() {
        book = new Book();
    }
   
     @Test
     public void testAddTitle() {
     book.setTitle("title");
         assertEquals(book.getTitle(), "title");
     
     }
     @Test
     public void testAddAuthor() {
     book.setAuthor("author");
         assertEquals(book.getAuthor(), "author");
     
     }
     @Test
     public void testAddISBN() {
     book.setIsbn("isbn");
         assertEquals(book.getIsbn(), "isbn");
     
     }
}

