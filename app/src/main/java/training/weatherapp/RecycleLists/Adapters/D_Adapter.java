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
import training.weatherapp.RecycleLists.Models.Temp_model_days;

/**
 * Created by hindahmed on 16/08/17.
 */

public class D_Adapter extends RecyclerView.Adapter<D_Adapter.ViewHolder> {


    private Context context;
    private List<Temp_model_days> m_items;


    public D_Adapter(Context context, List<Temp_model_days> m_items) {
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
        holder.tx_time.setText(m_items.get(position).getD_Date());
        holder.tx_temp_mm.setText(m_items.get(position).getD_Max_Temp()+"/"+m_items.get(position).getD_Min_Temp());

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


