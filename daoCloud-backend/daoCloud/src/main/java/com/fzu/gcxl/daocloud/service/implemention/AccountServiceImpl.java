package com.fzu.gcxl.daocloud.service.implemention;

import com.fzu.gcxl.daocloud.entity.Account;
import com.fzu.gcxl.daocloud.mapper.AccountMapper;
import com.fzu.gcxl.daocloud.mapper.UserMapper;
import com.fzu.gcxl.daocloud.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;

    @Override
    public Account queryAccountByPhone(String phone) {
        return accountMapper.queryAccountByPhone(phone);
    }

    @Override
    public int updatePassword(Account account) {
        return accountMapper.updatePassword(account);
    }

    @Override
    public int addAccount(Account account) {
        return accountMapper.addAccount(account);
    }
}
