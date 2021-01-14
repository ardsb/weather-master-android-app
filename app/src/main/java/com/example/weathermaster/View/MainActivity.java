package com.example.weathermaster.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.weathermaster.Adapter.ForecastAdapter;
import com.example.weathermaster.Model.ForecastListItem;
import com.example.weathermaster.Model.ForecastResponse;
import com.example.weathermaster.Model.WeatherResponse;
import com.example.weathermaster.R;
import com.example.weathermaster.Services.ApiClient;
import com.example.weathermaster.Services.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements LocationListener {

    public FusedLocationProviderClient client;


    Button btnSearch, btnCurrent;
    TextView txtTemp, txtPressure, txtHumidity, txtTempMin, txtTempMax, txtFeelsLike,txtAddress;
    EditText search;
    RecyclerView recyclerView;
    LocationManager locationManager;
    private ProgressDialog gpsProgressDialog, apiProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gpsProgressDialog = new ProgressDialog(this);
        gpsProgressDialog.setTitle("Fetching GPS location");
        gpsProgressDialog.setMessage("Please hold tight while we get your current location");
        gpsProgressDialog.setCancelable(false);


        apiProgressBar = new ProgressDialog(this);
        apiProgressBar.setTitle("Please Wait...");
        apiProgressBar.setMessage("Fetching Data");
        apiProgressBar.setCancelable(false);

        if (ContextCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        txtTemp = findViewById(R.id.txtTemp);
        txtPressure = findViewById(R.id.txtPressure);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtTempMin = findViewById(R.id.txtTempMin);
        txtTempMax = findViewById(R.id.txtTempMax);
        txtFeelsLike = findViewById(R.id.txtFeelsLike);
        txtAddress=findViewById(R.id.txtAddress);

        search = findViewById(R.id.txtSearch);
        recyclerView = findViewById(R.id.recycler);

        btnSearch=findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getWeatherData(search.getText().toString().trim());
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


        btnCurrent=findViewById(R.id.btnCurrent);
        btnCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocation();
            }
        });

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        if (!gpsProgressDialog.isShowing()) {
            gpsProgressDialog.show();
        }

        try {
            locationManager=(LocationManager) getApplication().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 5,MainActivity.this);


        }catch (Exception e){

            e.printStackTrace();
        }

    }


    private void getWeatherData(String searchQuery) {
        if (!apiProgressBar.isShowing()) {
            apiProgressBar.show();
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<WeatherResponse> call = apiInterface.getWeatherData(searchQuery);

        call.enqueue(new Callback<WeatherResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                if (apiProgressBar.isShowing()) {
                    apiProgressBar.hide();
                }
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
                getForecastData(searchQuery);


            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

                if (apiProgressBar.isShowing()) {
                    apiProgressBar.hide();
                }
            }

        });


    }

    private void getWeatherDataLatLon(String lat, String lon) {
        if (!apiProgressBar.isShowing()) {
            apiProgressBar.show();
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<WeatherResponse> call = apiInterface.getWeatherDataLatLon(lat,lon);

        call.enqueue(new Callback<WeatherResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (apiProgressBar.isShowing()) {
                    apiProgressBar.hide();
                }
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

                getForecastDataLatLon(lat,lon);

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                if (apiProgressBar.isShowing()) {
                    apiProgressBar.hide();
                }


            }
        });


    }





    private void getForecastData(String name) {
        if (!apiProgressBar.isShowing()) {
            apiProgressBar.show();
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ForecastResponse> call = apiInterface.getForecastData(name);

        call.enqueue(new Callback<ForecastResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {

                if (apiProgressBar.isShowing()) {
                    apiProgressBar.hide();
                }
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
                if (apiProgressBar.isShowing()) {
                    apiProgressBar.hide();
                }

            }
        });
    }


    private void getForecastDataLatLon(String lat,String lon) {
        if (!apiProgressBar.isShowing()) {
            apiProgressBar.show();
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ForecastResponse> call = apiInterface.getForecastDataLatLon(lat,lon);

        call.enqueue(new Callback<ForecastResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (apiProgressBar.isShowing()) {
                    apiProgressBar.hide();
                }
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

                if (apiProgressBar.isShowing()) {
                    apiProgressBar.hide();
                }
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



    @Override
    public void onLocationChanged(@NonNull Location location) {

        if (gpsProgressDialog.isShowing()) {
            gpsProgressDialog.hide();
        }
        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());
        getWeatherDataLatLon(lat, lon);
        Toast.makeText(this,"This is the Longitude"+location.getLongitude()+"This is Latitude"+location.getLatitude(),Toast.LENGTH_SHORT).show();
        try{
            Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            txtAddress.setText(address);
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}