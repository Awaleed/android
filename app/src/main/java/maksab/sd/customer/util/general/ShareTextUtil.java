package maksab.sd.customer.util.general;

import android.content.Context;
import android.content.Intent;

public class ShareTextUtil {
   public void shareText(Context context, String captain , String body){
      Intent intent = new Intent(android.content.Intent.ACTION_SEND);

      intent.setType("text/plain");
      intent.putExtra(android.content.Intent.EXTRA_SUBJECT, captain);
      intent.putExtra(android.content.Intent.EXTRA_TEXT, body);
      context.startActivity(intent);
   }
}
