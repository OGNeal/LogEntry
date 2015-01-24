
/**
 * Write a description of class LogEntryAnalyzer here.
 * 
 * @author GivenName FamilyName
 * @version 1.0 yyyy-mm-dd
 */
public class LogEntryAnalyzer
{
    // class fields
    private static final Calendar CALENDAR = Calendar.getInstance();
    private static final int CURRENT_DAY = CALENDAR.get(CALENDAR.DATE);
    private static final int CURRENT_HOUR = CALENDAR.get(CALENDAR.HOUR);
    private static final int CURRENT_MINUTE = CALENDAR.get(CALENDAR.MINUTE);
    private static final int CURRENT_MONTH = CALENDAR.get(CALENDAR.MONTH) + 1;
    private static final int CURRENT_YEAR = CALENDAR.get(CALENDAR.YEAR);
    private static final int DAY_IN_A_MONTH1 = 31;
    private static final int DAY_IN_A_MONTH2 = 30;
    private static final int DAY_IN_A_MONTH3 = 29;
    private static final int DAY_IN_A_MONTH4 = 28;
    private static final int DAY_MIN = 1;
    private static final int HOUR_IN_A_DAY = 24;
    private static final int INVALID_ENTRY_VALUE = -1;
    private static final int LOG_ENTRY_COMPONENTS = 5;
    private static final int MINUTE_IN_AN_HOUR = 60;
    private static final int TIME_MIN = 0;
    private static final int YEAR_MIN = 1961;

    /**
     * Write a description of method main here.
     *
     * @param argument not used
     */
    public static void main(String[] argument)
    {

        String year = tokens.nextToken();
        String month = tokens.nextToken();
        String day = tokens.nextToken();
        String hour = tokens.nextToken();
        String minute = tokens.nextToken();

        this.year = validateYear(Integer.parseInt(year));
        this.month = validateMonth(this.year, Integer.parseInt(month));
        this.day = validateDay(this.year, this.month, 
            Integer.parseInt(day));
        this.hour = validateHour(this.year, this.month, this.day, 
            Integer.parseInt(hour));
        this.minute = validateMinute(this.year, this.month, this.day, 
            this.hour, Integer.parseInt(minute));

    } // end of main(String[] argument)

    private static int validateYear(int year)
    {
        if (year >= YEAR_MIN && year <= CURRENT_YEAR)
        {
            return year;
        }
        else
        {
            return INVALID_ENTRY_VALUE;
        } // end of if (year >= YEAR_MIN && year <= CALENDAR.YEAR)
    } // end of method validateYear(int year)

    private static int validateMonth(int year, int month)
    {
        if (year == CURRENT_YEAR)
        {
            if (month > CALENDAR.JANUARY && month <= CURRENT_MONTH)
            {
                return month;
            }
            else
            {
                return INVALID_ENTRY_VALUE;
            }
        }
        else if (month > CALENDAR.JANUARY && month < CALENDAR.DECEMBER)
        {
            return month;
        }
        else
        {
            return INVALID_ENTRY_VALUE;
        }// end of if (year == CALENDAR.YEAR &&...
    } // end of method validateMonth(int year, int month)

    private static int validateDay(int year, int month, int day)
    {
        if (year == CURRENT_YEAR && month == CURRENT_MONTH)
        {
            if (day >= DAY_MIN && day <= CURRENT_DAY)
            {
                return day;
            }
            else
            {
                return INVALID_ENTRY_VALUE;
            } // end of if (day >= DAY_MIN && day <= CURRENT_DAY)
        }
        else
        {
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
                dayInAMonth= DAY_IN_A_MONTH1;
                break;
                case 4:
                case 6:
                case 9:
                case 11:
                dayInAMonth= DAY_IN_A_MONTH2;
                break;
                case 2:
                if (year % 4 != 0)
                {
                    dayInAMonth= DAY_IN_A_MONTH4;
                }
                else
                {
                    dayInAMonth= DAY_IN_A_MONTH3;
                } // end of if (year % 4 != 0)
                break;
                default:
                break;
            } // end of switch (month - 1)

            if (day >= DAY_MIN && day <= dayInAMonth)
            {
                return day;
            }
            else
            {
                return INVALID_ENTRY_VALUE;
            } // end of if (day >= DAY_MIN && day <= dayInAMonth)
        } // end of if (year == CALENDAR.YEAR &&...
    } // end of method validateDay(int year, int month, int day)

    private static int validateHour(int year, int month, int day, int hour)
    {
        if (year == CURRENT_YEAR && month == CURRENT_MONTH && 
        day == CURRENT_DAY)
        {
            if (hour >= TIME_MIN && hour <= CURRENT_HOUR)
            {
                return hour;
            }
            else
            {
                return INVALID_ENTRY_VALUE;
            } // end of if (hour > TIME_MIN && hour <= CURRENT_HOUR)
        }
        else if (hour >= TIME_MIN && hour <= HOUR_IN_A_DAY)
        {
            return hour;
        }
        else
        {
            return INVALID_ENTRY_VALUE;
        }// end of if (year == CALENDAR.YEAR &&...
    } // end of method validateHour(int year, int month, int day, int hour)

    private static int validateMinute(int year, int month, int day, 
    int hour, int minute)
    {
        if (year == CURRENT_YEAR && month == CURRENT_MONTH && 
        day == CURRENT_DAY && hour == CURRENT_HOUR)
        {
            if (minute >= TIME_MIN && minute <= CURRENT_MINUTE)
            {
                return minute;
            }
            else
            {
                return INVALID_ENTRY_VALUE;
            } // end of if (minute > TIME_MIN && minute <= CURRENT_MINUTE)
        }
        else if (minute >= TIME_MIN && minute <= MINUTE_IN_AN_HOUR)
        {
            return minute;
        }
        else
        {
            return INVALID_ENTRY_VALUE;
        }// end of if (year == CALENDAR.YEAR &&...
    } // end of method validateMinute(int year, int month, int day...
} // end of class LogEntryAnalyzer