package com.anastasiyayuragina.testproject.ourDataBase;

import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.PageInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

class CountryDeserializer extends JsonDeserializer<CountryItem> {

    private static final int PAGE_INFO = 0;
    private static final int COUNTRIES_ARRAY = 1;

    @Override
    public CountryItem deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        CountryItem CountryItem = new CountryItem();
        ObjectMapper mapper = new ObjectMapper();
        TreeNode treeNode = p.readValueAsTree();

        CountryItem.setPageInfo((PageInfo) mapper.readerFor(new TypeReference<PageInfo>() {})
                .readValue((JsonNode) treeNode.get(PAGE_INFO)));
        CountryItem.setCountryList((List<Country>) mapper.readerFor(new TypeReference<List<Country>>(){})
                .readValue((JsonNode) treeNode.get(COUNTRIES_ARRAY)));

        return CountryItem;
    }
}
