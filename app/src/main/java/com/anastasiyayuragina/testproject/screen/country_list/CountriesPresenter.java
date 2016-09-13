package com.anastasiyayuragina.testproject.screen.country_list;

import android.support.v4.util.ArrayMap;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.PageInfo;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.ourDataBase.CountryComment;
import com.raizlabs.android.dbflow.sql.language.Select;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by anastasiyayuragina on 8/2/16.
 *
 */
public class CountriesPresenter implements CountriesMvp.Presenter, CountriesMvp.Model.OnDataLoaded, Observer {

    private CountriesMvp.Model model;
    private CountriesMvp.View view;
    private PageInfo pageInfo = null;
    private boolean dataLoaded = false;
    private Map<String, String> com = new ArrayMap<>();

    public CountriesPresenter(CountriesMvp.View view) {
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
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onDataLoadedList(List<Country> itemCountry) {

        for (int i = 0; i < itemCountry.size(); i++) {
            CountryComment comment = new Select(CountryComment_Table.comment).from(CountryComment.class).where(CountryComment_Table.id_country.is(itemCountry.get(i).getId())).querySingle();
            if (comment != null) {
                com.put(itemCountry.get(i).getId(), comment.getComment());
                itemCountry.get(i).setComment(com.get(itemCountry.get(i).getId()));
            }
        }

        if(view != null){
            view.setData(itemCountry);
            dataLoaded = true;
        }
    }

    @Override
    public void onDataLoadedPage(PageInfo pageInfo) {
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
