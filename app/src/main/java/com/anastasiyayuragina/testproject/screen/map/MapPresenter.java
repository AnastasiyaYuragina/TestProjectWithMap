package com.anastasiyayuragina.testproject.screen.map;

import com.anastasiyayuragina.testproject.ourDataBase.MapItem;

/**
 * Created by anastasiyayuragina on 8/10/16.
 *
 */
class MapPresenter implements MapMvp.PresenterMap, MapMvp.ModelMap.OnDataLoadedMap {
    private MapMvp.ModelMap modelMap;
    private MapMvp.ViewMap viewMap;
    private String countryName;

    MapPresenter(MapMvp.ViewMap viewMap) {
        this.viewMap = viewMap;
        this.modelMap = new MapModel();
    }

    @Override
    public void loadData() {
        modelMap.loadData(countryName, this);
    }

    @Override
    public void onDataLoadedMap(MapItem mapItem) {
        viewMap.setMapMarker(mapItem);
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


}
