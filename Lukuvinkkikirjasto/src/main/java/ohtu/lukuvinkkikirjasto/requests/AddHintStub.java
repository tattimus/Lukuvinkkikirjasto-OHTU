/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.requests;

import ohtu.lukuvinkkikirjasto.hint.Hint;

/**
 *
 * @author kape
 */
public class AddHintStub implements CreateRequest {

    private String title;
    private String comment;

    public AddHintStub() {

    }
@Override
    public String createHint(String title, String comment) {
        this.title = title;
        this.comment = comment;
        return "New item created";
    }
    
    public String getTitle() {
        return this.title;
    }
    public String getComment() {
        return this.comment;
    }

}
