package com.anastasiyayuragina.testproject.screen.map;

import com.anastasiyayuragina.testproject.ServiceSingleton;
import com.anastasiyayuragina.testproject.ourDataBase.MapItem;
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
        Observable<MapItem> itemObservable = ServiceSingleton.getInstance().getAPIServices()
                .loadMapItem("https://restcountries.eu/rest/v1/name/" + countryName);

        itemObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onDataLoadedMap, throwable -> listener.onDataLoadedMap(null));
    }
}
