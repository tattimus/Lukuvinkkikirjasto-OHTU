/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.requests;

import java.util.ArrayList;
import ohtu.lukuvinkkikirjasto.hint.Hint;

/**
 *
 * @author kape
 */
public class QueryHintsStub implements QueryRequest{
    
    private ArrayList<Hint> hints;
    public QueryHintsStub(ArrayList<Hint> hints) {
        this.hints=hints;
    }   
    public ArrayList<Hint> getAllRequests() {
        return this.hints;
    }
}
