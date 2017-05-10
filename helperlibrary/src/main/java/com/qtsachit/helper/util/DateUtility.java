package com.qtsachit.helper.util;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * The class is Created by Sachit on 31/January/2017
 * <p>
 * Description- This class will provide the helper method for date utilities, Here you
 * can find the method that is used to parse or get the desirable date formatted objects
 * <p>
 * Additional notes-
 */
public class DateUtility {

    /**
     * Don't let anyone instantiate this class.
     */
    private DateUtility() {
        throw new Error("Do not need instantiate!");
    }


    private static final String TAG = DateUtils.class.getSimpleName();


    /**
     * Method is to convert date into GMT format
     *
     * @param date The date object which is to be parsed
     * @return
     */
    public static String convertDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {
            Date dateObject = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateObject);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

            String monthInString = getMonthInShort(month);


            return (monthInString + " " + day).toString();
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * Gets short name of the month from the given date.
     *
     * @param monthInNumber in integer
     * @return the name of the month as an abbreviation
     */
    public static String getMonthInShort(int monthInNumber) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getShortMonths();
        if (monthInNumber >= 0 && monthInNumber <= 12) {
            month = months[monthInNumber - 1];
        }
        return month;
    }

    /**
     * This method will convert the UTC formatted date into local date format
     *
     * @param dateTime date time which is need to be converted into local date
     * @return local date in dd MM yyyy format
     */
    public static String convertDateFromUTCtoLocalTime(String dateTime) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date myDate = simpleDateFormat.parse(dateTime);

            DateFormat defaultFormat = new SimpleDateFormat("dd MMM yyyy");
            defaultFormat.setTimeZone(TimeZone.getDefault());

            String formattedDate = defaultFormat.format(myDate);
            return formattedDate;
        } catch (Exception e) {

        }
        return "";
    }


    /**
     * This method will convert the UTC formatted time into local time format
     *
     * @param dateTime time time which is need to be converted into local time
     * @return local time in hh:mm a format
     */
    public static String convertTimeFromUTCtoLocalTime(String dateTime) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date myDate = simpleDateFormat.parse(dateTime);

            DateFormat defaultFormat = new SimpleDateFormat("hh:mm a");
            defaultFormat.setTimeZone(TimeZone.getDefault());

            String formattedTime = defaultFormat.format(myDate);
            return formattedTime;
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * This method will return the current date
     *
     * @param dateFormat format in which date should be shown
     * @return the current date
     */
    public static String getCurrentDate(String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        String formattedDate = df.format(calendar.getTime());
        return formattedDate;
    }

    /****
     * Parses date string and return a {@link java.util.Date} object
     *
     * @return The ISO formatted date object
     *****/
    public static Date parseDate(String date) {

        if (date == null) {
            return null;
        }

        StringBuffer sbDate = new StringBuffer();
        sbDate.append(date);
        String newDate = null;
        Date dateDT = null;

        try {
            newDate = sbDate.substring(0, 19).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String rDate = newDate.replace("T", " ");
        String nDate = rDate.replaceAll("-", "/");

        try {
            dateDT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).parse(nDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateDT;
    }

    /**
     * Returns abbreviated (3 letters) day of the week.
     *
     * @param date
     *            ISO format date
     * @return The name of the day of the week
     */
    public static String getDayOfWeekAbbreviated(String date) {
        Date dateDT = parseDate(date);

        if (dateDT == null) {
            return null;
        }

        // Get current date
        Calendar c = Calendar.getInstance();
        // it is very important to
        // set the date of
        // the calendar.
        c.setTime(dateDT);
        int day = c.get(Calendar.DAY_OF_WEEK);

        String dayStr = null;

        switch (day) {

            case Calendar.SUNDAY:
                dayStr = "Sun";
                break;

            case Calendar.MONDAY:
                dayStr = "Mon";
                break;

            case Calendar.TUESDAY:
                dayStr = "Tue";
                break;

            case Calendar.WEDNESDAY:
                dayStr = "Wed";
                break;

            case Calendar.THURSDAY:
                dayStr = "Thu";
                break;

            case Calendar.FRIDAY:
                dayStr = "Fri";
                break;

            case Calendar.SATURDAY:
                dayStr = "Sat";
                break;
        }

        return dayStr;
    }

    /***
     * Gets the name of the month from the given date.
     *
     * @param date
     *            ISO format date
     * @return Returns the name of the month
     * ***/
    public static String getMonth(String date) {
        Date dateDT = parseDate(date);

        if (dateDT == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(dateDT);
        int day = c.get(Calendar.MONTH);

        String dayStr = null;

        switch (day) {

            case Calendar.JANUARY:
                dayStr = "January";
                break;

            case Calendar.FEBRUARY:
                dayStr = "February";
                break;

            case Calendar.MARCH:
                dayStr = "March";
                break;

            case Calendar.APRIL:
                dayStr = "April";
                break;

            case Calendar.MAY:
                dayStr = "May";
                break;

            case Calendar.JUNE:
                dayStr = "June";
                break;

            case Calendar.JULY:
                dayStr = "July";
                break;

            case Calendar.AUGUST:
                dayStr = "August";
                break;

            case Calendar.SEPTEMBER:
                dayStr = "September";
                break;

            case Calendar.OCTOBER:
                dayStr = "October";
                break;

            case Calendar.NOVEMBER:
                dayStr = "November";
                break;

            case Calendar.DECEMBER:
                dayStr = "December";
                break;
        }

        return dayStr;
    }

    /**
     * Gets abbreviated name of the month from the given date.
     *
     * @param date
     *            ISO format date
     * @return Returns the name of the month
     */
    public static String getMonthAbbreviated(String date) {
        Date dateDT = parseDate(date);

        if (dateDT == null) {
            return null;
        }

        // Get current date
        Calendar c = Calendar.getInstance();
        // it is very important to
        // set the date of
        // the calendar.
        c.setTime(dateDT);
        int day = c.get(Calendar.MONTH);

        String dayStr = null;

        switch (day) {

            case Calendar.JANUARY:
                dayStr = "Jan";
                break;

            case Calendar.FEBRUARY:
                dayStr = "Feb";
                break;

            case Calendar.MARCH:
                dayStr = "Mar";
                break;

            case Calendar.APRIL:
                dayStr = "Apr";
                break;

            case Calendar.MAY:
                dayStr = "May";
                break;

            case Calendar.JUNE:
                dayStr = "Jun";
                break;

            case Calendar.JULY:
                dayStr = "Jul";
                break;

            case Calendar.AUGUST:
                dayStr = "Aug";
                break;

            case Calendar.SEPTEMBER:
                dayStr = "Sep";
                break;

            case Calendar.OCTOBER:
                dayStr = "Oct";
                break;

            case Calendar.NOVEMBER:
                dayStr = "Nov";
                break;

            case Calendar.DECEMBER:
                dayStr = "Dec";
                break;
        }

        return dayStr;
    }

    /***
     * Converts ISO date string to UTC timezone equivalent.
     *
     * @param dateAndTime
     *            ISO formatted time string.
     ****/
    public static String getUTCTime(String dateAndTime) {
        Date d = parseDate(dateAndTime);

        String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());

        // Convert Local Time to UTC
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        return sdf.format(d);
    }

}
