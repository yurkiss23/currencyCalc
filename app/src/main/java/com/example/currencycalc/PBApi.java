package com.example.currencycalc;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PBApi {
    @GET("pubinfo?json=&exchange=&coursid=5")
    public Call<List<Excange>> getAll();
}
