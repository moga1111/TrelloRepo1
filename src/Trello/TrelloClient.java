package Trello;

import Trello.Services.TrelloService;
import okhttp3.OkHttpClient;

import static Util.TrelloUtilityClass.getOkHttpClientInstance;
import static Util.TrelloUtilityClass.getRetrofitInstance;

public class TrelloClient {

    private static TrelloClient instance;
    private static TrelloService trelloService;

    /**
     * Singleton, do not allow instantiating with constructor
     * */
    private TrelloClient() {}

    /**
     * Retrieves an instance of this class
     *
     * @param apiKey Trello Application Key
     * @param apiToken User authorization token
     * @return TrelloClient single instance
    * */
    public static TrelloClient getInstance(String apiKey, String apiToken) {
        OkHttpClient okHttpClient= getOkHttpClientInstance(apiKey,apiToken);
        trelloService = getRetrofitInstance(okHttpClient).create(TrelloService.class);
        if(instance == null)
            instance = new TrelloClient();
        return instance;
    }

    /**
     * Retrieves prepared TrelloService instance
     *
     * @return TrelloService instance
     * */
    public TrelloService getTrelloService() {
        return trelloService;
    }
}
