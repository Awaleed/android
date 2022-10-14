package maksab.sd.customer.util.general;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    private Date date;
    private String dateString;
    private String dateStringStartWithYear;
    private String timeString;
    private String dateMonthNameString;

    public static DateUtil newInstance() {
        return new DateUtil();
    }

    private DateUtil() {
    }

    public boolean parse(String dateWithTime) {
        if(dateWithTime == null || TextUtils.isEmpty(dateWithTime)) return false;
        dateWithTime = dateWithTime.replaceAll("\u200f", "");

        SimpleDateFormat sdf;

        Locale locale;
        if (dateWithTime.contains("ص") || dateWithTime.contains("م")) {
            locale = new Locale("ar");
        }
        else {
            locale = Locale.ENGLISH;
        }

        if (dateWithTime.contains("/")) {
            if (dateWithTime.contains(":")) {
                if (locale.getLanguage().equals("ar")) {
                    sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss a", locale);
                }
                else
                    sdf = new SimpleDateFormat("MM/dd/yy hh:mm:ss a", locale);
            }
            else {
                if (locale.getLanguage().equals("ar")) {
                    sdf = new SimpleDateFormat("dd/MM/yy", locale);
                }
                else
                    sdf = new SimpleDateFormat("MM/dd/yy", locale);
            }
        }
        else {
            if (dateWithTime.endsWith("Z")) {
                sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale);
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            } else {
                sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale);
            }
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", locale);
        DateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
        DateFormat timeFormat = new SimpleDateFormat("hh:mm a", locale);
        DateFormat monthFormat = new SimpleDateFormat("dd MMMM yyyy", locale);

        try {
            date = sdf.parse(dateWithTime);
            dateString = dateFormat.format(date);
            dateStringStartWithYear = yearFormat.format(date);
            timeString = timeFormat.format(date);
            dateMonthNameString  = monthFormat.format(date);

            return true;
        } catch (ParseException e) {
            Log.d("PAARSE", dateWithTime);
            e.printStackTrace();
        }
        return false;
    }

    public String getDateTimeString() {
        return getDateString() + " - " + getTimeString();
    }

    public String getDateString() {
        return dateString;
    }

    public String getTimeString() {
        return timeString;
    }

    public String getDateMonthNameString() {
        return dateMonthNameString;
    }

    public String getDateStringStartWithYear() {
        return dateStringStartWithYear;
    }

    public Date getRawDate() {
        return date;
    }
}
