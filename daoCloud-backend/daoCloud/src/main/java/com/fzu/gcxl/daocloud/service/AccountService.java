package com.fzu.gcxl.daocloud.service;

import com.fzu.gcxl.daocloud.entity.Account;

public interface AccountService {

    Account queryAccountByPhone(String phone);

    int addAccount(Account account);
}
