package com.romanusynin.mobreg.mobreg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.romanusynin.mobreg.mobreg.R;

public class SelectRegionFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.select_region_layout, parent, false);
        getActivity().setTitle(R.string.title_regions_activity);
        Button buttonOmsk = (Button) v.findViewById(R.id.buttonOmsk);
        buttonOmsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingFragment fragment = new LoadingFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", LoadingFragment.HOSPITALS);
                fragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.fragmentContainer, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        Button buttonOmskState = (Button) v.findViewById(R.id.buttonOmskState);
        buttonOmskState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingFragment fragment = new LoadingFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", LoadingFragment.REGIONS);
                fragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.fragmentContainer, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return v;
    }
}
