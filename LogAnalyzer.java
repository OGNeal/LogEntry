import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.NumberFormatException;
import java.util.Calendar;

/**
 * An analyzer that analyzes a log file.
 * 
 * @author Neal Sun
 * @version 1.0 2013-10-22
 */
public class LogAnalyzer
{
   // class fields
   private static final Calendar CALENDAR = Calendar.getInstance();
   private static final int CURRENT_DAY = CALENDAR.get(CALENDAR.DATE);
   private static final int CURRENT_HOUR = CALENDAR.get(CALENDAR.HOUR);
   private static final int CURRENT_MINUTE = CALENDAR.get(CALENDAR.MINUTE);
   private static final int CURRENT_MONTH = CALENDAR.get(CALENDAR.MONTH) + 1;
   private static final int CURRENT_YEAR = CALENDAR.get(CALENDAR.YEAR);
   private static final int DAY_IN_A_MONTH_ODD = 31;
   private static final int DAY_IN_A_MONTH_EVEN = 30;
   private static final int DAY_IN_A_MONTH_FEB_ODD = 29;
   private static final int DAY_IN_A_MONTH_FEB_EVEN = 28;
   private static final int DAY_MIN = 1;
   private static final int ERROR_IOEXCEPTION = -1;
   private static final int ERROR_FILE_NOT_FOUND_EXCEPTION = -2;
   private static final LogEntry EARLIEST_ENTRY = new LogEntry("1961 01 01 00 00");
   private static final int HOUR_IN_A_DAY = 24;
   private static final int INVALID_ENTRY_VALUE = -1;
   private static final int LOG_ENTRY_COMPONENTS = 5;
   private static final int MINUTE_IN_AN_HOUR = 60;
   private static final String TEXT_INVALID = "Log entry invalid. ";
   private static final String TEXT_NOT_IN_ORDER = "Log entry not in order. ";
   private static final int TIME_MIN = 0;
   private static final int YEAR_MIN = 1961;

   // instance fields
   private int countException;
   private int countLog;
   private int countVerified;
   private String fileLog;
   private String fileVerified;
   private String fileExceptions;
   private boolean hasBeenVerified;
   private int[] hourlyCounts = new int[HOUR_IN_A_DAY];

   /**
    * Constructs a log analyzer which accepts three strings containing filenames: 
    * one for the name of the log to be verified, one for the name 
    * of the verified file, and one for the name of the exceptions file.
    * 
    * @param logFile the name of the log file
    * @param verifyedLogs the name of the verifyed logs file
    * @param exceptionFile the name of the exception file
    */
   public LogAnalyzer(String file, String fileVerified, String fileExceptions)
   {
      fileLog = file;
      this.fileVerified = fileVerified;
      this.fileExceptions = fileExceptions;

      hasBeenVerified = false;
      verifyLog();

      countLog = getFileLineNumber(fileLog);
      countVerified = getFileLineNumber(this.fileVerified);
      countException = getFileLineNumber(this.fileExceptions);

   } // end of constructor LogAnalyzer(String file, String fileVerified, ...

   /**
    * This method displays each entry in the exceptions file with its corresponding
    * entry number, followed—on a separate line—by a description of the error 
    * which triggered the exception.
    */
   public void displayExceptions()
   {
      try
      {
         BufferedReader reader = new BufferedReader(new FileReader(fileExceptions));
         System.out.println(reader.readLine());
         reader.close();
      }
      catch (FileNotFoundException exception)
      {
         System.err.println("File not found!");
         System.exit(ERROR_FILE_NOT_FOUND_EXCEPTION);
      } // end of catch (FileNotFoundException exception)
      catch (IOException exception)
      {
         System.err.println("Yikes! There was a problem!");
         System.err.println("It's not your fault.");
         System.exit(ERROR_IOEXCEPTION);
      } // end of catch (IOException exception)
   } // end of method displayExceptions()

