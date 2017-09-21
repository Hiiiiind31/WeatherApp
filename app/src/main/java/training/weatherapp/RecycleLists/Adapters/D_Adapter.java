package training.weatherapp.RecycleLists.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

import training.weatherapp.R;
;
import training.weatherapp.Volley.Model_5Days.Model5days;

import static training.weatherapp.Activities.ActivityMain.PlaceholderFragment.select_icon;


/**
 * Created by hindahmed on 16/08/17.
 */

public class D_Adapter extends RecyclerView.Adapter<D_Adapter.ViewHolder> {


    private Context context;
    private Model5days m_items;


    public D_Adapter(Context context, Model5days m_items) {
        this.context = context;
        this.m_items = m_items;
    }

    @Override
    public D_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_list_item, parent, false);
        return new D_Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(D_Adapter.ViewHolder holder, int position) {

        Integer epochDate = m_items.getDailyForecasts().get(position).getEpochDate();

        //convert seconds to milliseconds
        Date date = new Date(epochDate*1000L);
        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("EE, MMM d");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String java_date = jdf.format(date);

        int icon = select_icon(m_items.getDailyForecasts().get(position).getDay().getIcon());

        holder.img_weather2.setImageResource(icon);
        holder.tx_time.setText(java_date);
        holder.tx_temp_mm.setText(m_items.getDailyForecasts().get(position).getTemperature().getMaximum().getValue().intValue()+" ْ"+"/"+m_items.getDailyForecasts().get(position).getTemperature().getMinimum().getValue().intValue()+" ْ");


    }

    @Override
    public int getItemCount() {
        return m_items.getDailyForecasts().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tx_time;
        public ImageView img_weather2;
        public TextView tx_temp_mm;

        public ViewHolder(View itemView) {
            super(itemView);
            tx_time = itemView.findViewById(R.id.tx_time);
            img_weather2 = itemView.findViewById(R.id.img_weather2);
            tx_temp_mm = itemView.findViewById(R.id.tx_temp_MM);

        }
    }
}


