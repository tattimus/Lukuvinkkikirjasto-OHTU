/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.requests;

import ohtu.lukuvinkkikirjasto.dao.HintDAO;
import ohtu.lukuvinkkikirjasto.hint.HintClass;

/**
 *
 * @author kape
 */
public class AddHint implements CreateRequest{
    
    private HintDAO hdao;
    private HintClass hint;
    
    public AddHint(HintDAO hdao) {
        this.hdao=hdao;
    }

    @Override
    public String createHint(String title, String comment) {
        int id=0;
        this.hint=new HintClass(null, title, comment);
        try{
            id=hdao.insert(hint);
        } catch(Exception e) {
            System.out.println(e);
        }
        return "Hint "+ title + " with commment " + comment+ " is created with id " + id ;
        
    }
    
}
