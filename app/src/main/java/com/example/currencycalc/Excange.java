package com.example.currencycalc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Excange {
    @SerializedName("ccy")
    @Expose
    private String ccy;
    @SerializedName("base_ccy")
    @Expose
    private String base_ccy;
    @SerializedName("buy")
    @Expose
    private double buy;
    @SerializedName("sale")
    @Expose
    private double sale;

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public void setBase_ccy(String base_ccy) {
        this.base_ccy = base_ccy;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }
}
