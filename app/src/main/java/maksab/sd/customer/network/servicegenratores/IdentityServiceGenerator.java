package maksab.sd.customer.network.servicegenratores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import maksab.sd.customer.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by WajdyEssam on 6/22/2017.
 */

public class IdentityServiceGenerator {
    private static final Gson GSON = new GsonBuilder().setLenient().create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BuildConfig.IdentityBaseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(GSON));

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder().readTimeout(40, TimeUnit.SECONDS).writeTimeout(40, TimeUnit.SECONDS).connectTimeout(40, TimeUnit.SECONDS);

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        if (BuildConfig.DEBUG) {
            if (!httpClient.interceptors().contains(logging)) {
                httpClient.addInterceptor(logging);
            }
        }
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }


    public static Retrofit retrofit() {
        return retrofit;
    }
}