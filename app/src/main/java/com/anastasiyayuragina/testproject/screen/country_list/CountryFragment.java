package com.anastasiyayuragina.testproject.screen.country_list;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.anastasiyayuragina.testproject.EndlessRecyclerOnScrollListener;
import com.anastasiyayuragina.testproject.InternetConnectionObservable;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.MyCountryRecyclerViewAdapter;
import com.anastasiyayuragina.testproject.R;
import java.util.List;
import java.util.Observer;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CountryFragment extends Fragment implements CountriesMvp.View {
    private static final java.lang.String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener listener;
    private MyCountryRecyclerViewAdapter adapter;
    private CountriesMvp.Presenter presenter;
    private ProgressDialog progressDialog;

    public static CountryFragment newInstance(int columnCount) {
        CountryFragment fragment = new CountryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_list, container, false);
        presenter = new CountriesPresenter(this);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            RecyclerView.LayoutManager layoutManager = mColumnCount <= 1 ? new LinearLayoutManager(context)
                    : new GridLayoutManager(context, mColumnCount);

            recyclerView.setLayoutManager(layoutManager);

            adapter = new MyCountryRecyclerViewAdapter(listener);
            recyclerView.setAdapter(adapter);

            setProgressDialog(context, presenter);

            recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) layoutManager) {
                @Override
                public void onLoadMore() {
                    if (presenter.isDataLoaded()) {
                        progressDialog.dismiss();
                    }
                    presenter.loadData();
                }
            });
        }

        return view;
    }

    private void setProgressDialog (Context context, CountriesMvp.Presenter presenter) {
        progressDialog = new ProgressDialog(context);
        if (!presenter.isDataLoaded()) {
            progressDialog.setProgressStyle(R.layout.progress_bar_item);
            progressDialog.show();

            if (!isInternetConnection(context)) {
                Toast.makeText(context, "no internet", Toast.LENGTH_SHORT).show();
            } else {
                presenter.loadData();
            }
        }
    }

    private boolean isInternetConnection(Context context) {
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            isConnected = activeNetwork.isConnectedOrConnecting();
        }

        return isConnected;
    }

    @Override
    public void onStart() {
        super.onStart();
        InternetConnectionObservable.getInstance().addObserver((Observer) presenter);
    }

    @Override
    public void onStop() {
        super.onStop();
        progressDialog.dismiss();
        InternetConnectionObservable.getInstance().deleteObservers();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void setData(List<Country> countryList) {
        adapter.addItems(countryList);
        progressDialog.dismiss();
    }

    @Override
    public void showLoadMore() {
        adapter.setLoading(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Country item);
    }
}
