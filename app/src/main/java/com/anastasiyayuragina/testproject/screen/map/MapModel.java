package com.anastasiyayuragina.testproject.screen.map;

import com.anastasiyayuragina.testproject.ourDataBase.MapItem;
import com.anastasiyayuragina.testproject.MapsAPIService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by anastasiya yuragina on 8/10/16.
 *
 */
class MapModel implements MapMvp.ModelMap {

    @Override
    public void loadData(String countryName, final OnDataLoadedMap listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://restcountries.eu/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        MapsAPIService service = retrofit.create(MapsAPIService.class);
        Observable<MapItem> itemObservable = service.loadItem(countryName);
        itemObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MapItem>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onDataLoadedMap(null);
                    }

                    @Override
                    public void onNext(MapItem mapItem) {
                        listener.onDataLoadedMap(mapItem);
                    }
                });
    }
}
