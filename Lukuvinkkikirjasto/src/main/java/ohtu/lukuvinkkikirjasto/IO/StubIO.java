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
public class StubIO implements IO {

    int[] input1 = new int[10];
    String[] input2 = new String[10];
    int indexer1;
    int indexer2;
    ArrayList<String> output;
    ArrayList<String> listOutput;


    public StubIO(int[] input1, String[] input2) {
        this.input1 = input1;
        this.input2 = input2;
        indexer1 = 0;
        indexer2 = 0;
        this.output = new ArrayList<>();
        this.listOutput=new ArrayList<>();

    }

    @Override
    public void printLine(String printable) {
        output.add(printable);
    }

    @Override
    public int readInt(String prompt) {
        int value = input1[indexer1++];

        return value;

    }

    @Override
    public String readString(String prompt) {
        String value = input2[indexer2++];
        return value;
    }

    @Override
    public void printList(ArrayList<Hint> hints) {
        for(Hint h: hints) {
            String s=h.getTitle()+ " " + h.getComment();
            this.listOutput.add(s);
        }
    }

    public ArrayList<String> getOutputs() {
        return this.output;
    }
    
    public ArrayList<String> getListOutputs() {
        return this.listOutput;
    }

}
