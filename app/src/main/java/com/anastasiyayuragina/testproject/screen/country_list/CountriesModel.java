package com.anastasiyayuragina.testproject.screen.country_list;

import android.support.v4.util.ArrayMap;
import com.anastasiyayuragina.testproject.CountriesAPIService;
import com.anastasiyayuragina.testproject.ourDataBase.CountryTable;
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
public class CountriesModel implements CountriesMvp.Model {
    private CountryTable countryTable = new CountryTable();

    @Override
    public void loadData(int page, final OnDataLoaded listener) {

        List<CountryTable> countryTables = new Select(CountryTable_Table.country).from(CountryTable.class).where(CountryTable_Table.countryPage.is(page)).queryList();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.worldbank.org/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        CountriesAPIService service = retrofit.create(CountriesAPIService.class);
        Call<ItemCountry> itemCall = service.loadItem(pageParam(String.valueOf(page)));
        itemCall.enqueue(new Callback<ItemCountry>() {
            @Override
            public void onResponse(Call<ItemCountry> call, Response<ItemCountry> response) {
                ItemCountry itemCountry = response.body();
                listener.onDataLoadedList(itemCountry.getCountryList());
                listener.onDataLoadedPage(itemCountry.getPageInfo());

                for (int i = 0; i < itemCountry.getCountryList().size(); i++) {
                    countryTable.setCountry(itemCountry.getCountryList().get(i));
                    countryTable.setCountryPage(itemCountry.getPageInfo().getPage());
                    countryTable.save();
                }

            }

            @Override
            public void onFailure(Call<ItemCountry> call, Throwable t) {
            }
        });
    }

    private Map<String, String> pageParam(String page) {
        Map<String, String> urlParams = new ArrayMap<>();
        urlParams.put("per_page", "10");
        urlParams.put("format", "json");
        urlParams.put("page", page);

        return urlParams;
    }
}
