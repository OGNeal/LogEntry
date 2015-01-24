import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Tests the <code>LogEntry</code> class.
 * 
 * @author Michael Arkin
 * @version 1.0 2013-10-07
 */
public class LogEntryTester
{
    private final static String LOG_FILE = "logfile.text";

    public static void main(String[] argument)
    {
        // Establish a connection to the log file.
        BufferedReader logFile = null;
        try
        {
            logFile = new BufferedReader(new FileReader(LOG_FILE));
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("Yikes! Couldn't find " + LOG_FILE + "\".");
            System.out.println("I'm toast!");
            System.out.println(-1);
        } // end of catch (FileNotFoundException exception)

        // Read from the log file. Create and display log entries.
        try
        {
            String rawData = logFile.readLine();

            while (rawData != null)
            {
                LogEntry entry = new LogEntry(rawData);
                System.out.println(entry);
                rawData = logFile.readLine();
            } // while (rawData != null)

            logFile.close();
        }
        catch (IOException exception)
        {
            System.out.println("Yikes! There was a problem with " + LOG_FILE 
                + "\".");
            System.out.println("I'm toast!");
        } // end of catch (IOException exception)

    } // end of method main(String[] argument)

} // end of class public class LogEntryTester