   /**
    * This method displays each entry in the verified file 
    * with its corresponding entry number.
    */
   public void displayVerifiedEntries()
   {
      try
      {
         BufferedReader reader = new BufferedReader(new FileReader(fileVerified));
         System.out.println(reader.readLine());
         reader.close();
      }
      catch (FileNotFoundException exception)
      {
         System.err.println("File not found!");
         System.exit(ERROR_FILE_NOT_FOUND_EXCEPTION);
      } // end of catch (FileNotFoundException exception)
      catch (IOException exception)
      {
         System.err.println("Yikes! There was a problem!");
         System.err.println("It's not your fault.");
         System.exit(ERROR_IOEXCEPTION);
      } // end of catch (IOException exception)
   } // end of method displayVerifiedEntries()

   /**
    * This accessor return the count of entries in the exception file.
    * 
    * @return the count of entries in the exception file
    */
   public int getExceptionCount()
   {
      return countException;
   } // end of method getExceptionCount()

   /**
    * This accessor returns an array of file names in order as follows: 
    * log file, verified file, exceptions file.
    * 
    * @return the array of the names of the files
    */
   public String[] getFileNames()
   {
      return new String[]{fileLog, fileVerified, fileExceptions};
   } // end of method getFileNames()

   /**
    * This accessor returns the count of entries in the log file.
    * 
    * @return the count of entries in the log file
    */
   public int getLogCount()
   {
      return countLog;
   } // end of method getLogCount()

   /**
    * This accessor returns the count of entries in the verified file.
    * 
    * @return the count of entries in the verified file
    */
   public int getVerifiedCount()
   {
      return countVerified;
   } // end of method getVerifiedCount()

   /**
    * This boolean method reveals whether the analyzer has verified its log file.
    * 
    * @return wheather the log file has been verified
    */
   public boolean hasBeenVerified()
   {
      return hasBeenVerified;
   } // end of method hasBeenVerified()

   /**
    * This method reads each line in the log file and writes valid entries to a verified 
    * file and rejected entries to an exceptions file. For each rejected entry, the method 
    * will write a separate line describing the error which triggered the exception. Once 
    * verifyLog has been called, the analyzer knows the number of entries in all three files.
    */
   public void verifyLog()
   {
      try
      {
         BufferedReader reader = new BufferedReader(new FileReader(fileLog));
         BufferedWriter writerVerified = new BufferedWriter(new FileWriter(fileVerified)); 
         BufferedWriter writerExceptions = new BufferedWriter(new FileWriter(fileExceptions)); 

         LogEntry logEntry;
         LogEntry previousEntry = EARLIEST_ENTRY;
         String fileText = reader.readLine();
         while (fileText != null)
         {
            logEntry = new LogEntry(fileText);

            if (validateLogEntry(logEntry))
            {
               if (checkOrderLogEntry(logEntry, previousEntry))
               {
                  writerVerified.write(fileText + "\n");
                  previousEntry = logEntry;
                  hourlyCounts[logEntry.getHour()]++;
               }
               else
               {
                  writerExceptions.write(fileText + " ");
                  writerExceptions.write(TEXT_NOT_IN_ORDER + "\n");
               }
            }
            else
            {
               writerExceptions.write(fileText + " ");
               writerExceptions.write(TEXT_INVALID + "\n");
            } // emd of if (validateLogEntry(logEntry))

            fileText = reader.readLine();
         } // end of while (fileText != null)

         reader.close();
         writerVerified.close();
         writerExceptions.close();

         hasBeenVerified = true;
      }
      catch (FileNotFoundException exception)
      {
         System.err.println("File not found!");
         System.exit(ERROR_FILE_NOT_FOUND_EXCEPTION);
      } // end of catch (FileNotFoundException exception)
      catch (IOException exception)
      {
         System.err.println("Yikes! There was a problem!");
         System.err.println("It's not your fault.");
         System.exit(ERROR_IOEXCEPTION);
      } // end of catch (IOException exception)
   } // end of method verifyLog()

