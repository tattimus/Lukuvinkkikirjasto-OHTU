/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.isbn;

import java.io.IOException;
import java.util.Arrays;
import org.json.JSONException;

/**
 *
 * @author jaakko
 */
public class MockISBNFetcher implements ISBNFetcher {
    public static final String IO_EXCEPTION = "9780262033848";
    public static final String JSON_EXCEPTION = "9780133805918";
    
    @Override
    public ISBNBook fetchByISBN(String isbn) throws Exception {
        if (IO_EXCEPTION.equals(isbn)) {
            throw new IOException();
        }
        if (JSON_EXCEPTION.equals(isbn)) {
            throw new JSONException("Fake JSON exception!");
        }
        
        return new ISBNBook(isbn, "author", "url", Arrays.asList("tag_1", "tag_2"));
    }
    
}
