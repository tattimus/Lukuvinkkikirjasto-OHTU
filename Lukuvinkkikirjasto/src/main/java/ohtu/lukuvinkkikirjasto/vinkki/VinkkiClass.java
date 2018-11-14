/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.vinkki;

import ohtu.lukuvinkkikirjasto.dao.ObjectWithID;

/**
 *
 * @author y50u
 */
public class VinkkiClass implements Vinkki, ObjectWithID {
    public VinkkiClass(Integer id, String otsikko, String kommentti) {
        this.id = id;
        this.otsikko = otsikko;
        this.kommentti = kommentti;
    }
    
    private Integer id;
    
    private String otsikko;
    private String kommentti;
    
    @Override
    public void setID(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getID() {
        return id;
    }
    
    @Override
    public String getOtsikko() {
        return otsikko;
    }

    @Override
    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    @Override
    public String getKommentti() {
        return kommentti;
    }

    @Override
    public void setKommentti(String kommentti) {
        this.kommentti = kommentti;
    }

}
