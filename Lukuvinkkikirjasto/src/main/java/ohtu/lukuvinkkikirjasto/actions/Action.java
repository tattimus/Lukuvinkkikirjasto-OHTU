/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.actions;

import ohtu.lukuvinkkikirjasto.IO.IO;

/**
 *
 * @author jaakko
 */
public abstract class Action implements Comparable<Action> {
    public int getPriority() { 
        return 0;
    }
    public abstract String getHint();
    public abstract void run(IO io);
    
    @Override
    public final int compareTo(Action other) {
        return Integer.compare(getPriority(), other.getPriority());
    }
}
