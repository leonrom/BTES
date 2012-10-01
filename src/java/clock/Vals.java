/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leon
 */
public class Vals {
    private static final boolean isDB = true;
    private static final Random random = new Random();
    private double P0 = 0;
    private double PW = 0;
    private double dP = 0;
    private double dA = 0;
    private double PZ = 0;
    private Date sT;
    private double Pp = 0;
    private double Wu = 0;
    private double Ww = 0;
    private double Po = 0;
    private double Pr = 0;
    private double Pw = 0;
    ArrayList<Double> aA = new ArrayList<Double>();
    private static final Logger log = Logger.getLogger(Vals.class.getName());
    private Connection conO = null, conS = null;
    private Statement sRs = null, sRm = null, sOm = null;

    public Vals() {
        log.log(Level.INFO, null, "=== started ===");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to load oracle.jdbc.driver.OracleDriver" + e);
            return;
        }
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
        } catch (Exception e) {
            System.err.println("Unable to load org.firebirdsql.jdbc.FBDriver" + e);
            return;
        }
        conR_open();
    }

    private void ShowErr(SQLException ex, String txt) {
        log.log(Level.INFO, null, "=== errors ===");
        log.log(Level.SEVERE, null, txt);
        log.log(Level.SEVERE, null, "SQLException: " + ex.getMessage());
        log.log(Level.SEVERE, null, "SQLState: " + ex.getSQLState());
        log.log(Level.SEVERE, null, "VendorError: " + ex.getErrorCode());
    }

    private void conR_open() {
        try {            
            conO = DriverManager.getConnection("jdbc:oracle:thin:@"
                    + "(DESCRIPTION =(SOURCE_ROUTE = ASDUES)"
                    + "(ADDRESS_LIST =(ADDRESS = (PROTOCOL = tcp)(HOST = 10.11.254.24)(PORT = 1521))"
                    + "(ADDRESS = (PROTOCOL = tcp)(HOST = 10.11.254.26)(PORT = 1521)))"
                    + "(CONNECT_DATA =(SERVICE_NAME = ASDUES.ZES.LOCAL)))", "OIK", "oik");
        } catch (SQLException ex) {
            ShowErr(ex, "conR_open -------------- 10.11.254.24 ?");
        }  
        try {
            conO = DriverManager.getConnection("jdbc:oracle:thin:@"
                    +"(DESCRIPTION ="
                        +    "(ADDRESS = (PROTOCOL = tcp)(HOST = 10.11.254.24)(PORT = 1521))"
                        +    "(ADDRESS = (PROTOCOL = tcp)(HOST = 10.11.254.26)(PORT = 1521))"
                        +"(CONNECT_DATA ="
                        +"(SERVER = DEDICATED)"
                        +"(SERVICE_NAME = ASDUES.ZES.LOCAL)"
                        +"))");
        } catch (SQLException ex) {
            ShowErr(ex, "conR_open -------------- 10.11.254.26 ?");
        }
        try {
            conS = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//10.11.246.58:1521/XE", "RC", "rc");
        } catch (SQLException ex) {
            ShowErr(ex, "conR_open -------------- 10.11.246.58 ?");
        }
        
        try {
            conS.setReadOnly(true);
            sRs = conS.createStatement();
            sRm = conS.createStatement();
        } catch (SQLException ex) {
            ShowErr(ex, "conR_open --------------?");
        }
    }

    private boolean getSecondsD(Date t) {
        if (sRs == null) {
            return false;
        }
        Date timSec = new java.util.Date(t.getTime() - 1000 * 180);

        log.log(Level.FINE, "readSecB");
        String sql;
        sql = "SELECT  timmark"
                + ",P0,PW,dP"
                + " FROM secarch"
                + " WHERE timmark > TO_DATE('" + Cnst.dateformat.format(timSec.getTime()) + "','YYYY-MM-DD HH24:MI:SS')"
                + " AND omd>0"
                + " AND rownum <= " + Cnst.fmi.format(Cnst.nusred)
                + " ORDER BY timmark DESC";
        try {
            ResultSet rs = sRs.executeQuery(sql);
            int k = 0, n;
            while (rs.next()) {
                timSec = rs.getTimestamp("timMark");
                String tim = timSec.toString();
                Date dtB = timSec;
                long L = dtB.getTime();

                double dp = rs.getFloat("dP");
                if (k++ == 0) {
                    dP = dp;
                    P0 = rs.getFloat("P0");
                    PW = rs.getFloat("PW");
                }
                n = aA.size();
                if (n == Cnst.nusred) {
                    aA.remove(0);
                }
                aA.add(dp);
            }
            rs.close();

            double d = 0;
            n = aA.size();
            for (Iterator<Double> iter = aA.iterator(); iter.hasNext();) {
                d += iter.next();
            }
            if (n > 0) {
                dA = d / n;
            } else {
                dA = 999;
            }
        } catch (SQLException ex) {
            ShowErr(ex, "readSecB");
            return false;
        }
        return true;
    }

