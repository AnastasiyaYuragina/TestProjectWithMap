package com.anastasiyayuragina.testproject;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.screen.country_list.CountryFragment;
import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;
import java.util.ArrayList;
import java.util.List;

public class MyCountryRecyclerViewAdapter extends SupportAnnotatedAdapter implements MyCountryRecyclerViewAdapterBinder {
    private static final int ITEM_TYPE_COUNTRY = 0;
    private static final int ITEM_TYPE_LOADING = 1;
    private final List<Country> countryList = new ArrayList<>();
    private final CountryFragment.OnListFragmentInteractionListener listener;
    private boolean loading;

    public MyCountryRecyclerViewAdapter(Context context, CountryFragment.OnListFragmentInteractionListener listener) {
        super(context);
        this.listener = listener;
    }

    public void addItems(List<Country> items) {
        countryList.addAll(items);
        loading = false;
        notifyDataSetChanged();
    }

    @ViewType(
            layout = R.layout.fragment_country,
            views = {
                    @ViewField(id = R.id.county_name, name = "countryName", type = TextView.class),
                    @ViewField(id = R.id.country_region, name = "countryRegion", type = TextView.class),
                    @ViewField(id = R.id.show_comment, name = "comment", type = TextView.class)
            }
    )
    public final int itemTypeCountry = ITEM_TYPE_COUNTRY;

    @ViewType(
            layout = R.layout.progress_bar_item
    )
    public final int itemTypeLoading = ITEM_TYPE_LOADING;

    @Override
    public int getItemCount() {
        int size = countryList.size();
        return loading ? size + 1 : size;
    }

    @Override
    public int getItemViewType(int position) {
        return countryList.size() == position ? ITEM_TYPE_LOADING : ITEM_TYPE_COUNTRY;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;

        new Handler().post(this::notifyDataSetChanged);
    }

    @Override
    public void bindViewHolder(MyCountryRecyclerViewAdapterHolders.ItemTypeCountryViewHolder vh, int position) {
        Country viewModel = countryList.get(position);

        if (viewModel.getComment() != null && !viewModel.getComment().isEmpty()) {
            vh.comment.setText(vh.itemView.getResources()
                    .getString(R.string.comment, viewModel.getComment()));
        } else {
            vh.comment.setText("");
        }

        vh.countryName.setText(vh.itemView.getResources()
                .getString(R.string.country_name, viewModel.getName()));
        vh.countryRegion.setText(vh.itemView.getResources()
                .getString(R.string.region_name, viewModel.getRegion().getValue()));
        vh.itemView.setOnClickListener(v -> {
            if (null != listener) {
                listener.onListFragmentInteraction(viewModel);
            }
        });
    }

    @Override
    public void bindViewHolder(MyCountryRecyclerViewAdapterHolders.ItemTypeLoadingViewHolder vh, int position) {

    }
}
