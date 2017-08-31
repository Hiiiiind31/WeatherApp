package training.weatherapp.RecycleLists.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import training.weatherapp.Activities.ActivityAddCity;
import training.weatherapp.Activities.ActivityCities;
import training.weatherapp.R;
import training.weatherapp.RoomDatabase.Models.Cities_Model;
import training.weatherapp.Volley.Model_Cities.ModelCity;

import static training.weatherapp.Activities.ActivityMain.db;
import static training.weatherapp.R.id.cities_list;
import static training.weatherapp.R.id.view;

/**
 * Created by hindahmed on 30/08/17.
 */

public class cities_Adapter extends RecyclerView.Adapter<cities_Adapter.ViewHolder> {

    private Context context;
    private ModelCity[] m_items;

    public cities_Adapter(Context context, ModelCity[] m_items) {
        this.context = context;
        this.m_items = m_items;
    }


    @Override
    public cities_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false);
        return new cities_Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cities_Adapter.ViewHolder holder, int position) {
        ModelCity modelCity = m_items[position];
        holder.city_text.setText(modelCity.getLocalizedName() + " , " + modelCity.getCountry().getLocalizedName());

    }

    @Override
    public int getItemCount() {
        return m_items.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView city_text;
        int adapterPosition;

        public ViewHolder(final View itemView) {
            super(itemView);
            city_text = itemView.findViewById(R.id.city_item_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterPosition = getAdapterPosition();
                    Toast.makeText(view.getContext(), m_items[adapterPosition].getKey(), Toast.LENGTH_LONG).show();
                    add_city_to_data_base();

                }


            });


        }

        private void add_city_to_data_base() {
            String city_name = m_items[adapterPosition].getLocalizedName();
            String city_key = m_items[adapterPosition].getKey();
            db.cities_Dao().insertAll(new Cities_Model(city_name, city_key));
            Intent i = new Intent(context, ActivityCities.class);
            context.startActivity(i);
        }


    }
}
