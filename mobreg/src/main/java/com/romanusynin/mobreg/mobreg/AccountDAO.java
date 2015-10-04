package com.romanusynin.mobreg.mobreg;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.romanusynin.mobreg.mobreg.objects.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO extends BaseDaoImpl<Account, Integer> {

    protected AccountDAO(ConnectionSource connectionSource, Class<Account> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Account> getAllAccounts() throws SQLException{
        return this.queryForAll();
    }

    public Account getSelectedAccount() throws SQLException{

        for (Account account : this.queryForAll()) {
            if (account.isSelected()) {
                return account;
            }
        }
        return null;
    }
}
