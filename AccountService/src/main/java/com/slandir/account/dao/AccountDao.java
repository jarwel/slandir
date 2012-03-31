package com.slandir.account.dao;

import com.google.common.collect.Maps;
import com.slandir.account.model.Account;

import java.util.Map;

public class AccountDao {

    Map<String, Account> accounts = Maps.newHashMap();
    
    public void save(Account account) {
        accounts.put(account.getEmail(), account);
    }

    public Account fetch(String email) {
        return accounts.get(email);
    }

}
