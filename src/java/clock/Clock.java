/*
 * 
 */
package clock;

import filter.ContextPathRequestFilter;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;

/**
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class Clock implements Runnable {
    /**
     *
     */
    public static final Logger log=Logger.getLogger(Clock.class);
    private static final MyLog myLog = new MyLog("c:\\123.txt");
    private static final Vals vals = new Vals();
    private ServerContext sctx;
    long jdto;
    boolean isNew = false;
    boolean isPage = false;
    
    public Clock(int a) {
        log.info("Hello World!");
        myLog.log(Level.WARNING, "Init Clock(int a) 22");
        Cnst.initEnv();
    }

    public Clock() {
        log.info("Hello World!");
        myLog.log(Level.WARNING, "Init Clock()");
        Cnst.initEnv();
        ServletContext servletContext = WebContextFactory.get().getServletContext();
        sctx = ServerContextFactory.get(servletContext);
        jdto = -1; 
        isNew = false;
        isPage = false;
    }

    /**
     *
     */
    public synchronized void connect() {
        myLog.log(Level.WARNING, "   !!! connected");
        if (!isPage) {
            new Thread(this).start();
            isPage = true;
            myLog.log(Level.WARNING, "do Thread(this).start()");
        }
        isNew = true;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            myLog.log(Level.WARNING, " do run()");
//            String path = "/clock/index.html";
            String path = "/index.html";
            
            while (isPage) {
                String rcp = ContextPathRequestFilter.getContextPath();
                String rp = rcp + path;
//                myLog.log(Level.WARNING, "rp = '" + rp + "'");

                Date dt = new java.util.Date();
                long jdt = dt.getTime() / 1000;
                if (jdto != jdt){
                    Collection sessions = sctx.getScriptSessionsByPage(rp);
                    Util pages = new Util(sessions);
                    isPage = ! sessions.isEmpty();
                    
                    if (isNew) {
                        
                        String sSo;
                        sSo = vals.getSecOld(dt);
                        pages.setValue("archSecond", sSo);

                        String sMo = vals.getMinOld(dt);
                        pages.setValue("archMinute", sMo);

                        pages.setValue("initParams",
                            "." + // десятичный разделитель
                            "|" + // символ "не число"
                            " 1000" + // периодичность считывания
                            " " + Cnst.fmi.format(Cnst.nPoints) + // количество значащих точек на графике
                            " 5" + // к-во доп. точек на графике  (курсор)
                            " " + Cnst.fmi.format(Cnst.nusred) + // периодичность усреднения  
                            " 6"+  // доп.к-во неодачных (пустых) считываний секундных значений
                            " 60"  // диапазон показа Y на графике
                            );     
                    }

                    if (isNew || 
                        ((jdt % 60) == 0) || 
                        (((jdto + 60 + 5) < jdt) && ((jdto + 60 + 35) > jdt))
                        ) {
                        String sM = vals.getMinutes();
                        pages.setValue("currMinute", sM);
                        myLog.log(Level.WARNING, "currMinute = '" + sM + "'");
                    }

                    String sS;
                    sS = vals.getSeconds();
                    pages.setValue("currSecond", sS);
                    
                    isNew = false;
                    jdto = jdt;
                /*				
                 Util utilAll = new Util(sessions);
                 utilAll.removeAllOptions("chatlog");
                 utilAll.addOptions("chatlog", messages, "text");
				
                 */
//                    myLog.log(Level.WARNING, "Sent message" + pages.toString());
                    Thread.sleep(300);
                    log.info("------------- Hello World!");
                }
            }

            Collection sessions = sctx.getScriptSessionsByPage(ContextPathRequestFilter.getContextPath() + path);
            Util pages = new Util(sessions);
            pages.setValue("currSecond", "усё");

        } catch (InterruptedException ex) {
            myLog.log(Level.WARNING, "CLOCK: InterruptedException");
        }
    }
}