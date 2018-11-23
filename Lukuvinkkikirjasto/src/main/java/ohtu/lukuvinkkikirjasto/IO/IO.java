/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.IO;

import java.util.ArrayList;
import ohtu.lukuvinkkikirjasto.hint.Hint;

/**
 *
 * @author kape
 */
public interface IO {
    void printLine(String printable);
    int readInt(String prompt);
    String readString(String prompt);
    String readLine(String prompt);
    void printList(ArrayList<Hint> hints);
    default void printLine(Object object) {
        printLine(object.toString());
    }
}
