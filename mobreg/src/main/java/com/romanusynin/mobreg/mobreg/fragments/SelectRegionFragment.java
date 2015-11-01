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
        createOmskBtn(v);
        createRegionBtn(v);
        return v;
    }

    private void createRegionBtn(View v){
        Button buttonOmskState = (Button) v.findViewById(R.id.buttonOmskState);
        buttonOmskState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", LoadingFragment.REGIONS);
                startLoading(bundle);
            }
        });
    }

    private void createOmskBtn(View v){
        Button buttonOmsk = (Button) v.findViewById(R.id.buttonOmsk);
        buttonOmsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", LoadingFragment.HOSPITALS);
                startLoading(bundle);
            }
        });
    }

    private void startLoading(Bundle bundle){
        LoadingFragment fragment = new LoadingFragment();
        fragment.setArguments(bundle);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
