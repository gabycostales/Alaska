import java.awt.Color;
import java.util.*;
import javax.swing.UIManager;
import javax.swing.UIDefaults;

/**
    Prints a list of all the Swing defaults for the default
    look and feel.
*/
public class SwingDefaults {

    public static void main(String[] args) throws Exception {

        List allValues = new ArrayList();

        // store all the values in a list
        UIDefaults defaults = UIManager.getDefaults();
        Enumeration e = defaults.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            Object value = defaults.get(key);
            if (value instanceof Color) {
                allValues.add(key + " = " + value);
            }
        }

        // sort and print the values
        Collections.sort(allValues);
        for (int i=0; i<allValues.size(); i++) {
            System.out.println(allValues.get(i));
        }

    }
}
