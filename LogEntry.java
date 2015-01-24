import java.util.StringTokenizer;
import java.lang.NumberFormatException;
import java.util.NoSuchElementException;

/**
 * Creates a log entry displaying the date. 
 * 
 * @author Neal Sun
 * @version 1.0 2013-10-07
 */
public class LogEntry
{
   // class constant
   private static final int INVALID_ENTRY = -1;

   // instance fields
   private int year;
   private int month;
   private int day;
   private int hour;
   private int minute;

   // constructors
   /**
    * Constructs a log entry.
    * 
    * @param logEntry - a single string containing all of the data
    * for a log entry
    */
   public LogEntry(String logEntry)
   {
      // Convert string logEntry into 5 integers. 
      StringTokenizer tokens = new StringTokenizer(logEntry);

      day = INVALID_ENTRY;
      hour = INVALID_ENTRY;
      minute = INVALID_ENTRY;
      month = INVALID_ENTRY;
      year = INVALID_ENTRY;

      try
      {
         year = Integer.parseInt(tokens.nextToken());
         month = Integer.parseInt(tokens.nextToken());
         day = Integer.parseInt(tokens.nextToken());
         hour = Integer.parseInt(tokens.nextToken());
         minute = Integer.parseInt(tokens.nextToken());
      }
      catch (NumberFormatException exception)
      {
         System.err.println(logEntry + ": The entry is not numeric. ");
      } // end of catch (NumberFormatException exception).
      catch (NoSuchElementException exception)
      {
         System.err.println(logEntry + ": The entry doesn't have enough tokens. ");
      } // end of catch (NoSuchElementException exception).
   } // end of LogEntry(string LogEntry)

   /**
    * Constructs a log entry.
    * 
    * @param year - the year of this log entry
    * @param month - the month of this log entry
    * @param day - the day of this log entry
    * @param hour - the hour of this log entry
    * @param minute - the minute of this log entry
    */
   public LogEntry(int year, int month, int day, int hour, int minute)
   {
      this.year = year;
      this.month = month;
      this.day = day;
      this.hour = hour;
      this.minute = minute;
   } // end of LogEntry(int year, int month, int day, int hour, int minute)

   /**
    * Compares this log entry with another one. 
    * 
    * @param anotherLogEntry - another log entry to compare with
    * @return value 0 if two log entries are same, 
    *  1 if this log entry goes first, 
    *  -1 if this log entry goes later
    */
   public int compareTo(LogEntry anotherLogEntry)
   {
      return this.toString().compareTo(anotherLogEntry.toString());
   } // end of method compareTo(LogEntry logEntry)

   /**
    * Returns the year of the log entry 
    * 
    * @return the year of this log entry 
    */
   public int getYear()
   {
      return year;
   } // end of method getYear()

   /**
    * Returns the month of the log entry 
    * 
    * @return the month of this log entry 
    */
   public int getMonth()
   {
      return month;
   } // end of method getMonth()

   /**
    * Returns the day of the log entry 
    * 
    * @return the day of this log entry 
    */
   public int getDay()
   {
      return day;
   } // end of method getDay()

   /**
    * Returns the hour of the log entry 
    * 
    * @return the hour of this log entry 
    */
   public int getHour()
   {
      return hour;
   } // end of method getHour()

   /**
    * Returns the minute of the log entry 
    * 
    * @return the minute of this log entry 
    */
   public int getMinute()
   {
      return minute;
   } // end of method getMinute()

   /**
    * Creates a string representation of this <code>LogEntry</code>. 
    * 
    * @return a string representing this <code>LogEntry</code>
    */
   public String toString()
   {
      String month;
      if (this.month < 10) month = "0" + this.month;
      else month = "" + this.month;
      
      String day;
      if (this.day < 10) day = "0" + this.day;
      else day = "" + this.day;
      
      String hour;
      if (this.hour < 10) hour = "0" + this.hour;
      else hour = "" + this.hour;
      
      String minute;
      if (this.minute < 10) minute = "0" + this.minute;
      else minute = "" + this.minute;
      
      
      return getClass().getName()
      + "["
      + "Year: " + year
      + " Month: " + month
      + " Day: " + day
      + " Hour: " + hour
      + " Minute: " + minute
      + " ]";
   } // end of method toString()
} // end of class LogEntry