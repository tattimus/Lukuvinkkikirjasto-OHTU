/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.hint;

/**
 *
 * @author y50u
 */
public interface Hint {

    String getComment();

    String getTitle();

    void setComment(String comment);

    void setTitle(String title);
    
}
