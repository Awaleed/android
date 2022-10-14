package maksab.sd.customer.util.general;

/**
 * Created by AdminUser on 10/25/2017.
 */

public  class NumberValidator {

    public static boolean IsVaildNumber(String mobilenumber){
        boolean lengthValidating = mobilenumber.length() ==10;
        //mtn & zain prodvider
        if ( lengthValidating && mobilenumber.startsWith("09")){
            return true;
        }
        //sudani provider
        else if(lengthValidating && mobilenumber.startsWith("01")) {
          return true;
        }

        return false;
    }
}
