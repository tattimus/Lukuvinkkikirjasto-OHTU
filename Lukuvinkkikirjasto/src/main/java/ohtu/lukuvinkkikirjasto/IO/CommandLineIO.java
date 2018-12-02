/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.IO;

import java.util.ArrayList;
import java.util.Scanner;
import ohtu.lukuvinkkikirjasto.hint.Hint;

/**
 *
 * @author kape
 */
public class CommandLineIO implements IO {

    private Scanner scanner;
    private Scanner lineScanner;

    public CommandLineIO() {
        scanner = new Scanner(System.in);
        lineScanner=new Scanner(System.in);
    }

    @Override
    public void printLine(String printable) {
        System.out.println(printable);
    }

 @Override
    public int readInt(String prompt) {
        System.out.println(prompt);

        String string = scanner.nextLine();
        if (Integer.getInteger(string) != null) {
            return Integer.getInteger(string);
        } else {
            System.out.println("Syötteen on oltava merkkijono");
            return -1;
        }

    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String s = lineScanner.nextLine();
        return s;
    }

    public void printList(ArrayList<Hint> hints) {
        for (Hint h : hints) {
            System.out.println(h.getTitle() + " " + h.getComment());
        }
    }

}
