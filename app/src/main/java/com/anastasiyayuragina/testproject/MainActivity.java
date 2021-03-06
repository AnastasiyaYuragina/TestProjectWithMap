package com.anastasiyayuragina.testproject;

import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.screen.country_list.CountryFragment;
import com.anastasiyayuragina.testproject.screen.map.MapFragment;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements CountryFragment.OnListFragmentInteractionListener {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private FragmentManager manager;
    private InternetConnectionReceiver receiver;

    enum FragmentType {
        COUNTRY_LIST,
        MAP
    }

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            replaceFragment(FragmentType.COUNTRY_LIST);
        }

        getApplication();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        Action viewAction = setViewAction();
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    private Action setViewAction() {
        return Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.anastasiyayuragina.testproject/http/host/path")
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new InternetConnectionReceiver();
        registerReceiver(receiver, new IntentFilter(Context.CONNECTIVITY_SERVICE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = setViewAction();
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    void replaceFragment(FragmentType type) {
        Fragment fragment;
        fragment = manager.findFragmentByTag(type.name());

        if (fragment == null) {
            fragment = CountryFragment.newInstance(1);
        }

        FragmentTransaction transaction = manager.beginTransaction();
        if (type.equals(FragmentType.COUNTRY_LIST)) {
            transaction.replace(R.id.container, fragment, type.name()).commit();
        }
    }

    void showCountryMap(String countryName, String latitude, String longitude, String id){
        Fragment mapFragment = MapFragment.newInstance(countryName, latitude, longitude, id);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, mapFragment, FragmentType.MAP.name()).addToBackStack(null).commit();
    }

    @Override
    public void onListFragmentInteraction(Country item) {
        showCountryMap(item.getName(), item.getLatitude(), item.getLongitude(), item.getId());
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}
