package training.weatherapp.RecycleLists.Adapters;

/**
 * Created by hindahmed on 27/09/17.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import training.weatherapp.R;
import training.weatherapp.Volley.Model_12Hour.Model12hour;
import training.weatherapp.Volley.Model_5Days.List;
import training.weatherapp.Volley.Model_5Days.Model5days;

import static training.weatherapp.Activities.ActivityMain.PlaceholderFragment.select_icon;


/**
 * Created by hindahmed on 15/08/17.
 */

public class H_Adapter extends RecyclerView.Adapter<H_Adapter.ViewHolder> {

    private Context context;
    private Model5days m_items;


    public H_Adapter(Context context, Model5days m_items) {
        this.context = context;
        this.m_items = m_items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        List model12hour = m_items.getList().get(position);

        //convert seconds to milliseconds
        Date date = new Date(model12hour.getDt() * 1000L);

        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("hh:mm a");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String java_date = jdf.format(date);

        int icon = select_icon(model12hour.getWeather().get(0).getIcon());

        holder.img_weather.setImageResource(icon);
        holder.tx_hour.setText(java_date);
        holder.tx_temp.setText(model12hour.getMain().getTemp().intValue() + "ْ ");

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tx_hour;
        public ImageView img_weather;
        public TextView tx_temp;

        public ViewHolder(View itemView) {
            super(itemView);
            tx_hour = itemView.findViewById(R.id.tx_hour);
            img_weather = itemView.findViewById(R.id.img_weather);
            tx_temp = itemView.findViewById(R.id.tx_temp);

        }
    }
}