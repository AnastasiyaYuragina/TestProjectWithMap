package com.anastasiyayuragina.testproject.screen.country_list;

import android.support.v4.util.ArrayMap;
import com.anastasiyayuragina.testproject.CountriesAPIService;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country_Table;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.PageInfo;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Region;
import com.anastasiyayuragina.testproject.ourDataBase.CountryItem;
import com.raizlabs.android.dbflow.sql.language.Select;
import java.util.List;
import java.util.Map;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by anastasiya yuragina on 8/2/16.
 *
 */
class CountriesModel implements CountriesMvp.Model {
    private List<Country> list;
    private PageInfo pageInfo;

    @Override
    public void loadData(final int page, final OnDataLoaded listener) {
        List<Country> countryTable = new Select().from(Country.class).where(Country_Table.page.is(page)).queryList();

        if (countryTable.isEmpty()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl("http://api.worldbank.org/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            CountriesAPIService service = retrofit.create(CountriesAPIService.class);

            Observable<CountryItem> itemCall = service.loadItem(pageParam(String.valueOf(page)));

            itemCall.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<CountryItem>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(CountryItem countryItem) {
                            list = countryItem.getCountryList();
                            pageInfo = countryItem.getPageInfo();
                            listener.onDataLoaded(list, pageInfo);
                            saveIntoDB(list, page);
                        }
                    });
        } else {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPage(page);
            pageInfo.setPages(page + 1);

            listener.onDataLoaded(countryTable, pageInfo);
        }
    }

    private Map<String, String> pageParam(String page) {
        Map<String, String> urlParams = new ArrayMap<>();
        urlParams.put("per_page", "10");
        urlParams.put("format", "json");
        urlParams.put("page", page);

        return urlParams;
    }

    private void saveIntoDB (List<Country> itemCountry, int page) {
        for (int i = 0; i < itemCountry.size(); i++) {
            Country countryListItem = itemCountry.get(i);
            Country country = new Country();
            Region region = new Region();

            region.setId(countryListItem.getRegion().getId());
            region.setValue(countryListItem.getRegion().getValue());
            region.save();

            country.setId(countryListItem.getId());
            country.setName(countryListItem.getName());
            country.setRegion(region);
            country.setLatitude(countryListItem.getLatitude());
            country.setLongitude(countryListItem.getLongitude());
            country.setPage(page);
            country.save();
        }
    }
}
