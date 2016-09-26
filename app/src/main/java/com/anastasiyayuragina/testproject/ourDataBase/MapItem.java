package com.anastasiyayuragina.testproject.ourDataBase;

import com.anastasiyayuragina.testproject.jsonInfoForMapClasses.MapInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by anastasiyayuragina on 8/10/16.
 *
 */

@JsonDeserialize(using = MapDeserializer.class)
public class MapItem {

    private MapInfo mapInfo;

    public MapInfo getInfoForMap() {
        return mapInfo;
    }

    void setInfoForMap(MapInfo mapInfo) {
        this.mapInfo = mapInfo;
    }
}
