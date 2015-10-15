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
import com.romanusynin.mobreg.mobreg.activities.DoctorsActivity;
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
        TextView nameHospital = (TextView)v.findViewById(R.id.nameHospital);
        nameHospital.setText(hospital.getName());
        TextView addressHospital = (TextView)v.findViewById(R.id.addressHospital);
        addressHospital.setText(hospital.getAddress());
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
        DepartmentAdapter adapter = new DepartmentAdapter(getActivity(), departments);
        ListView listView = (ListView) v.findViewById(R.id.lvDepartments);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View v, int position,long id){

                Department selectedDepartment = (Department) view.getItemAtPosition(position);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.DOCTORS);
                b.putSerializable("department", selectedDepartment);
                f.setArguments(b);
                ft.replace(R.id.fragmentContainer, f);
                ft.addToBackStack(null);
                ft.commit();
            }

        });
        listView.setAdapter(adapter);
        return v;
    }
}
