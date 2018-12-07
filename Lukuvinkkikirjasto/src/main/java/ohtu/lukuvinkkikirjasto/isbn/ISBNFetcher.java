/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.isbn;

import ohtu.lukuvinkkikirjasto.isbn.ISBNBook;

/**
 *
 * @author jaakko
 */
public interface ISBNFetcher {
    ISBNBook fetchByISBN(String isbn) throws Exception;
}
