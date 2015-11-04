package com.romanusynin.mobreg.mobreg.fragments;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.Hospital;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap map;
    private ArrayList<Hospital> hospitals;
    private String name;
    private LatLng pos;
    private boolean zoomMarker;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        hospitals = (ArrayList<Hospital>)bundle.getSerializable("hospitals");
        if (hospitals!=null) {
            if (hospitals.size() == 1){
                zoomMarker = true;
            }else{
                zoomMarker = false;
            }
        }else{
            zoomMarker = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.map_fragment_layout, parent, false);
        createMap(v, savedInstanceState);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        this.map = map;
        for (Hospital hospital: hospitals){
            name = hospital.getName();
            String address = hospital.getAddress().replace(" ","")+",Омск,Россия";
            new CreateHospitalMarker().execute(address);
        }
        if (!zoomMarker) {
            moveToMyLocation();
        }
    }

    private void createMap(View v, Bundle savedInstanceState){
        MapView mapView = (MapView)v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        MapsInitializer.initialize(this.getActivity());
        mapView.getMapAsync(this);
    }

    private void moveToMyLocation(){
        LocationManager locMan =
                (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria crit = new Criteria();
        Location loc = null;
        try {
             loc = locMan.getLastKnownLocation(locMan.getBestProvider(crit, false));
        }catch(SecurityException e){
            e.printStackTrace();
        }
        if(loc!=null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(loc.getLatitude(), loc.getLongitude()), 14f));
        }
    }

    private JSONObject getLocationFormGoogle(String placesName) {
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

    private  LatLng getLatLng(JSONObject jsonObject) {
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
            if (location != null) {
                pos = getLatLng(location);
                if (zoomMarker) {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 14f));
                }
                map.addMarker(new MarkerOptions().position(pos).title(name));
            }
        }
    }

}
