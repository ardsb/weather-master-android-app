package com.example.weathermaster.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.weathermaster.Model.ForecastListItem;
import com.example.weathermaster.Model.Main;
import com.example.weathermaster.R;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private final List<ForecastListItem> forecast;


    public static class ForecastViewHolder extends RecyclerView.ViewHolder {

        TextView txtForecastPressure,txtForecastHumidity,txtForecastTempMin,txtForecastTempMax,txtForecastFeelsLike,txtForecastDate;





        public ForecastViewHolder(View v) {
            super(v);



            txtForecastPressure=v.findViewById(R.id.txtForecastPressure);
            txtForecastHumidity=v.findViewById(R.id.txtForecastHumidity);
            txtForecastTempMin=v.findViewById(R.id.txtForecastTempMin);
            txtForecastTempMax=v.findViewById(R.id.txtForecastTempMax);
            txtForecastFeelsLike=v.findViewById(R.id.txtForecastFeelsLike);

            txtForecastDate=v.findViewById(R.id.txtForecastFeel);


        }
    }

    public ForecastAdapter(List<ForecastListItem> forecast, int rowLayout, Context context) {
        this.forecast = forecast;

    }

    @Override
    public ForecastAdapter.ForecastViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forcast_layout, parent, false);
        return new ForecastViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ForecastViewHolder holder, final int position) {


        String forecastListItem=forecast.get(position).getDate();
        holder.txtForecastDate.setText(forecastListItem);


        Main main = forecast.get(position).getMain();
        holder.txtForecastPressure.setText("Pressure: " + main.getPressure());
        holder.txtForecastHumidity.setText("Humidity: " + main.getHumidity()+"%");
        holder.txtForecastTempMin.setText("Temp Min: " + main.getTempMin()+"C");
        holder.txtForecastTempMax.setText("Temp Max: " + main.getTempMax()+"C");
        holder.txtForecastFeelsLike.setText("Feels Like: "+ main.getFeelsLike()+"C");





    }

    @Override
    public int getItemCount() {
        return forecast.size();
    }
}





