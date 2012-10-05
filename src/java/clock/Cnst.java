/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clock;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author Admin
 */
public class Cnst {

    public static int nPoints = 360;
    public static int nusred = 10;
    public static SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    public static DecimalFormat fmt = new DecimalFormat("#0.0");
    public static DecimalFormat fmi = new DecimalFormat("#0");

    private static void InitCnf() {

        Properties prop = new Properties();
        try {
            //load a properties file
            prop.load(new FileInputStream("/config/btes.properties"));

            Cnst.nPoints = Integer.parseInt(prop.getProperty("nPoints", "360"));
            Cnst.nusred = Integer.parseInt(prop.getProperty("nusred", "10"));

        } catch (IOException ex) {
            System.err.println("? 1 Init() :" + ex);
            System.out.println("? 2 Init() :" + ex);
        }
    }

    public static void initEnv() {
        Locale locale = new Locale(("ru-RU"));
//        Locale.setDefault(Locale.ENGLISH);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(locale);
        dfs.setDecimalSeparator('.');
        fmt.setDecimalFormatSymbols(dfs);
        InitCnf();
    }
}