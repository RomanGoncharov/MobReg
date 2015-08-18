package com.romanusynin.mobreg.mobreg.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.activities.AddOrEditAccountActivity;
import com.romanusynin.mobreg.mobreg.activities.DoctorsActivity;
import com.romanusynin.mobreg.mobreg.objects.Account;
import com.romanusynin.mobreg.mobreg.objects.Department;
import com.romanusynin.mobreg.mobreg.objects.HelperFactory;

import java.sql.SQLException;
import java.util.ArrayList;

public class AccountListFragment extends Fragment {
    private ArrayList<Account> accounts= new ArrayList<Account>();
    private static final String TAG = "AccountListFragment";
    private SwipeMenuListView listView;
    private AccountAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup parent, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.accounts_fragment, parent, false);

        try {
            accounts = (ArrayList<Account>) HelperFactory.getHelper().getAccountDAO().getAllAccount();
        }
        catch (SQLException e){
            Log.e(TAG, e.toString());
        }
        adapter = new AccountAdapter(accounts);

        FloatingActionButton addAccountButton = (FloatingActionButton) v.findViewById(R.id.addAccountButton);
        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddOrEditAccountActivity.class);
                startActivity(intent);
            }
        });
        listView = (SwipeMenuListView) v.findViewById(R.id.accountsListView);
        listView.setAdapter(adapter);
        listView.setEmptyView(v.findViewById(R.id.accountListEmptyText));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View v, int position, long id) {
                Account selectedAccount = (Account) view.getItemAtPosition(position);
                for (Account a : accounts) {
                    boolean isChanged = false;
                    if(a.getId() != selectedAccount.getId()){
                        if(a.isSelected()){
                            a.setIsSelected(false);
                            isChanged = true;
                        }
                    }
                    else{
                        a.setIsSelected(true);
                        isChanged = true;
                    }
                    if (isChanged){
                        try {
                            HelperFactory.getHelper().getAccountDAO().update(a);
                        }
                        catch (SQLException e){
                            Log.e(TAG, e.toString());
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                editItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                editItem.setWidth(dp2px(90));
                editItem.setIcon(R.drawable.ic_mode_edit_white_24dp);
                menu.addMenuItem(editItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setIcon(R.drawable.ic_delete_white_24dp);
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Account a = adapter.getItem(position);
                switch (index) {
                    case 0:
                        Intent intent = new Intent(getActivity(), AddOrEditAccountActivity.class);
                        intent.putExtra("selectedAccount", a);
                        startActivity(intent);
                        break;
                    case 1:
                        accounts.remove(position);
                        adapter.notifyDataSetChanged();
                        try {
                            HelperFactory.getHelper().getAccountDAO().delete(a);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        return v;
    }

    private class AccountAdapter extends ArrayAdapter<Account> {

        public AccountAdapter(ArrayList<Account> accounts) {
            super(getActivity(), 0, accounts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Account account = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.account_list_item, parent, false);
            }
            TextView tvTitle = (TextView) convertView.findViewById(R.id.text1);
            TextView tvNumberPolicy = (TextView) convertView.findViewById(R.id.text2);
            ImageView cBIsSelectedAccount = (ImageView) convertView.findViewById(R.id.checkboxSelectedAccount);
            if (account.isSelected()) {
                cBIsSelectedAccount.setImageResource(R.drawable.ic_star_black_36dp);
            }
            else{
                cBIsSelectedAccount.setImageResource(R.drawable.ic_star_outline_black_36dp);
            }
            tvTitle.setText(account.getTitle());
            tvNumberPolicy.setText(account.getNumberPolicy());
            return convertView;
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}



