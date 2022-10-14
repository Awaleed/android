package maksab.sd.customer.util.general;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.widget.Toast;

public  class WhatsappUtil {

     public void openWhatsApp(String number, Context context) {
         try {
             number = number.replace(" ", "").replace("+", "");

             Intent sendIntent = new Intent("android.intent.action.MAIN");
             sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
             sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
             context.startActivity(sendIntent);

         } catch(Exception e) {
             Toast.makeText(context , e.getMessage() , Toast.LENGTH_LONG).show();
         }
     }
}
