package com.anastasiyayuragina.testproject.screen.map;

import com.anastasiyayuragina.testproject.ourDataBase.MapItem;

/**
 * Created by anastasiyayuragina on 8/10/16.
 *
 */
interface MapMvp {
    interface PresenterMap {
        void loadData();
        void initCountry(String countryName);
        void onStop();
        void addInDB(String comment, String countryId);
        void setView(MapMvp.ViewMap viewMap);
    }

    interface ViewMap {
        void setMapMarker(MapItem mapItem);
    }

    interface ModelMap {
        interface OnDataLoadedMap {
            void onDataLoadedMap(MapItem mapItem);
        }
        void loadData(String countryName, OnDataLoadedMap listener);
    }
}
