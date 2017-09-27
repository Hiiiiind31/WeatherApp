package training.weatherapp.RecycleLists.Offline_Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import training.weatherapp.R;
import training.weatherapp.RecycleLists.Adapters.Cities_View_Adapter;
import training.weatherapp.RecycleLists.Adapters.D_Adapter;
import training.weatherapp.RecycleLists.offline_Models.Offline_model_5Days;
import training.weatherapp.RoomDatabase.Models.Cities_Model;


/**
 * Created by hindahmed on 12/09/17.
 */

public class Offline_5D_Adapter_ extends RecyclerView.Adapter<Offline_5D_Adapter_.ViewHolder> {


    private Context context;
    private List<Offline_model_5Days> m_items;

    public Offline_5D_Adapter_(Context context, List<Offline_model_5Days> m_items) {
        this.context = context;
        this.m_items = m_items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_list_item, parent, false);
        return new Offline_5D_Adapter_.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int epochDateTime = Integer.parseInt(m_items.get(position).getD_Date());

        //convert seconds to milliseconds
        Date date = new Date(epochDateTime * 1000L);
        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("EE, MMM d");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String java_date = jdf.format(date);

//        int icon = select_icon(m_items.get(position).getD_Image());
//
//        holder.img_weather2.setImageResource(icon);
        holder.tx_time.setText(java_date);
        holder.tx_temp_mm.setText(m_items.get(position).getD_Max_Temp() + " ْ " + "/" + m_items.get(position).getD_Min_Temp() + " ْ ");

    }

    @Override
    public int getItemCount() {
        return m_items.size();
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
