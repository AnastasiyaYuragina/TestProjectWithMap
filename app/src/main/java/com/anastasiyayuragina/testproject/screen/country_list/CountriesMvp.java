package com.anastasiyayuragina.testproject.screen.country_list;

import android.content.Context;

import com.anastasiyayuragina.testproject.jsonCountriesClasses.PageInfo;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import java.util.List;

/**
 * Created by anastasiyayuragina on 8/2/16.
 *
 */
interface CountriesMvp {
    interface Presenter{
        void loadData();
        void onStop();
        boolean isDataLoaded();
        void setView(CountriesMvp.View view);
        void setProgressDialog ();
    }
    interface View{
        void setData(List<Country> countryList);
        void showLoadMore();
        void showProgressDialog();
        void hideProgressDialog();
    }
    interface Model{
        interface OnDataLoaded{
            void onDataLoaded(List<Country> countryList, PageInfo pageInfo);
            void onError(Throwable error);
        }
        void loadData(int page, OnDataLoaded listener);
    }
}
