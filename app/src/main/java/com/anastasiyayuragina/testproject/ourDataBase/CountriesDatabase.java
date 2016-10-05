package com.anastasiyayuragina.testproject.ourDataBase;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by anastasiyayuragina on 8/18/16.
 *
 */
@Database(name = CountriesDatabase.NAME, version = CountriesDatabase.VERSION)
public class CountriesDatabase {
    static final String NAME = "CountriesDataBase";
    static final int VERSION = 1;
}

