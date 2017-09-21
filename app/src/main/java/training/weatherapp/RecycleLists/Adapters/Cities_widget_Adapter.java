package training.weatherapp.RecycleLists.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import training.weatherapp.R;
import training.weatherapp.RoomDatabase.Models.Cities_Model;

/**
 * Created by hindahmed on 22/08/17.
 */

public class Cities_widget_Adapter extends RecyclerView.Adapter<Cities_widget_Adapter.ViewHolder> {

    private Context context ;
    private List<Cities_Model> m_items;

    public Cities_widget_Adapter(Context context, List<Cities_Model> m_items) {
        this.context = context;
        this.m_items = m_items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_list_item, parent, false);
        return new Cities_widget_Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tx_city.setText(m_items.get(position).getCites());

    }

    @Override
    public int getItemCount() {
        return m_items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tx_city;

        public ViewHolder(View itemView) {
            super(itemView);
            tx_city= itemView.findViewById(R.id.city_item);

        }
    }
}
