package com.anastasiyayuragina.testproject.ourDataBase;

import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by anastasiyayuragina on 9/13/16.
 *
 */

@Table(database = MyDatabase.class)
public class CountryTable extends BaseModel {

    @PrimaryKey
    int id;

    @Column
    int countryPage;

    @Column
    Country country;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getCountryPage() {
        return countryPage;
    }

    public void setCountryPage(int countryPage) {
        this.countryPage = countryPage;
    }
}
