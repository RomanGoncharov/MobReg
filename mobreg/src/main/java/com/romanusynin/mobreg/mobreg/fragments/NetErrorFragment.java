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

public class NetErrorFragment extends Fragment {
    private Bundle b;
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        b = bundle;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.error_net_layout, parent, false);
        getActivity().setTitle(R.string.title_net_error_fragment);
        createRefreshBtn(v);
        return v;
    }

    private void createRefreshBtn(View v){
        Button reloadButton = (Button) v.findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                f.setArguments(b);
                if (b.getBoolean("list",false)) {
                    ft.replace(R.id.listContainer, f);
                }else {
                    ft.replace(R.id.fragmentContainer, f);
                }
                ft.commit();
            }
        });
    }
}
