/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.isbn;

import java.util.ArrayList;
import java.util.List;
import ohtu.lukuvinkkikirjasto.isbn.ISBNBook;
import ohtu.lukuvinkkikirjasto.isbn.ISBNFetcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jaakko
 */
public class OpenLibraryISBNFetcher implements ISBNFetcher {
    private static final String URL = "https://openlibrary.org/api/books?bibkeys=ISBN:%s&format=json&jscmd=data";
    
    private OkHttpClient httpClient;
    
    public OpenLibraryISBNFetcher(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }
    
    @Override
    public ISBNBook fetchByISBN(String isbn) throws Exception {
        String url = String.format(URL, isbn);
        
        try (Response response = httpClient.newCall(new Request.Builder().url(url).build()).execute()) {
            return parse(response.body().string());
        }
    }
    
    ISBNBook parse(String json) {
        JSONObject root = new JSONObject(json);
        
        JSONObject book = root.getJSONObject(root.keys().next());
        
        String title = book.optString("title");
        String author = book.optString("by_statement");
        String url = book.optString("url");
        List<String> tags = new ArrayList<>();
        
        JSONArray subjects = book.optJSONArray("subjects");
        if (subjects != null) {
            for (int i = 0; i < subjects.length(); i++) {
                JSONObject subject = subjects.getJSONObject(i);
                String tag = subject.optString("name");
                
                if (tag != null && !tag.isEmpty()) {
                    tags.add(tag);
                }
            }
        }
        
        return new ISBNBook(title, author, url, tags);
    }
}
