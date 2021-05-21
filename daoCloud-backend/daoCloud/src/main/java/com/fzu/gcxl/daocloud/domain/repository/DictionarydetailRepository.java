package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.Dictionarydetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DictionarydetailRepository {
    int deleteByPrimaryKey(String dicdetailCode);

    int insert(Dictionarydetail record);

    int insertSelective(Dictionarydetail record);

    Dictionarydetail selectByPrimaryKey(String dicdetailCode);

    int updateByPrimaryKeySelective(Dictionarydetail record);

    int updateByPrimaryKey(Dictionarydetail record);

    List<Dictionarydetail> selectDetialsbyDicCode(String dicCode);

    Dictionarydetail selectByDicAndDetialCode(String dicCode, String dicdetailCode);
}