package com.anastasiyayuragina.testproject.screen.country_list;

import com.anastasiyayuragina.testproject.jsonCountriesClasses.PageInfo;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by anastasiyayuragina on 8/2/16.
 *
 */
class CountriesPresenter implements CountriesMvp.Presenter, CountriesMvp.Model.OnDataLoaded, Observer {

    private CountriesMvp.Model model;
    private CountriesMvp.View view;
    private PageInfo pageInfo = null;
    private boolean dataLoaded = false;

    CountriesPresenter(CountriesMvp.View view) {
        this.view = view;
        this.model = new CountriesModel();
    }

    @Override
    public void loadData() {
        if (pageInfo == null) {
            model.loadData(1, this);
        } else {
            int page = pageInfo.getPage() + 1;
            if (page <= pageInfo.getPages()){
                view.showLoadMore();
                dataLoaded = false;
                model.loadData(page, this);
            } else if (page > pageInfo.getPages()) {
                dataLoaded = true;
            }
        }
    }

    @Override
    public void onStop() {
        view = null;
    }

    @Override
    public void onDataLoaded(List<Country> countryList, PageInfo pageInfo) {
        if(view != null){
            view.setData(countryList);
            dataLoaded = true;
        }

        this.pageInfo = pageInfo;
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    @Override
    public void update(Observable observable, Object o) {
        loadData();
    }
}
