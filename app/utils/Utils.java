package utils;

import java.util.Calendar;
import java.util.Date;

public class Utils {
  /**
   * Returns a Date set to the last possible millisecond of the day, just
   * before midnight. If a null day is passed in, a new Date is created.
   * midnight (00m 00h 00s)
   */
  public static Date getEndOfDay(Date day) {
      return getEndOfDay(day,Calendar.getInstance());
  }


  public static Date getEndOfDay(Date day,Calendar cal) {
      if (day == null) day = new Date();
      cal.setTime(day);
      cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
      cal.set(Calendar.MINUTE,      cal.getMaximum(Calendar.MINUTE));
      cal.set(Calendar.SECOND,      cal.getMaximum(Calendar.SECOND));
      cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
      return cal.getTime();
  }

}
