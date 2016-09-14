package com.anastasiyayuragina.testproject.screen.country_list;

import android.support.v4.util.ArrayMap;
import com.anastasiyayuragina.testproject.CountriesAPIService;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country_Table;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.PageInfo;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Region;
import com.anastasiyayuragina.testproject.ourDataBase.ItemCountry;
import com.raizlabs.android.dbflow.sql.language.Select;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by anastasiya yuragina on 8/2/16.
 *
 */
class CountriesModel implements CountriesMvp.Model {
    private ItemCountry itemCountry;

    @Override
    public void loadData(final int page, final OnDataLoaded listener) {
        List<Country> countryTable = new Select().from(Country.class).where(Country_Table.page.is(page)).queryList();

        if (countryTable.isEmpty()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.worldbank.org/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            CountriesAPIService service = retrofit.create(CountriesAPIService.class);
            Call<ItemCountry> itemCall = service.loadItem(pageParam(String.valueOf(page)));
            itemCall.enqueue(new Callback<ItemCountry>() {
                @Override
                public void onResponse(Call<ItemCountry> call, Response<ItemCountry> response) {
                    itemCountry = response.body();

                    listener.onDataLoadedList(itemCountry.getCountryList());
                    listener.onDataLoadedPage(itemCountry.getPageInfo());

                    saveIntoDB(itemCountry.getCountryList(), page);
                }

                @Override
                public void onFailure(Call<ItemCountry> call, Throwable t) {
                }
            });
        } else {
            listener.onDataLoadedList(countryTable);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPage(page);
            pageInfo.setPages(page + 1);
            listener.onDataLoadedPage(pageInfo);
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
            Country country = new Country();
            Region region = new Region();

            region.setId(itemCountry.get(i).getRegion().getId());
            region.setValue(itemCountry.get(i).getRegion().getValue());
            region.save();

            country.setId(itemCountry.get(i).getId());
            country.setName(itemCountry.get(i).getName());
            country.setRegion(region);
            country.setLatitude(itemCountry.get(i).getLatitude());
            country.setLongitude(itemCountry.get(i).getLongitude());
            country.setPage(page);
            country.save();
        }
    }
}
