/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.IO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.hint.Hint;

/**
 *
 * @author jaakko
 */
public class AsyncStubIO implements IO {
    private List<String> output = new ArrayList<>();
    private BlockingQueue<Optional<String>> stringQueue = new LinkedBlockingQueue();
    private BlockingQueue<Optional<Integer>> intQueue = new LinkedBlockingQueue();
    
    public void pushString(String string) throws InterruptedException {
        stringQueue.put(Optional.ofNullable(string));
    }
    public void pushInt(int i) throws InterruptedException {
        intQueue.put(Optional.ofNullable(i));
    }
    
    @Override
    public void printLine(String printable) {
        output.add(printable);
    }

    @Override
    public int readInt(String prompt) {
        printLine(prompt);
        try {
            return intQueue.poll(5, TimeUnit.MINUTES).orElse(null);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String readString(String prompt) {
        printLine(prompt);
        try {
            return stringQueue.poll(5, TimeUnit.MINUTES).orElse(null);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void printList(ArrayList<Hint> hints) {
        hints.stream().map(h -> h.toString()).forEach(this::printLine);
    }
    
    public List<String> getOutput() {
        return output;
    }

}
