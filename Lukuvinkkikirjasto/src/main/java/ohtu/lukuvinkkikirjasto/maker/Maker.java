/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.maker;

import ohtu.lukuvinkkikirjasto.dao.ObjectWithID;

/**
 *
 * @author lindradi
 */

public class Maker implements ObjectWithID {
    private Integer id;
    private String maker;
    
    public Maker(Integer id, String maker) {
        this.id = id;
        this.maker = maker;
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;
    }

    public String getMaker() {
        return maker;
    }
    
    public void setMaker(String maker) {
        this.maker = maker;
    }
    
    @Override
    public String toString() {
        return maker;
    }
}

