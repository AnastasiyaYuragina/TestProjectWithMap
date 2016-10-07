package com.anastasiyayuragina.testproject;

import com.anastasiyayuragina.testproject.ourDataBase.CountryItem;
import java.util.Map;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface CountriesAPIService {
    @GET("country")
    Observable<CountryItem> loadItem(@QueryMap Map<String, String> param);
}
