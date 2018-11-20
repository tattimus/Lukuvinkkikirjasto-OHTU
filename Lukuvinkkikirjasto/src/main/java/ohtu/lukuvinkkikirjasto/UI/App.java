/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.UI;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.actions.Action;

/**
 *
 * @author kape
 */
public class App extends Thread {
    private IO io;
    private Map<Integer, Action> actions;
    
    private boolean running = false;

    public App(IO io, Action... actions) {
        this.io = io;
        this.actions = new HashMap<>(actions.length + 1);
        
        Action[] actionsWithQuit = new Action[actions.length + 1];
        System.arraycopy(actions, 0, actionsWithQuit, 0, actions.length);
        actionsWithQuit[actionsWithQuit.length - 1] = new Action() {
            @Override
            public int getPriority() {
                return Integer.MIN_VALUE;
            }
            
            @Override
            public String getHint() {
                return "Lopeta";
            }

            @Override
            public void run(IO io) {
                App.this.running = false;
            }
        };
        
        Arrays.sort(actionsWithQuit, Comparator.reverseOrder());
        for (int i = 0; i < actionsWithQuit.length; i++) {
            this.actions.put(i + 1, actionsWithQuit[i]);
        }
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            printActions();
            
            int action = io.readInt("Valitse komento: ");
            
            if (!actions.containsKey(action)) {
                io.printLine("Virheellinen komento: "+action);
                continue;
            }
            
            io.printLine("");
            actions.get(action).run(io);
        }

    }
    
    private void printActions() {
        io.printLine("\nKäytössä olevat komennot:");
        actions.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(e -> e.getKey()))
                .forEach(e -> {
                    io.printLine("\t("+e.getKey()+") "+e.getValue().getHint());
                });
    }
    
    //Helper method for testing, finds the action id for given action hint
    //Returns -1 if the action was not found
    public int findAction(String actionHint) {
        return actions.entrySet()
                .stream()
                .filter(e -> e.getValue().getHint().equals(actionHint))
                .findAny()
                .map(e -> e.getKey())
                .orElse(-1);
    }
}
