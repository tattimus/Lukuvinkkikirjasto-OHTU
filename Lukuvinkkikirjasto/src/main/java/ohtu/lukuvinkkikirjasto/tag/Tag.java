/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.tag;

import ohtu.lukuvinkkikirjasto.dao.ObjectWithID;

/**
 *
 * @author jaakko
 */
public class Tag implements ObjectWithID {
    private Integer id;
    private String tag;

    public Tag(Integer id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    
    @Override
    public String toString() {
        return tag;
    }
}
