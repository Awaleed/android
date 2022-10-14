package maksab.sd.customer.network.servicegenratores;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import maksab.sd.customer.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dev on 11/14/2017.
 */

public class GetWayServiceGenerator {

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BuildConfig.ApiBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder().readTimeout(3,TimeUnit.MINUTES).writeTimeout(3,TimeUnit.MINUTES).connectTimeout(3,TimeUnit.MINUTES);;


    public static <S> S createService(Class<S> serviceClass) {
        if (BuildConfig.DEBUG) {
            if (httpClient.interceptors().size() == 0) {
                httpClient.addInterceptor(logging);
            }
        }
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);

                httpClient.interceptors().clear();
                httpClient.addInterceptor(interceptor);

                if (BuildConfig.DEBUG) {
                    if (!httpClient.interceptors().contains(logging)) {
                        httpClient.addInterceptor(logging);
                    }
                }

                builder.client(httpClient.build());
                retrofit = builder.build();

        }

        return retrofit.create(serviceClass);
    }


    public static Retrofit retrofit() {
        return retrofit;
    }

}
