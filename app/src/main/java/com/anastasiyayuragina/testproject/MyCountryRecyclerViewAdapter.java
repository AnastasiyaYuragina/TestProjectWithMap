package com.anastasiyayuragina.testproject;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.anastasiyayuragina.testproject.jsonCountriesClasses.Country;
import com.anastasiyayuragina.testproject.screen.country_list.CountryFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCountryRecyclerViewAdapter extends RecyclerView.Adapter<MyCountryRecyclerViewAdapter.ViewHolder> {
    private static final int ITEM_TYPE_COUNTRY = 0;
    private static final int ITEM_TYPE_LOADING = 1;
    private final List<Country> countryList = new ArrayList<>();
    private final CountryFragment.OnListFragmentInteractionListener listener;
    private boolean loading;

    public MyCountryRecyclerViewAdapter(CountryFragment.OnListFragmentInteractionListener listener) {
        this.listener = listener;
    }

    public void addItems(List<Country> items) {
        countryList.addAll(items);
        loading = false;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(viewType == ITEM_TYPE_COUNTRY){
            view = inflater.inflate(R.layout.fragment_country, parent, false);
        }else if(viewType == ITEM_TYPE_LOADING){
            view = inflater.inflate(R.layout.progress_bar_item, parent, false);
        }else {
            throw new IllegalArgumentException("Wrong view type");
        }

        return new ViewHolder(view, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return countryList.size() == position ? ITEM_TYPE_LOADING : ITEM_TYPE_COUNTRY;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(getItemViewType(position) == ITEM_TYPE_LOADING) {
            return;
        }

        Country viewModel = countryList.get(position);

        if (viewModel.getComment() != null && !viewModel.getComment().isEmpty()) {
            holder.comment.setText(holder.view.getResources()
                    .getString(R.string.comment, viewModel.getComment()));
        } else {
            holder.comment.setText("");
        }

        holder.country = viewModel;
        holder.countryName.setText(holder.view.getResources()
                .getString(R.string.country_name, viewModel.getName()));
        holder.countryRegion.setText(holder.view.getResources()
                .getString(R.string.region_name, viewModel.getRegion().getValue()));
        holder.view.setOnClickListener(v -> {
            if (null != listener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                listener.onListFragmentInteraction(holder.country);
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = countryList.size();
        return loading ? size + 1 : size;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;

        new Handler().post(this::notifyDataSetChanged);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        Country country;
        @BindView(R.id.county_name) TextView countryName;
        @BindView(R.id.country_region) TextView countryRegion;
        @BindView(R.id.show_comment) TextView comment;

        ViewHolder(View view, int type) {
            super(view);
            this.view = view;

            if (type == ITEM_TYPE_COUNTRY) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public java.lang.String toString() {
            return super.toString() + " '" + countryRegion.getText() + "'";
        }
    }
}
