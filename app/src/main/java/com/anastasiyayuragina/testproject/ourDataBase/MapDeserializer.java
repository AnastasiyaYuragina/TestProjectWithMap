package com.anastasiyayuragina.testproject.ourDataBase;

import com.anastasiyayuragina.testproject.jsonInfoForMapClasses.MapInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Created by anastasiyayuragina on 8/11/16.
 *
 */
class MapDeserializer extends JsonDeserializer<MapItem> {

    @Override
    public MapItem deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        MapItem mapItem = new MapItem();
        ObjectMapper mapper = new ObjectMapper();
        TreeNode treeNode = p.readValueAsTree();

        mapItem.setInfoForMap((MapInfo) mapper.readerFor(new TypeReference<MapInfo>() {})
                .readValue((JsonNode) treeNode.get(0)));

        return mapItem;
    }
}
