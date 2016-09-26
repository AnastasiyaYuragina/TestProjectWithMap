package com.anastasiyayuragina.testproject;

import com.anastasiyayuragina.testproject.ourDataBase.CountryItem;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CountriesAPIService {
    @GET("country")
    Call<CountryItem> loadItem(@QueryMap Map<String, String> param);
}
