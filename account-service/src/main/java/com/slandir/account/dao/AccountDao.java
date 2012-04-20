package com.slandir.account.dao;

import com.slandir.account.model.Account;

public interface AccountDao {
    void save(Account account);
    Account fetch(String email);
}