   private static LogEntry[] bubbleSort(LogEntry[] array)
   {
      for (int i = array.length - 1; i > 0; i--)
      {
         for (int j = 0; j < i; j++)
         {
            if (array[j].compareTo(array[j + 1]) > 0)
            {
               LogEntry temp = array[j];
               array[j] = array[j + 1];
               array[j + 1] = temp;
            } // end of if (array[j].compareTo(array[j + 1]) > 0)
         } // end of for (int j = 0; j < i; j++)
      } // end of for (int i = array.length - 1; i > 0; i--)
      
      return array;
   } // end if method bubbleSort(LogEntry[] array)

   private int getFileLineNumber(String file)
   {
      LineNumberReader console = null;

      try
      {
         console = new LineNumberReader(new FileReader(file));
         String text = ""; 

         do
         {
            text = console.readLine();
         }
         while (text != null);
      }
      catch (FileNotFoundException exception)
      {
         System.err.println("File not found!");
         hasBeenVerified = false;
         System.exit(ERROR_FILE_NOT_FOUND_EXCEPTION);
      } // end of catch (FileNotFoundException exception)
      catch (IOException exception)
      {
         System.err.println("Yikes! There was a problem!");
         System.err.println("It's not your fault.");
         hasBeenVerified = false;
         System.exit(ERROR_IOEXCEPTION);
      } // end of catch (IOException exception)

      return console.getLineNumber();
   } // end of method getFileLineNumber()

   private int[] getHourlyCounts()
   {
      return hourlyCounts;
   } // end of method getHourlyCounts()

   private static boolean checkOrderLogEntry(LogEntry logEntry, LogEntry previousEntry)
   {
      if (logEntry.compareTo(previousEntry) >= 0) return true;

      return false;
   } // end of method checkOrderLogEntry(LogEntry logEntry, LogEntry previousEntry)

   private static boolean validateLogEntry(LogEntry logEntry)
   {
      int year = logEntry.getYear();
      int month = logEntry.getMonth();
      int day = logEntry.getDay();
      int hour = logEntry.getHour();
      int minute = logEntry.getMinute();

      if (year < YEAR_MIN || year > CURRENT_YEAR) return false;

      if (year == CURRENT_YEAR && month > CURRENT_MONTH) return false;

      if (month < CALENDAR.JANUARY + 1 || month > CALENDAR.DECEMBER + 1) return false;

      if (year == CURRENT_YEAR && month == CURRENT_MONTH && day > CURRENT_DAY) return false;

      int dayInAMonth = 0;
      switch (month)
      {
         case 1:
         case 3:
         case 5:
         case 7:
         case 8:
         case 10:
         case 12:
         dayInAMonth= DAY_IN_A_MONTH_ODD;
         break;

         case 4:
         case 6:
         case 9:
         case 11:
         dayInAMonth= DAY_IN_A_MONTH_EVEN;
         break;

         case 2:
         if (year % 4 != 0)
         {
            dayInAMonth= DAY_IN_A_MONTH_FEB_EVEN;
         }
         else
         {
            dayInAMonth= DAY_IN_A_MONTH_FEB_ODD;
         } // end of if (year % 4 != 0)
         break;

         default:
         break;
      } // end of switch (month - 1)

      if (day < DAY_MIN || day > dayInAMonth) return false;

      if (year == CURRENT_YEAR && month == CURRENT_MONTH && day == CURRENT_DAY && 
      hour > CURRENT_HOUR) return false;

      if (hour < TIME_MIN || hour > HOUR_IN_A_DAY) return false;

      if (year == CURRENT_YEAR && month == CURRENT_MONTH && day == CURRENT_DAY && 
      hour == CURRENT_HOUR && minute > CURRENT_MINUTE) return false;

      if (minute < TIME_MIN || minute > MINUTE_IN_AN_HOUR) return false;

      return true;
   } // end of method validateLogEntry(LogEntry logEntry)
} // end of class LogEntryAnalyzer