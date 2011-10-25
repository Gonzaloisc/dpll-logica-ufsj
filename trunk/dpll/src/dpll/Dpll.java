/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dpll;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Rodrigo
 */
public class Dpll {
    public static String literais = "ABCDEFGHIJKLMNOPQRSTUVXWYZ┬┴";
    public static String ignore = "()¬";
    public static String operacoes = "Λ+→↔";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            
        }
        FormDPLL f = new FormDPLL();
        
        f.setVisible(true);
        
        // TODO code application logic here
    }
}
