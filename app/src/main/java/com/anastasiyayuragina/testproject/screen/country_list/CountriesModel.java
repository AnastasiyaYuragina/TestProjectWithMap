package com.anastasiyayuragina.testproject.screen.country_list;

import android.support.v4.util.ArrayMap;
import com.anastasiyayuragina.testproject.ServiceSingleton;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country_Table;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.PageInfo;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.PageInfo_Table;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Region;
import com.anastasiyayuragina.testproject.ourDataBase.CountryItem;
import com.raizlabs.android.dbflow.sql.language.Select;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by anastasiya yuragina on 8/2/16.
 *
 */
class CountriesModel implements CountriesMvp.Model {
    private PageInfo pageInfo1;

    @Override
    public void loadData(final int page, final OnDataLoaded listener) {

        requestCacheCountryList(page)
                .filter(countryItem -> !countryItem.getCountryList().isEmpty())
                .switchIfEmpty(requestNetworkCountryList(page))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        countryItem -> {
                            listener.onDataLoaded(countryItem.getCountryList(), pageInfo1);
                        },
                        listener::onError);
    }

    private Observable<CountryItem> requestCacheCountryList(int page) {
        return Observable.fromCallable(() -> {
            List<Country> countryList = new Select().from(Country.class)
                    .where(Country_Table.page.is(page)).queryList();
            pageInfo1 = new Select().from(PageInfo.class).where(PageInfo_Table.page.is(page)).querySingle();
            CountryItem countryItem = new CountryItem();
            countryItem.setCountryList(countryList);
            return countryItem;
        }).subscribeOn(Schedulers.io());
    }

    private Observable<CountryItem> requestNetworkCountryList(int page) {

        return ServiceSingleton.getInstance()
                .getAPIServices()
                .loadCountryItem(pageParam(String.valueOf(page)))
                .doOnNext(this::saveIntoDB)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(countryItem -> pageInfo1 = countryItem.getPageInfo());
    }

    private Map<String, String> pageParam(String page) {
        Map<String, String> urlParams = new ArrayMap<>();
        urlParams.put("per_page", "10");
        urlParams.put("format", "json");
        urlParams.put("page", page);

        return urlParams;
    }

    private void saveIntoDB (CountryItem itemCountry) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(itemCountry.getPageInfo().getPage());
        pageInfo.setPages(itemCountry.getPageInfo().getPages());
        pageInfo.setPerPage(itemCountry.getPageInfo().getPerPage());
        pageInfo.setTotal(itemCountry.getPageInfo().getTotal());
        pageInfo.save();

        for (Country countries : itemCountry.getCountryList()) {
            Country country = new Country();
            Region region = new Region();

            region.setId(countries.getRegion().getId());
            region.setValue(countries.getRegion().getValue());
            region.save();

            country.setId(countries.getId());
            country.setName(countries.getName());
            country.setRegion(region);
            country.setLatitude(countries.getLatitude());
            country.setLongitude(countries.getLongitude());
            country.setPage(pageInfo.getPage());
            country.save();
        }
    }
}
