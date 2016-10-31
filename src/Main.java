import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by Juan Pablo on 31/10/2016.
 */
public class Main {
    public static void main(String[] args) {
        TmdbService mTmdbService = getTmdbService();
        mTmdbService.getPelicula(343611).enqueue(new Callback<Pelicula>() {
            @Override
            public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body().getTitulo());
                    System.out.println(response.body().getResumen());
                }
            }

            @Override
            public void onFailure(Call<Pelicula> call, Throwable t) {
            }
        });
    }

    public static TmdbService getTmdbService() {
        // Creamos un Interceptor para inyectar el query api_key=API_KEY en todos los requests
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", TmdbService.API_KEY)
                        .build();
                Request.Builder requestBuilder = original.newBuilder().url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TmdbService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(TmdbService.class);
    }
}
