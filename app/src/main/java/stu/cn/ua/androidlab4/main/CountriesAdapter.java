package stu.cn.ua.androidlab4.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import stu.cn.ua.androidlab4.R;
import stu.cn.ua.androidlab4.model.Country;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ShipViewHolder>
        implements View.OnClickListener{

    private List<Country> countries = Collections.emptyList();
    private ShipListener listener;

    public CountriesAdapter(ShipListener listener) {
        setHasStableIds(true);
        this.listener = listener;
    }

    public void setCountries(List<Country> countries){
        this.countries = countries;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Country country = (Country) v.getTag();
        listener.onShipChosen(country);
    }

    @Override
    public long getItemId(int position) {
        return countries.get(position).getId().hashCode();
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @NonNull
    @Override
    public ShipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.item_ship, parent, false);
        return new ShipViewHolder(root, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.nameTextView.setText(country.getName());
        holder.typeTextView.setText(country.getIso2code());
        holder.itemView.setTag(country);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    static class ShipViewHolder extends RecyclerView.ViewHolder{
        private TextView typeTextView;
        private TextView nameTextView;
        public ShipViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            itemView.setOnClickListener(listener);
        }
    }

    public interface ShipListener {
        void onShipChosen(Country country);
    }
}
