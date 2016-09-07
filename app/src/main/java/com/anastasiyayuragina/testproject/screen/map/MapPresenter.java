package com.anastasiyayuragina.testproject.screen.map;

import com.anastasiyayuragina.testproject.ourDataBase.ItemForMap;

/**
 * Created by anastasiyayuragina on 8/10/16.
 *
 */
public class MapPresenter implements MapMvp.PresenterMap, MapMvp.ModelMap.OnDataLoadedMap {
    private MapMvp.ModelMap modelMap;
    private MapMvp.ViewMap viewMap;
    private String countryName;

    public MapPresenter(MapMvp.ViewMap viewMap) {
        this.viewMap = viewMap;
        this.modelMap = new MapModel();
    }

    @Override
    public void loadData() {
        modelMap.loadData(countryName, this);
    }

    @Override
    public void onDataLoadedMap(ItemForMap itemForMap) {

        viewMap.setMapMarker(itemForMap);
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


}
