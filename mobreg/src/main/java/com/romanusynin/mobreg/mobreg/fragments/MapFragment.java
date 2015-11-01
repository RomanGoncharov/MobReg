package com.romanusynin.mobreg.mobreg.fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.Hospital;
import com.romanusynin.mobreg.mobreg.objects.Parser;
import com.romanusynin.mobreg.mobreg.objects.Region;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap map;
    private String address;
    private String name;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        address = bundle.getString("address");
        name = bundle.getString("name");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.map_fragment_layout, parent, false);
        MapView mapView = (MapView)v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        MapsInitializer.initialize(this.getActivity());
        mapView.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        this.map = map;
        new CreateHospitalMarker().execute(address);
    }

    public JSONObject getLocationFormGoogle(String placesName) {
        OkHttpClient client = new OkHttpClient();
        StringBuilder stringBuilder = new StringBuilder();
        Request request = new Request.Builder()
                .url("http://maps.google.com/maps/api/geocode/json?address=" + placesName + "&ka&sensor=false")
                .build();
        String responseHTML="";
        try{
        Response response = client.newCall(request).execute();
        responseHTML = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringBuilder.append(responseHTML);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public  LatLng getLatLng(JSONObject jsonObject) {
        Double lon = 0.0;
        Double lat = 0.0;
        try {
            lon = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");
            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new LatLng(lat,lon);
    }

    class CreateHospitalMarker extends AsyncTask <String, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            return getLocationFormGoogle(params[0]);
        }

        @Override
        protected void onPostExecute(JSONObject location) {
            super.onPostExecute(location);
            if (location == null) {

            }else{
                LatLng pos = getLatLng(location);
                map.addMarker(new MarkerOptions().position(pos).title(name));
            }
        }
    }

}
