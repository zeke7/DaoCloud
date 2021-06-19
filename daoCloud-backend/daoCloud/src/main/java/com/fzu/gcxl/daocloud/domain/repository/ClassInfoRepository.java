package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.ClassInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface ClassInfoRepository {
    int deleteByPrimaryKey(Integer classinfoId);

    int insert(ClassInfo record);

    int insertSelective(ClassInfo record);

    ClassInfo selectByPrimaryKey(Integer classinfoId);

    int updateByPrimaryKeySelective(ClassInfo record);

    int updateByPrimaryKey(ClassInfo record);

    List<ClassInfo> selectUserByphonecode(String classcode, String userphone);

    List<ClassInfo> selectAllClassInfoByUser(String userPhone);

    List<ClassInfo> selectAllClassInfoByClassCode(String classCodes);
}