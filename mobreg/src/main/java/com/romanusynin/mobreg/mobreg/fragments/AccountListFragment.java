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
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.activities.AddOrEditAccountActivity;
import com.romanusynin.mobreg.mobreg.objects.Account;
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

    @Override
    public  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.accounts_title);

        try {
            accounts = (ArrayList<Account>) HelperFactory.getHelper().getAccountDAO().getAllAccount();
        }
        catch (SQLException e){
            Log.e(TAG, e.toString());
        }
        adapter = new AccountAdapter(accounts);
    }

    private class AccountAdapter extends ArrayAdapter<Account> {

        public AccountAdapter(ArrayList<Account> accounts) {
            super(getActivity(), 0, accounts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Account account = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview, parent, false);
            }
            TextView tvTitle = (TextView) convertView.findViewById(R.id.text1);
            TextView tvSelected = (TextView) convertView.findViewById(R.id.text2);
            tvTitle.setText(account.getTitle());
            tvSelected.setText(Boolean.toString(account.isSelected()));
            return convertView;
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}



