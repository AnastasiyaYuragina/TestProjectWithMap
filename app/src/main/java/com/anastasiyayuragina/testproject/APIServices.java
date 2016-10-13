package com.anastasiyayuragina.testproject;

import com.anastasiyayuragina.testproject.ourDataBase.CountryItem;
import com.anastasiyayuragina.testproject.ourDataBase.MapItem;
import java.util.Map;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by anastasiyayuragina on 10/13/16.
 *
 */

public interface APIServices {

    @GET("country")
    Observable<CountryItem> loadCountryItem(@QueryMap Map<String, String> param);

    @GET
    Observable<MapItem> loadMapItem(@Url String url);
}
