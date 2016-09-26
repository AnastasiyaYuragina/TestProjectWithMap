package com.anastasiyayuragina.testproject.screen.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country_Table;
import com.anastasiyayuragina.testproject.jsonInfoForMapClasses.MapInfo;
import com.anastasiyayuragina.testproject.ourDataBase.MapItem;
import com.anastasiyayuragina.testproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.raizlabs.android.dbflow.sql.language.Select;
import java.util.List;
import java.util.Locale;

/**
 * Created by anastasiya yuragina on 8/5/16.
 *
 */
public class MapFragment extends Fragment implements MapMvp.ViewMap{
    private static final String COUNTRY_NAME = "country_name";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String COUNTRY_ID = "id_country";
    private  MapView mapView;
    private String countryName;
    private double latitude;
    private double longitude;
    private String countryId;
    private TextView countryInfo;
    private EditText comment;

    public static MapFragment newInstance(String countryName, String latitude, String longitude, String id) {

        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        args.putString(COUNTRY_NAME, countryName);
        args.putString(LATITUDE, latitude);
        args.putString(LONGITUDE, longitude);
        args.putString(COUNTRY_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        countryName = getArguments().getString(COUNTRY_NAME);
        countryId = getArguments().getString(COUNTRY_ID);

        String latitudeString = getArguments().getString(LATITUDE);
        String longitudeString = getArguments().getString(LONGITUDE);
        Boolean isLatitude = latitudeString != null && !latitudeString.isEmpty();
        Boolean isLongitude = longitudeString != null && !longitudeString.isEmpty();

        if (isLatitude && isLongitude) {
            latitude = Double.parseDouble(latitudeString);
            longitude = Double.parseDouble(longitudeString);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        MapMvp.PresenterMap presenterMap = new MapPresenter(this);
        countryInfo = (TextView) view.findViewById(R.id.about_country);
        comment = (EditText) view.findViewById(R.id.editComment);

        presenterMap.setCountryName(countryName);
        presenterMap.loadData();

        mapView.onCreate(savedInstanceState);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        String commentString = comment.getText().toString();
        if (!commentString.isEmpty()) {
            Country countryComment = new Select()
                    .from(Country.class)
                    .where(Country_Table.id.is(countryId))
                    .querySingle();
            assert countryComment != null;
            countryComment.setComment(commentString);
            countryComment.save();
        }
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void setMapMarker(final MapItem mapItem) {
        if (mapItem == null) {
            Toast.makeText(mapView.getContext(), "Unknown coordinates", Toast.LENGTH_SHORT).show();
            return;
        }

        final MapInfo mapInfo = mapItem.getInfoForMap();

        if (latitude == 0 || longitude == 0) {
            final int LATITUDE_INT = 0;
            final int LONGITUDE_INT = 1;
            latitude = mapInfo.getLatlng().get(LATITUDE_INT);
            longitude = mapInfo.getLatlng().get(LONGITUDE_INT);
        }

        final StringBuilder countryInfoBuilder = countryInfoBuilder(mapInfo);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(4));

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker.setTitle(mapInfo.getCapital());

                        Boolean isVisibility = countryInfo.getVisibility() == View.INVISIBLE;
                        countryInfo.setVisibility(isVisibility ? View.VISIBLE : View.INVISIBLE);
                        countryInfo.setText(isVisibility ? countryInfoBuilder.toString() : " ");

                        return false;
                    }
                });
            }
        });
    }

    private StringBuilder countryInfoBuilder (MapInfo mapInfo) {
        StringBuilder builder = new StringBuilder();
        StringBuilder languageBuilder = languageBuilder(mapInfo);

        builder.append(mapInfo.getName()).append(", ")
                .append(mapInfo.getRegion()).append(", ")
                .append(mapInfo.getSubregion()).append(", ")
                .append("population: ").append(mapInfo.getPopulation()).append(", ")
                .append("area: ").append(mapInfo.getArea()).append(", ")
                .append("languages: ").append(languageBuilder.toString());

        return builder;
    }

    private StringBuilder languageBuilder (MapInfo mapInfo) {
        StringBuilder builderLang = new StringBuilder();
        List<String> languagesList = mapInfo.getLanguages();

        for (int i = 0; i < languagesList.size(); i++) {
            Locale locale = new Locale(languagesList.get(i));
            builderLang.append(locale.getDisplayLanguage());

            if (i < languagesList.size() - 1) {
                builderLang.append(", ");
            }
        }

        return builderLang;
    }
}