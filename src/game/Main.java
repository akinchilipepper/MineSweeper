package game;

import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
/**
 *
 * @author akinc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	System.setProperty("sun.java2d.uiScale", "1.0");
    	UIManager.put("Button.disabledText", new ColorUIResource(Color.CYAN));
        /* Create and display the form */
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameFrame().setVisible(true);
            }
        });
    }
    
}
