package com.anastasiyayuragina.testproject.screen.map;

import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country_Table;
import com.anastasiyayuragina.testproject.ourDataBase.MapItem;
import com.raizlabs.android.dbflow.sql.language.Select;

/**
 * Created by anastasiyayuragina on 8/10/16.
 *
 */
class MapPresenter implements MapMvp.PresenterMap, MapMvp.ModelMap.OnDataLoadedMap {
    private MapMvp.ModelMap modelMap;
    private MapMvp.ViewMap viewMap;
    private String countryName;

    MapPresenter() {
        this.modelMap = new MapModel();
    }

    @Override
    public void loadData() {
        modelMap.loadData(countryName, this);
    }

    @Override
    public void onDataLoadedMap(MapItem mapItem) {
        if (viewMap != null) {
            viewMap.setMapMarker(mapItem);
        }
    }

    @Override
    public void initCountry(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public void onStop() {
        viewMap = null;
    }

    @Override
    public void addInDB(String comment, String countryId) {
        if (!comment.isEmpty()) {
            Country countryComment = new Select()
                    .from(Country.class)
                    .where(Country_Table.id.is(countryId))
                    .querySingle();
            assert countryComment != null;
            countryComment.setComment(comment);
            countryComment.save();
        }
    }

    @Override
    public void setView(MapMvp.ViewMap viewMap) {
        this.viewMap = viewMap;
    }
}
