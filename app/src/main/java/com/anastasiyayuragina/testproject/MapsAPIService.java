package com.anastasiyayuragina.testproject;

import com.anastasiyayuragina.testproject.ourDataBase.MapItem;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by anastasiya yuragina on 8/10/16.
 *
 */
public interface MapsAPIService {
    @GET
    Observable<MapItem> loadItem(@Url String url);
}

