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

    public CommandLineIO() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void printLine(String printable) {
        System.out.println(printable);
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        int i = scanner.nextInt();
        return i;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String s = scanner.next();
        return s;
    }

    public void printList(ArrayList<Hint> hints) {
        for (Hint h : hints) {
            System.out.println(h.getTitle() + " " + h.getComment());
        }
    }

    @Override
    public String readList(String prompt) {
        System.out.println(prompt);
        String s="";
        while(s.length()==0) {
            s=scanner.nextLine();
        }
//        while (scanner.hasNext()) {
//            String s = scanner.next();
//            System.out.println(s);
//            palautettava.add(s);
//        }
        return s;

    }

}
