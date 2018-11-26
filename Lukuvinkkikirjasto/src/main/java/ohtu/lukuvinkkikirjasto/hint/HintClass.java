/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.hint;

import ohtu.lukuvinkkikirjasto.dao.ObjectWithID;

/**
 *
 * @author y50u
 */
public class HintClass implements Hint, ObjectWithID {

    private Integer id;

    private String title;
    private String comment;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HintClass(Integer id, String title, String comment, String url) {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.url = url;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public String toString() {
        return "\tOtsikko: " + title + "\n\tKommentti: " + comment;
    }
}
