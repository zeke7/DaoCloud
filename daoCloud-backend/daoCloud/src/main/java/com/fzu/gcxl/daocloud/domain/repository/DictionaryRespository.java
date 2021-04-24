package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.Dictionary;
import com.fzu.gcxl.daocloud.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DictionaryRespository extends IRepository<Dictionary>{

    int deleteByPrimaryKey(String dicCode);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(String dicCode);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

    Dictionary selectByDetailCode(String dicDetailcode, String dicCode);

    int deleteByDetailCode(String dicDetailcode, String dicCode);

    List<Dictionary> selectAllDictionaries();
}