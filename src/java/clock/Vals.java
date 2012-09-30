/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clock;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Leon
 */
public class Vals {

    private static final int isDB = 0;
    private static final Random random = new Random();
    private double dP = 0;
    private double PW = 0;
    private double PZ = 0;
    private Date   sT ;    
    
    private double Pp = 0;
    private double Wu = 0;
    private double Ww = 0;
    private double Po = 0;
    private double Pr = 0;
    private double Pw = 0;
    ArrayList<Double> dA = new ArrayList<Double>();
    
    public Vals()
    {        
    }
    
// sec: seconds isNewTime timeStr dP PW
    private void getSecondsR() {
        dP = 60 * (random.nextDouble() - 0.5);
        PW = 495 + 10 * random.nextDouble();        
        PZ = 495 + 10 * random.nextDouble();
        sT = new Date();
    }
    
    private void getSecondsD() {
        dP = 0;
        PW = 500;        
        PZ = 511;
        sT = new Date();        
    }
    
    private long calcCurTimSecond(Date t) {
        long js = sT.getTime() / 1000;
        long ns = js - (js % Cnst.nusred) + (Cnst.nusred / 2);
        return (ns);
    }
    
    /**
     *
     * @param nusred
     * @return
     */
    public String getSeconds() {        
        if (isDB > 0) {
            getSecondsD();
        } else {
            getSecondsR();
        }      
//        String d = timeformat.format(dt.getTime());
        int n = dA.size();
        if (n == Cnst.nusred){
            dA.remove(0);
        }
        dA.add(dP);
        double d = 0;   
        for (Iterator<Double> iter = dA.iterator(); iter.hasNext(); ) {
            d += iter.next();
        }
        d = d /n;     
        long ns = calcCurTimSecond(sT);
        return (Cnst.timeformat.format(sT) + " " + Cnst.fmt.format(dP) + " " + 
                Cnst.fmt.format(d) + " " + Cnst.fmt.format(PW) + " " + Cnst.fmt.format(PZ) + " " +
                Cnst.fmi.format(ns)); // номер секунды, к которой приписывается усреднение
    }    
    
    public String getMinutes() { 
        Pp = 495 + 10 * random.nextDouble(); 
        Wu = 495 + 10 * random.nextDouble();  
        Ww = 495 + 10 * random.nextDouble();  
        Po = 495 + 10 * random.nextDouble(); 
        Pr = 495 + 10 * random.nextDouble(); 
        Pw = 495 + 10 * random.nextDouble();               
        sT = new Date();
        return (Cnst.timeformat.format(sT) + " " + 
                Cnst.fmt.format(Pp) + " " + Cnst.fmt.format(Wu) + " " + Cnst.fmt.format(Ww) + " " +
                Cnst.fmt.format(Po) + " " + Cnst.fmt.format(Pr) + " " + Cnst.fmt.format(Pw) + " " +
                Cnst.fmt.format(100+Pp) + " " + Cnst.fmt.format(100+Wu) + " " + Cnst.fmt.format(100+Ww) + " " +
                Cnst.fmt.format(100+Po) + " " + Cnst.fmt.format(100+Pr) + " " + Cnst.fmt.format(100+Pw) 
                );
    }
    
    public String getSecOld(Date dt) {             
        sT = new Date();   
        long ns = calcCurTimSecond(sT);
        String s = Cnst.fmi.format(ns) + " ";
        for (int i = 0; i < Cnst.nPoints; i++){            
            dP = 60 * (random.nextDouble() - 0.5);
            s += Cnst.fmt.format(dP) + " ";
        }
        return (s);
    }
                   
    public String getMinOld(Date dt){    
        String s = "";
        for (int k = 0; k < 24; k++){
            for (int i = 0; i < 6; i++){            
                s += Cnst.fmi.format(k*10+i) + " ";
            }
        }
        return (s);        
    }
}
