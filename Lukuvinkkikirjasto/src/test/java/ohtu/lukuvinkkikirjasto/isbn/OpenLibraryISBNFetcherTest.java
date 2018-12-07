/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.isbn;

import okhttp3.OkHttpClient;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author jaakko
 */
public class OpenLibraryISBNFetcherTest {
    private static final String RESPONSE = "{\n" +
"  \"ISBN:9780262533058\": {\n" +
"    \"publishers\": [\n" +
"      {\n" +
"        \"name\": \"The MIT Press\"\n" +
"      }\n" +
"    ],\n" +
"    \"pagination\": \"p. cm.\",\n" +
"    \"classifications\": {\n" +
"      \"dewey_decimal_class\": [\n" +
"        \"005.1\"\n" +
"      ],\n" +
"      \"lc_classifications\": [\n" +
"        \"QA76.6 .I5858 2009\"\n" +
"      ]\n" +
"    },\n" +
"    \"title\": \"Introduction to algorithms\",\n" +
"    \"url\": \"https://openlibrary.org/books/OL23170657M/Introduction_to_algorithms\",\n" +
"    \"notes\": \"Includes bibliographical references and index.\",\n" +
"    \"identifiers\": {\n" +
"      \"isbn_13\": [\n" +
"        \"9780262033848\",\n" +
"        \"9780262533058\"\n" +
"      ],\n" +
"      \"lccn\": [\n" +
"        \"2009008593\"\n" +
"      ],\n" +
"      \"openlibrary\": [\n" +
"        \"OL23170657M\"\n" +
"      ],\n" +
"      \"librarything\": [\n" +
"        \"9820219\"\n" +
"      ],\n" +
"      \"goodreads\": [\n" +
"        \"6752187\",\n" +
"        \"7160858\"\n" +
"      ]\n" +
"    },\n" +
"    \"cover\": {\n" +
"      \"small\": \"https://covers.openlibrary.org/b/id/6959894-S.jpg\",\n" +
"      \"large\": \"https://covers.openlibrary.org/b/id/6959894-L.jpg\",\n" +
"      \"medium\": \"https://covers.openlibrary.org/b/id/6959894-M.jpg\"\n" +
"    },\n" +
"    \"subjects\": [\n" +
"      {\n" +
"        \"url\": \"https://openlibrary.org/subjects/computer_algorithms\",\n" +
"        \"name\": \"Computer algorithms\"\n" +
"      },\n" +
"      {\n" +
"        \"url\": \"https://openlibrary.org/subjects/computer_programming\",\n" +
"        \"name\": \"Computer programming\"\n" +
"      },\n" +
"      {\n" +
"        \"url\": \"https://openlibrary.org/subjects/long_now_manual_for_civilization\",\n" +
"        \"name\": \"Long Now Manual for Civilization\"\n" +
"      }\n" +
"    ],\n" +
"    \"publish_date\": \"2009\",\n" +
"    \"key\": \"/books/OL23170657M\",\n" +
"    \"by_statement\": \"Thomas H. Cormen ... [et al.].\",\n" +
"    \"publish_places\": [\n" +
"      {\n" +
"        \"name\": \"Cambridge, MA\"\n" +
"      }\n" +
"    ],\n" +
"    \"ebooks\": [\n" +
"      {\n" +
"        \"formats\": {},\n" +
"        \"preview_url\": \"https://archive.org/details/introductiontoal00corm_453\",\n" +
"        \"availability\": \"restricted\"\n" +
"      }\n" +
"    ]\n" +
"  }\n" +
"}";
    
    OpenLibraryISBNFetcher fetcher;
    
    @Before
    public void setup() {
        fetcher = new OpenLibraryISBNFetcher(new OkHttpClient());
    }
    
    @Test
    public void testParsing() {
        ISBNBook book = fetcher.parse(RESPONSE);
        
        assertEquals("Introduction to algorithms", book.getTitle());
        assertEquals("Thomas H. Cormen ... [et al.].", book.getAuthor());
        assertEquals("https://openlibrary.org/books/OL23170657M/Introduction_to_algorithms", book.getUrl());
        assertEquals(3, book.getTags().size());
    }
    
    @Test(expected = JSONException.class)
    public void testExceptionIsThrownWhenParsingInvalidData() {
        fetcher.parse("test");
    }
}
