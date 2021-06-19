package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.Checkin;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

    List<Checkin> selcetCheckinByClassCode(String classCode);

    Checkin getOpenCheckClass(String classCode, Integer isClose);

    int selcetIdByClassCodeDate(String classCode, Date checkinDate);

}