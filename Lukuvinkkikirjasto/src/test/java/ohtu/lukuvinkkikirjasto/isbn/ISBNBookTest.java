/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.isbn;

import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author jaakko
 */
public class ISBNBookTest {
    @Test
    public void testHashCode() {
        ISBNBook book1 = new ISBNBook("test", "test", "test", Arrays.asList("test"));
        ISBNBook book2 = new ISBNBook("test", "test", "test", Arrays.asList("test"));
        
        assertEquals(book1.hashCode(), book2.hashCode());
    }
    
    @Test
    public void testEquals() {
        ISBNBook book1 = new ISBNBook("test", "test", "test", Arrays.asList("test"));
        ISBNBook book2 = new ISBNBook("test", "test", "test", Arrays.asList("test"));
        
        assertEquals(book1, book2);
    }
    
    @Test
    public void testGetTitle() {
        ISBNBook book1 = new ISBNBook("title", "author", "url", Arrays.asList("tag"));
        
        assertEquals("title", book1.getTitle());
    }
    
    @Test
    public void testGetAuthor() {
        ISBNBook book1 = new ISBNBook("title", "author", "url", Arrays.asList("tag"));
        
        assertEquals("author", book1.getAuthor());
    }
    
    @Test
    public void testGetUrl() {
        ISBNBook book1 = new ISBNBook("title", "author", "url", Arrays.asList("tag"));
        
        assertEquals("url", book1.getUrl());
    }
    
    @Test
    public void testGetTags() {
        ISBNBook book1 = new ISBNBook("title", "author", "url", Arrays.asList("tag"));
        
        assertEquals(1, book1.getTags().size());
        assertEquals("tag", book1.getTags().get(0));
    }
}
