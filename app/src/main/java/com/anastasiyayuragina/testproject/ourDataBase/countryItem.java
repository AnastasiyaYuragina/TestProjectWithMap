package com.anastasiyayuragina.testproject.ourDataBase;

import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.PageInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

/**
 * Created by anastasiya yuragina on 7/27/16.
 *
 */
@JsonDeserialize (using = CountryDeserializer.class)
public class CountryItem {
    private PageInfo pageInfo;
    private List<Country> countryList;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
}
