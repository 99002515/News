package com.example.news.currencyFile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RealtimeCurrencyExchangeRate {

    @SerializedName("1. From_Currency Code")
    @Expose
    private String _1FromCurrencyCode;
    @SerializedName("2. From_Currency Name")
    @Expose
    private String _2FromCurrencyName;
    @SerializedName("3. To_Currency Code")
    @Expose
    private String _3ToCurrencyCode;
    @SerializedName("4. To_Currency Name")
    @Expose
    private String _4ToCurrencyName;
    @SerializedName("5. Exchange Rate")
    @Expose
    private String _5ExchangeRate;
    @SerializedName("6. Last Refreshed")
    @Expose
    private String _6LastRefreshed;
    @SerializedName("7. Time Zone")
    @Expose
    private String _7TimeZone;
    @SerializedName("8. Bid Price")
    @Expose
    private String _8BidPrice;
    @SerializedName("9. Ask Price")
    @Expose
    private String _9AskPrice;

    public String get1FromCurrencyCode() {
        return _1FromCurrencyCode;
    }

    public void set1FromCurrencyCode(String _1FromCurrencyCode) {
        this._1FromCurrencyCode = _1FromCurrencyCode;
    }

    public String get2FromCurrencyName() {
        return _2FromCurrencyName;
    }

    public void set2FromCurrencyName(String _2FromCurrencyName) {
        this._2FromCurrencyName = _2FromCurrencyName;
    }

    public String get3ToCurrencyCode() {
        return _3ToCurrencyCode;
    }

    public void set3ToCurrencyCode(String _3ToCurrencyCode) {
        this._3ToCurrencyCode = _3ToCurrencyCode;
    }

    public String get4ToCurrencyName() {
        return _4ToCurrencyName;
    }

    public void set4ToCurrencyName(String _4ToCurrencyName) {
        this._4ToCurrencyName = _4ToCurrencyName;
    }

    public String get5ExchangeRate() {
        return _5ExchangeRate;
    }

    public void set5ExchangeRate(String _5ExchangeRate) {
        this._5ExchangeRate = _5ExchangeRate;
    }

    public String get6LastRefreshed() {
        return _6LastRefreshed;
    }

    public void set6LastRefreshed(String _6LastRefreshed) {
        this._6LastRefreshed = _6LastRefreshed;
    }

    public String get7TimeZone() {
        return _7TimeZone;
    }

    public void set7TimeZone(String _7TimeZone) {
        this._7TimeZone = _7TimeZone;
    }

    public String get8BidPrice() {
        return _8BidPrice;
    }

    public void set8BidPrice(String _8BidPrice) {
        this._8BidPrice = _8BidPrice;
    }

    public String get9AskPrice() {
        return _9AskPrice;
    }

    public void set9AskPrice(String _9AskPrice) {
        this._9AskPrice = _9AskPrice;
    }

}
