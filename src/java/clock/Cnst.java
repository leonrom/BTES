/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clock;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author Admin
 */
public class Cnst {
    public static int nPoints = 360;
    public static int nusred = 10;
    
    public static SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
    public static DecimalFormat fmt = new DecimalFormat("#0.0");
    public static DecimalFormat fmi = new DecimalFormat("#0");  
    
    public static void initEnv(){
        Locale locale = new Locale(("ru-RU"));
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(locale);
        dfs.setDecimalSeparator('.');
        fmt.setDecimalFormatSymbols(dfs);        
    }
}
