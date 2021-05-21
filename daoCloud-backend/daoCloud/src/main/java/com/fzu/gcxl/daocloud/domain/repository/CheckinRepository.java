package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.Checkin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface CheckinRepository extends IRepository<Checkin>{
    int deleteByPrimaryKey(Integer checkinId);

    int insert(Checkin record);

    int insertSelective(Checkin record);

    Checkin selectByPrimaryKey(Integer checkinId);

    int updateByPrimaryKeySelective(Checkin record);

    int updateByPrimaryKey(Checkin record);

    Checkin selcetByClassCode(String classCode);

    int selcetIdByClassCodeDate(String classCode, Date checkinDate);

}