/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.vinkki;

/**
 *
 * @author y50u
 */
public class VinkkiClass implements Vinkki {

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
    private String otsikko;
    private String kommentti;
}
