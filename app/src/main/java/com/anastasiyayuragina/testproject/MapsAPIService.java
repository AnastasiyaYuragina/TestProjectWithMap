package com.anastasiyayuragina.testproject;

import com.anastasiyayuragina.testproject.ourDataBase.MapItem;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by anastasiya yuragina on 8/10/16.
 *
 */
public interface MapsAPIService {
    @GET("rest/v1/name/{country}")
    Observable<MapItem> loadItem(@Path("country") String country);
}