// sec: seconds isNewTime timeStr dP PW
    private void getSecondsR() {
        dP = 60 * (random.nextDouble() - 0.5);
        dA = dP * 0.5;
        PW = 495 + 10 * random.nextDouble();
        PZ = 495 + 10 * random.nextDouble();
        sT = new Date();
    }

    private long calcCurTimSecond(Date t) {
        long js = sT.getTime() / 1000;
        long ns = js - (js % Cnst.nusred) + (Cnst.nusred / 2);
        return (ns);
    }

    public String getSeconds(Date t) {
        if (isDB) {
            if (!getSecondsD(t)) {
                return ("?");
            }
        } else {
            getSecondsR();
        }
        long ns = calcCurTimSecond(t);
        return (Cnst.timeformat.format(t) + " " + Cnst.fmt.format(dP) + " "
                + Cnst.fmt.format(dA) + " " + Cnst.fmt.format(PW) + " " + Cnst.fmt.format(PZ) + " "
                + Cnst.fmi.format(ns)); // номер секунды, к которой приписывается усреднение

    }

    public String getMinutes() {
        Pp = 495 + 10 * random.nextDouble();
        Wu = 495 + 10 * random.nextDouble();
        Ww = 495 + 10 * random.nextDouble();
        Po = 495 + 10 * random.nextDouble();
        Pr = 495 + 10 * random.nextDouble();
        Pw = 495 + 10 * random.nextDouble();
        sT = new Date();
        return (Cnst.timeformat.format(sT) + " "
                + Cnst.fmt.format(Pp) + " " + Cnst.fmt.format(Wu) + " " + Cnst.fmt.format(Ww) + " "
                + Cnst.fmt.format(Po) + " " + Cnst.fmt.format(Pr) + " " + Cnst.fmt.format(Pw) + " "
                + Cnst.fmt.format(100 + Pp) + " " + Cnst.fmt.format(100 + Wu) + " " + Cnst.fmt.format(100 + Ww) + " "
                + Cnst.fmt.format(100 + Po) + " " + Cnst.fmt.format(100 + Pr) + " " + Cnst.fmt.format(100 + Pw));
    }

    public String getSecOld(Date dt) {
        sT = new Date();
        long ns = calcCurTimSecond(sT);
        String s = Cnst.fmi.format(ns) + " ";
        for (int i = 0; i < Cnst.nPoints; i++) {
            dP = 60 * (random.nextDouble() - 0.5);
            s += Cnst.fmt.format(dP) + " ";
        }
        return (s);
    }

    /**
     *
     * @param dt
     * @return строку архива часовых (минутных) наборов значений
     */
    public String getMinOld(Date dt) {
        String s = "";
        for (int k = 0; k < 24; k++) {
            for (int i = 0; i < 6; i++) {
                s += Cnst.fmi.format(k * 10 + i) + " ";
            }
        }
        return (s);
    }
}
