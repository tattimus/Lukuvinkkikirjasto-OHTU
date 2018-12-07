/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.lukuvinkkikirjasto.isbn;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 *
 * @author jaakko
 */
public class ISBNValidator {
    private static final Pattern REGEX = Pattern.compile("(\\d-?){13}");
    
    public static boolean validate(String isbn) {
        if (REGEX.matcher(isbn).matches()) {
            //https://en.wikipedia.org/wiki/International_Standard_Book_Number#ISBN-13_check_digit_calculation
            int sum = 0;
            int multiplier = 1;
            for (char c : isbn.replaceAll("-", "").toCharArray()) {
                sum = multiplier * Character.getNumericValue(c);
                
                if (multiplier == 3) {
                    multiplier = 1;
                } else if (multiplier == 1) {
                    multiplier = 3;
                }
            }
            
            boolean valid = sum % 10 == 0;
            
            return valid;
        } else {
            return false;
        }
    }
}
