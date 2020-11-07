package com.example.news.currencyFile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
//https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=BTC&to_currency=INR&apikey=UDU1FSG6BH1KX7A0
public interface ApiCurrency {

    String Base_Url = "https://www.alphavantage.co/";
    String key = "UDU1FSG6BH1KX7A0";
    String func = "CURRENCY_EXCHANGE_RATE";

    @GET("query")
    Call<CurrencyPojo> getCurrency(@Query("function") String function,
                              @Query("from_currency") String from_currency,
                              @Query("to_currency") String to_currency,
                              @Query("apikey") String apikey);
}
