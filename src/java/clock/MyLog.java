package clock;

/**
 * Created with IntelliJ IDEA.
 * User: leonid.romanov
 * Date: 28.08.12
 * Time: 10:14
 */
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLog {
    static final Logger logger = Logger.getLogger("MyLog");
    public MyLog(String path) {

       FileHandler fh;
//        logger = Logger.getLogger("MyLog");
        try {     // This block configure the logger with handler and formatter
         fh = new FileHandler(path, true);
         logger.addHandler(fh);
         logger.setLevel(Level.ALL);
         SimpleFormatter formatter = new SimpleFormatter();
         fh.setFormatter(formatter);

       } catch (SecurityException e) {
//         e.printStackTrace();
       } catch (IOException e) {
//         e.printStackTrace();
       }
     }

    public void log(Level warning, String s) {
        logger.log(warning, s);
    }
}
