package com.example.weathermaster.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.weathermaster.Adapter.ForecastAdapter;
import com.example.weathermaster.Model.ForecastListItem;
import com.example.weathermaster.Model.ForecastResponse;
import com.example.weathermaster.Model.WeatherResponse;
import com.example.weathermaster.R;
import com.example.weathermaster.Services.ApiClient;
import com.example.weathermaster.Services.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnSearch, btnCurrent;
    TextView txtTemp, txtPressure, txtHumidity, txtTempMin, txtTempMax, txtFeelsLike;
    EditText search;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSearch = findViewById(R.id.btnSearch);
//        btnCurrent=findViewById(R.id.btnCurrent);


        txtTemp = findViewById(R.id.txtTemp);
        txtPressure = findViewById(R.id.txtPressure);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtTempMin = findViewById(R.id.txtTempMin);
        txtTempMax = findViewById(R.id.txtTempMax);
        txtFeelsLike = findViewById(R.id.txtFeelsLike);

        search = findViewById(R.id.txtSearch);
        recyclerView = findViewById(R.id.recycler);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//          Calling the API
                getWeatherData(search.getText().toString().trim());
                getForecastData(search.getText().toString().trim());


                String val = search.getText().toString().trim();

                if (!TextUtils.isEmpty(val)) {

                } else {
                    search.setError("Value can't be empty");
                }

            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void getWeatherData(String name) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<WeatherResponse> call = apiInterface.getWeatherData(name);

        call.enqueue(new Callback<WeatherResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                if (response.isSuccessful()) {
                    txtTemp.setText("Temp " + response.body().getMain().getTemp() + "C");
                    txtFeelsLike.setText("Feels Like: " + response.body().getMain().getFeelsLike() + "C");
                    txtHumidity.setText("Humidity: " + response.body().getMain().getHumidity() + "%");
                    txtTempMin.setText(" Temp Min: " + response.body().getMain().getTempMin() + "C");
                    txtTempMax.setText("Temp Max: " + response.body().getMain().getTempMax() + "C");
                    txtPressure.setText("Pressure: " + response.body().getMain().getPressure());

                } else {

                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {


            }
        });


    }


    private void getForecastData(String name) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ForecastResponse> call = apiInterface.getForecastData(name);

        call.enqueue(new Callback<ForecastResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {

                if (response.isSuccessful()) {
                    List<ForecastListItem> forecastListItems = response.body().getList();

                    ForecastAdapter adapter = new ForecastAdapter(forecastListItems, R.layout.forcast_layout, getApplicationContext());

                    recyclerView.setAdapter(adapter);


                } else {

                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {


            }
        });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}