package training.weatherapp.RecycleLists.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import training.weatherapp.R;
import training.weatherapp.RecycleLists.Models.Temp_Model_hours;


/**
 * Created by hindahmed on 15/08/17.
 */

public class H_Adapter extends RecyclerView.Adapter<H_Adapter.ViewHolder> {

    private Context context;
    private List<Temp_Model_hours> m_items;


    public H_Adapter(Context context, List<Temp_Model_hours> m_items) {
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
        holder.tx_hour.setText(m_items.get(position).getTime());
        holder.tx_temp.setText(m_items.get(position).getTemp());


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
