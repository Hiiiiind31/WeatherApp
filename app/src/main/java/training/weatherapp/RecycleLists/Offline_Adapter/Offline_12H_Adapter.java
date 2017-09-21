package training.weatherapp.RecycleLists.Offline_Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import training.weatherapp.R;
import training.weatherapp.RecycleLists.Adapters.H_Adapter;
import training.weatherapp.RecycleLists.offline_Models.Offline_Model_12Hours;
import training.weatherapp.RecycleLists.offline_Models.Offline_model_5Days;

import static training.weatherapp.Activities.ActivityMain.PlaceholderFragment.select_icon;

/**
 * Created by hindahmed on 12/09/17.
 */

public class Offline_12H_Adapter extends  RecyclerView.Adapter<Offline_12H_Adapter.ViewHolder> {


    private Context context;
    private List<Offline_Model_12Hours> m_items;

    public Offline_12H_Adapter(Context context,List <Offline_Model_12Hours> m_items) {
        this.context = context;
        this.m_items = m_items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_list_item, parent, false);
        return new Offline_12H_Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

          Long echo = Long.valueOf(m_items.get(position).getTime());
        //convert seconds to milliseconds
        Date date = new Date(echo*1000L);

        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("hh:mm a");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String java_date = jdf.format(date);

        int icon = select_icon(m_items.get(position).getImage());
        holder.img_weather.setImageResource(icon);
        holder.tx_hour.setText(java_date);
        holder.tx_temp.setText(m_items.get(position).getTemp()+"ْ ");
    }

    @Override
    public int getItemCount() {
        return m_items.size();
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
