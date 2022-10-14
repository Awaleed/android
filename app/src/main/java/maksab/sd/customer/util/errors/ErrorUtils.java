package maksab.sd.customer.util.errors;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.lang.annotation.Annotation;

import maksab.sd.customer.network.servicegenratores.IdentityServiceGenerator;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static void StringResponse(Context context, Response response) {
        try {
            Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ErrorResponse showError(Context context, Response response) {
        ErrorResponse errorMessage = ErrorUtils.parseError(response);

        if (errorMessage != null) {
            Toast.makeText(context,
                    errorMessage.getMessage() ,
                    Toast.LENGTH_LONG).show();
        }


        return errorMessage;
    }

    private static ErrorResponse parseError(Response<?> response) {
        Converter<ResponseBody, ErrorResponse> converter =
                IdentityServiceGenerator.retrofit()
                        .responseBodyConverter(ErrorResponse.class, new Annotation[0]);

        ErrorResponse error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            e.printStackTrace();
            return new ErrorResponse();
        }

        return error;
    }
}

