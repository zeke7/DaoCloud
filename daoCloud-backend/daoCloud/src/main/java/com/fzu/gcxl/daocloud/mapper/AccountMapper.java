package com.fzu.gcxl.daocloud.mapper;

import com.fzu.gcxl.daocloud.entity.Account;
import com.fzu.gcxl.daocloud.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccountMapper {


    List<Account> qAList();

    Account queryAccounts(int id);

    Account queryAccountByPhone(String phone);

    int addAccount(Account account);
}