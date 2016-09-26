package com.anastasiyayuragina.testproject.screen.map;

import com.anastasiyayuragina.testproject.ourDataBase.MapItem;
import com.anastasiyayuragina.testproject.MapsAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by anastasiyayuragina on 8/10/16.
 *
 */
class MapModel implements MapMvp.ModelMap {

    @Override
    public void loadData(String countryName, final OnDataLoadedMap listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        MapsAPIService service = retrofit.create(MapsAPIService.class);
        Call<MapItem> itemCall = service.loadItem(countryName);
        itemCall.enqueue(new Callback<MapItem>() {
            @Override
            public void onResponse(Call<MapItem> call, Response<MapItem> response) {
                MapItem mapItem = response.body();
                listener.onDataLoadedMap(mapItem);
            }

            @Override
            public void onFailure(Call<MapItem> call, Throwable t) {

            }
        });
    }
}
