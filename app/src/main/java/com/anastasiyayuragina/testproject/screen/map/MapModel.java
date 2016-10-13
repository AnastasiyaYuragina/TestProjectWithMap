package com.anastasiyayuragina.testproject.screen.map;

import com.anastasiyayuragina.testproject.RetrofitSingleton;
import com.anastasiyayuragina.testproject.ourDataBase.MapItem;
import com.anastasiyayuragina.testproject.MapsAPIService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by anastasiya yuragina on 8/10/16.
 *
 */
class MapModel implements MapMvp.ModelMap {

    @Override
    public void loadData(String countryName, final OnDataLoadedMap listener) {
        MapsAPIService service = RetrofitSingleton.getInstance().getRetrofit().create(MapsAPIService.class);
        Observable<MapItem> itemObservable = service.loadItem("https://restcountries.eu/rest/v1/name/" + countryName);
        itemObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onDataLoadedMap, throwable -> listener.onDataLoadedMap(null));
    }
}
