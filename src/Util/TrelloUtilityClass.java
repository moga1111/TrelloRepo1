package Util;

import Trello.TrelloClient;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrelloUtilityClass {
    /**
     * do not allow instantiating with constructor
     * */
    private TrelloUtilityClass(){}

    /**
     * Helper method used by Retrofit object in TrelloClient
     *
     * @param key Trello Application Key
     * @param token User authorization token
     * @return Returns prepared OkHttpClient with provided key, token
     * @see Trello.TrelloClient#getInstance(String, String)
     * */
    public static OkHttpClient getOkHttpClientInstance(String key, String token)
    {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url()
                            .newBuilder()
                            .addQueryParameter("key", key) //include key in all requests
                            .addQueryParameter("token",token) //include token in all requests
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }).build();
    }
    /**
     * Helper method used by Retrofit object in TrelloClient
     *
     * @param key Trello Application Key
     * @return Returns prepared OkHttpClient with provided key
     * */
    public static OkHttpClient getOkHttpClientInstance(String key)
    {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url()
                            .newBuilder()
                            .addQueryParameter("key", key)
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }).build();
    }

    /**
     * Helper method used by Retrofit object in TrelloClient
     *
     * @param client Prepared OkHttpClient Object
     * @return Retrofit instance after processing OkHttpClient object
     * @see TrelloClient#getInstance(String, String)
     * @see TrelloClient#getTrelloService()
     * */
    public static Retrofit getRetrofitInstance(OkHttpClient client)
    {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
