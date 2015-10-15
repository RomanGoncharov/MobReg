package com.romanusynin.mobreg.mobreg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.adapters.RegionAdapter;
import com.romanusynin.mobreg.mobreg.objects.Region;

import java.util.ArrayList;

public class RegionsFragment extends Fragment {
    private ArrayList <Region> regions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.regions_layout, container, false);
        getActivity().setTitle(R.string.title_regions_activity);
        RegionAdapter adapter = new RegionAdapter(getActivity(), this.regions);
        ListView listView = (ListView) v.findViewById(R.id.lvMain);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View v, int position,long id){
                Region selectedRegion = (Region) view.getItemAtPosition(position);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.HOSPITALS);
                b.putSerializable("region", selectedRegion);
                f.setArguments(b);
                ft.replace(R.id.fragmentContainer, f);
                ft.addToBackStack(null);
                ft.commit();
            }

        });
        listView.setAdapter(adapter);
        return v;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        regions = (ArrayList<Region>) bundle.getSerializable("regions");
    }
}
