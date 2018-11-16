/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.UI;

import java.util.ArrayList;
import ohtu.lukuvinkkikirjasto.IO.IO;
import ohtu.lukuvinkkikirjasto.hint.Hint;
import ohtu.lukuvinkkikirjasto.requests.CreateRequest;
import ohtu.lukuvinkkikirjasto.requests.QueryRequest;

/**
 *
 * @author kape
 */
public class App {

    private IO io;
    private CreateRequest crequest;
    private QueryRequest qrequest;

    public App(IO io, CreateRequest crequest, QueryRequest qrequest) {
        this.io = io;
        this.crequest = crequest;
        this.qrequest = qrequest;
    }

    public void run() {
        int toiminto = 0;
        while (toiminto != 3) {
            io.printLine("My links and reads\n");
            toiminto = io.readInt("Select using numbers\n (1) Add an item to the list\n (2) List all items\n (3) quit\n");
            switch(toiminto) {
                case(1):
                    String feedback=addHint();
                    io.printLine(feedback);
                    break;
                case(2):
                    io.printList(qrequest.getAllRequests());
                    break;
                default:

            }

        }

    }

    private String addHint() {
        String title = io.readString("Insert title of the item");
        String comment = io.readString("Insert your comment regarding to the item");
        String newHint=crequest.createHint(title, comment);
        return newHint;
        

    }

}
