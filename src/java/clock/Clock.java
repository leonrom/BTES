/*
 * 
 */
package clock;

import filter.ContextPathRequestFilter;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
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
    private static final Logger log=Logger.getLogger(Clock.class.getName());

    private static final Vals vals = new Vals();
    private ServerContext sctx;
    long jdto;
    boolean isNew = false;
    boolean isPage = false;
    
    public Clock(int a) {
        log.info("===================================  Init Clock(int a)");
        Cnst.initEnv();
    }

    public Clock() {
        log.info("===================================  Init Clock()");
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
        log.info("! connected");
        if (!isPage) {
            new Thread(this).start();
            isPage = true;
            log.info("do Thread(this).start()");
        }
        isNew = true;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            log.info(" do run()");
            String path = "/index.html";
            
            while (isPage) {
                String rcp = ContextPathRequestFilter.getContextPath();
                String rp = rcp + path;

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
                        //                myLog.log(Level.WARNING, "rp = '" + rp + "'");
                        log.log(Level.INFO, "currMinute = ''{0}''", sM);
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
//                    log.log(Level.INFO, "Sent message{0}", pages.toString());
                    Thread.sleep(300);
                }
            }

            Collection sessions = sctx.getScriptSessionsByPage(ContextPathRequestFilter.getContextPath() + path);
            Util pages = new Util(sessions);
            pages.setValue("currSecond", "усё");

        } catch (InterruptedException ex) {
            log.warning("CLOCK: InterruptedException");
        }
    }
}