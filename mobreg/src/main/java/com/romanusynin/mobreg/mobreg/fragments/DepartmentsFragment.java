package com.romanusynin.mobreg.mobreg.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.adapters.DepartmentAdapter;
import com.romanusynin.mobreg.mobreg.objects.Department;
import com.romanusynin.mobreg.mobreg.objects.Hospital;

import java.util.ArrayList;

public class DepartmentsFragment extends Fragment {
    private Hospital hospital;
    private ArrayList<Department> departments;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        departments = (ArrayList<Department>) bundle.getSerializable("departments");
        hospital = (Hospital) bundle.getSerializable("hospital");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.departments_layout, parent, false);
        getActivity().setTitle(R.string.title_departments_activity);
        createText(v);
        createCallBtn(v);
        createMapBtn(v);
        createDepartmentList(v);
        return v;
    }

    private void createText(View v){
        TextView nameHospital = (TextView)v.findViewById(R.id.nameHospital);
        nameHospital.setText(hospital.getName());
        TextView addressHospital = (TextView)v.findViewById(R.id.addressHospital);
        addressHospital.setText(hospital.getAddress());
    }

    private void createCallBtn(View v){
        Button callNumber = (Button) v.findViewById(R.id.phoneHospital);
        callNumber.setText("Тел. " + hospital.getNumberPhone());
        callNumber.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hospital.getNumberPhone()));
                startActivity(intent);
            }
        });
        if (hospital.getNumberPhone() == null) {
            callNumber.setVisibility(View.GONE);
        }
    }

    private void createMapBtn(View v){
        Button mapBtn = (Button) v.findViewById(R.id.showOnMap);
        mapBtn.setText(R.string.show_on_map);
        mapBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new MapFragment();
                Bundle b = new Bundle();
                b.putString("address",hospital.getAddress().replace(" ","")+",Омск,Россия");
                b.putString("name", hospital.getName());
                f.setArguments(b);
                ft.replace(R.id.fragmentContainer, f);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void createDepartmentList(View v){
        DepartmentAdapter adapter = new DepartmentAdapter(getActivity(), departments);
        ListView listView = (ListView) v.findViewById(R.id.lvDepartments);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View v, int position,long id){

                Department selectedDepartment = (Department) view.getItemAtPosition(position);
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.DOCTORS);
                b.putSerializable("department", selectedDepartment);
                startLoading(b);
            }

        });
        listView.setAdapter(adapter);
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
