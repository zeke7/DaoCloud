package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface AccountRepository extends IRepository<Account>{
    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(Account record);

    @Override
    int insertSelective(Account record);

    @Override
    Account selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(Account record);

    @Override
    int updateByPrimaryKey(Account record);

    Account findAccountByPhone(String phone);

    int updateAccountPassword(Account account);

    int createNewAccount(Account account);

}
