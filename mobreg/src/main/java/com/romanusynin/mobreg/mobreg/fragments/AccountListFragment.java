package com.romanusynin.mobreg.mobreg.fragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.Account;
import com.romanusynin.mobreg.mobreg.objects.HelperFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountListFragment extends Fragment {
    private ArrayList<Account> accounts= new ArrayList<Account>();
    private static final String TAG = "AccountListFragment";
    private SwipeMenuListView listView;
    private AccountAdapter adapter;

    private List<Account> getAllAccounts(){
        try {
            return HelperFactory.getHelper().getAccountDAO().getAllAccounts();
        }
        catch (SQLException e){
            Log.e(TAG, e.toString());
            return new ArrayList<Account>();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle(R.string.accounts_title);
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_accounts_fragment, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setQueryHint(getString(R.string.search_text));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup parent, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.accounts_fragment, parent, false);

        accounts = (ArrayList<Account>) getAllAccounts();
        adapter = new AccountAdapter(accounts);

        FloatingActionButton addAccountButton = (FloatingActionButton) v.findViewById(R.id.addAccountButton);
        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag_account_detail = new AccountFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.fragmentContainer, frag_account_detail);
                fm.addToBackStack("ADD_ACCOUNT");
                fm.commit();
            }
        });

        listView = (SwipeMenuListView) v.findViewById(R.id.accountsListView);
        View footerView =  ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.accounts_list_footer_layout, null, false);
        listView.addFooterView(footerView);
        listView.setAdapter(adapter);
        listView.setEmptyView(v.findViewById(R.id.accountListEmptyText));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View v, int position, long id) {
                Account selectedAccount = (Account) view.getItemAtPosition(position);
                Account favorite_account_in_db = null;
                try {
                    favorite_account_in_db =HelperFactory.getHelper().getAccountDAO().getSelectedAccount();
                } catch (SQLException e) {
                    Log.e(TAG, e.toString());
                }

                //update in db
                if (favorite_account_in_db != null && selectedAccount.getId() != favorite_account_in_db.getId()){
                    try {
                        favorite_account_in_db.setIsSelected(false);
                        HelperFactory.getHelper().getAccountDAO().update(favorite_account_in_db);
                    } catch (SQLException e) {
                        Log.e(TAG, e.toString());
                    }
                }

                //update in list
                for (Account a : accounts) {
                    boolean isChanged = false;
                    if (a.getId() != selectedAccount.getId()) {
                        if (a.isSelected()) {
                            a.setIsSelected(false);
                            isChanged = true;
                        }
                    } else {
                        a.setIsSelected(true);
                        isChanged = true;
                    }
                    if (isChanged) {
                        try {
                            HelperFactory.getHelper().getAccountDAO().update(a);
                        } catch (SQLException e) {
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
                        Fragment frag_account_detail = new AccountFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("selectedAccount", a);
                        frag_account_detail.setArguments(bundle);
                        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                        fm.replace(R.id.fragmentContainer, frag_account_detail);
                        fm.addToBackStack("ADD_ACCOUNT");
                        fm.commit();

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

    public void onResume(){
        super.onResume();
        getActivity().setTitle(R.string.accounts_title);

        accounts.clear();
        accounts.addAll(getAllAccounts());
        adapter.notifyDataSetChanged();

    }

    private class AccountAdapter extends ArrayAdapter<Account> {
        private Filter accountsFilter;

        public AccountAdapter(ArrayList<Account> accounts) {
            super(getActivity(), 0, accounts);
        }

        @Override
        public Filter getFilter() {
            if (accountsFilter == null)
                accountsFilter = new AccountFilter<Account>();
            return accountsFilter;
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

        private class AccountFilter<T> extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence chars) {
                String filterSeq = chars.toString().toLowerCase();
                FilterResults result = new FilterResults();
                if (filterSeq.length() > 0) {
                    ArrayList<Account> filter = new ArrayList<Account>();

                    for (Account account : getAllAccounts()) {
                        if (account.getTitle().toLowerCase().contains(filterSeq) ||
                                account.getNumberPolicy().toLowerCase().contains(filterSeq))
                            filter.add(account);
                    }
                    result.count = filter.size();
                    result.values = filter;
                } else {
                    synchronized (this) {
                        result.values = getAllAccounts();
                        result.count = getAllAccounts().size();
                    }
                }
                return result;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                ArrayList<T> filtered = (ArrayList<T>) results.values;
                notifyDataSetChanged();
                clear();
                for (int i = 0, l = filtered.size(); i < l; i++)
                    add((Account) filtered.get(i));
                notifyDataSetInvalidated();
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}



