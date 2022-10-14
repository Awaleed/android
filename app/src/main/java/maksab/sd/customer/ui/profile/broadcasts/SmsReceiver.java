package maksab.sd.customer.ui.profile.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dev2 on 1/28/2018.
 */

public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();
            Log.d("autodetect" , sender);
            //You must check here if the sender is your provider and not another one with same text.
                String messageBody = smsMessage.getMessageBody();
             Pattern p = Pattern.compile("(|^)\\d{4}");

            try{


                if(smsMessage!=null)
                {
                    Matcher m = p.matcher(messageBody);
                    if(m.find()) {
                        if (mListener !=null){
                            Log.d("autodetect" , m.group(0));
                        mListener.messageReceived(m.group(0));
                        }
                    }
                    else
                    {
                        //something went wrong
                    }
                }
            }catch(Exception e) {

            }
              }
        }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
