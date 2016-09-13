package com.anastasiyayuragina.testproject.screen.country_list;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.anastasiyayuragina.testproject.CountriesAPIService;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
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
public class CountriesModel implements CountriesMvp.Model {

    List<Country> countryTable = new Select().from(Country.class).queryList();
    private String TAG = "MyLog";

    @Override
    public void loadData(int page, final OnDataLoaded listener) {
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
                listener.onDataLoaded(itemCountry);

                saveIntoDB(itemCountry);

                for (int i = 0; i < countryTable.size(); i++) {
                    Log.d(TAG, countryTable.get(i).toString());
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

    private void saveIntoDB (ItemCountry itemCountry) {
        Country country = new Country();
//        Region region = new Region();

        for (int i = 0; i < itemCountry.getCountryList().size(); i++) {
//            region.setId(itemCountry.getCountryList().get(i).getRegion().getId());
//            region.setValue(itemCountry.getCountryList().get(i).getRegion().getValue());
//            region.save();

            country.setId(itemCountry.getCountryList().get(i).getId());
            country.setName(itemCountry.getCountryList().get(i).getName());
//            country.setRegion(region);
            country.setLatitude(itemCountry.getCountryList().get(i).getLatitude());
            country.setLongitude(itemCountry.getCountryList().get(i).getLongitude());
            country.save();
        }
    }
}
