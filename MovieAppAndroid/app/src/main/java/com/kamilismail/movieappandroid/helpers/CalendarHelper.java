package com.kamilismail.movieappandroid.helpers;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CalendarHelper {

    public CalendarHelper() {}

    public String getCurrentDateWitTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(c.getTime());
    }

    /**
     * Sprawdznie czy podana data nie jest starsza od danej chwili o 20 min.
     * @param date
     * @return
     * @throws ParseException
     */
    public boolean checkIfNotToOld(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        cal.setTime(sdf.parse(date));
        long minutes = TimeUnit.MILLISECONDS.toMinutes((Calendar.getInstance().getTime().getTime() - cal.getTime().getTime()));
        if (minutes > 10)
            return false;
        else
            return true;
    }
}
