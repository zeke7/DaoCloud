package com.fzu.gcxl.daocloud.domain.persistence;

import com.fzu.gcxl.daocloud.domain.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface AccountRepository extends IBaseRepository<Account> {

    @Override
    int add(Account account);

    @Override
    int delete(int id);

    @Override
    int getCount(String search);

    @Override
    int update(Account account);

    Account findAccountByPhone(String phone);

    int updateAccountPassword(Account account);

    int createNewAccount(Account account);

}
