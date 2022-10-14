package maksab.sd.customer.util.general;

import java.util.Locale;

/**
 * Created by AdminUser on 10/25/2017.
 */

public class NumbersUtil {

    public static boolean IsVaildNumber(String mobilenumber) {
        boolean lengthValidating = mobilenumber.length() == 10;
        //mtn & zain prodvider
        if (lengthValidating && mobilenumber.startsWith("09")) {
            return true;
        }
        //sudani provider
        else if (lengthValidating && mobilenumber.startsWith("01")) {
            return true;
        }

        return false;
    }

    public static double round(double no) {
        String s = String.format(Locale.ENGLISH, "%.2f", no);
        s = s.replaceAll("٫", ".");
        return Double.valueOf(s);
    }

    public static String formatAmount(double amount) {
        String result = Math.round(amount) + " " + "ج";
        return result;
    }

    public static String formatAmountWithoutCurrency(double amount) {
        String result = Math.round(amount) + "";
        return result;
    }
}
