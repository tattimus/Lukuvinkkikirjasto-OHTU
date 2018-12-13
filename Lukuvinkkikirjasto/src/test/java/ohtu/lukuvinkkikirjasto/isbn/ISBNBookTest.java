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
    public void testEqualsIsItself() {
        ISBNBook book = new ISBNBook("book", "author", "www.url.com", Arrays.asList("test"));
        assertEquals(book.equals(book), true);
    }

    @Test
    public void testEqualsNull() {

        ISBNBook bookNull = null;
        ISBNBook book = new ISBNBook("book", "author", "www.url.com", Arrays.asList("test"));
        assertEquals(book.equals(bookNull), false);
    }

    @Test
    public void testEqualsNotISBNbook() {

        String notBook = "not ISBN book";
        ISBNBook book = new ISBNBook("book", "author", "www.url.com", Arrays.asList("test"));
        assertEquals(book.equals(notBook), false);
    }

    @Test
    public void testEqualsDiffTitles() {

        ISBNBook book1 = new ISBNBook("diff book title", "author", "www.url.com", Arrays.asList("test"));
        ISBNBook book2 = new ISBNBook("book", "author", "www.url.com", Arrays.asList("test"));
        assertEquals(book1.equals(book2), false);
    }

    @Test
    public void testEqualsDiffAuthor() {

        ISBNBook book1 = new ISBNBook("book", "author", "www.url.com", Arrays.asList("test"));
        ISBNBook book2 = new ISBNBook("book", "different author", "www.url.com", Arrays.asList("test"));
        assertEquals(book1.equals(book2), false);
    }

    @Test
    public void testEqualsDiffURL() {

        ISBNBook book1 = new ISBNBook("book", "author", "www.url.com", Arrays.asList("test"));
        ISBNBook book2 = new ISBNBook("book", "author", "www.lru.com", Arrays.asList("test"));
        assertEquals(book1.equals(book2), false);
    }

    @Test
    public void testEqualsDiffTags() {

        ISBNBook book1 = new ISBNBook("book", "author", "www.url.com", Arrays.asList("test"));
        ISBNBook book2 = new ISBNBook("book", "author", "www.url.com", Arrays.asList("tag"));
        assertEquals(book1.equals(book2), false);
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
