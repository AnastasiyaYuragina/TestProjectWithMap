package com.anastasiyayuragina.testproject.ourDataBase;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by anastasiyayuragina on 8/18/16.
 *
 */
@Database(name = CountriesDatabase.NAME, version = CountriesDatabase.VERSION)
public class CountriesDatabase {
    public static final String NAME = "CountriesDataBase";
    public static final int VERSION = 1;
}

