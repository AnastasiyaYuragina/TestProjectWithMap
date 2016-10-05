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

        return new ViewHolder(view);
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
            holder.comment.setText("Comment: " + viewModel.getComment());
        } else {
            holder.comment.setText("");
        }

        holder.country = viewModel;
        holder.countryName.setText("Country: " + viewModel.getName());
        holder.countryRegion.setText("Region: " + viewModel.getRegion().getValue());
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
        final TextView countryName;
        final TextView countryRegion;
        Country country;
        final TextView comment;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            countryName = (TextView) view.findViewById(R.id.county_name);
            countryRegion = (TextView) view.findViewById(R.id.country_region);
            comment = (TextView) view.findViewById(R.id.show_comment);
        }

        @Override
        public java.lang.String toString() {
            return super.toString() + " '" + countryRegion.getText() + "'";
        }
    }
}
