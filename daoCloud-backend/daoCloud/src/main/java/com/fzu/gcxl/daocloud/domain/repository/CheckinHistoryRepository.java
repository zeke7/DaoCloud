package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.CheckinHistory;
import com.fzu.gcxl.daocloud.domain.entity.CheckinRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Mapper
@Service
public interface CheckinHistoryRepository extends IRepository<CheckinHistory>{
    int deleteByPrimaryKey(Integer checkinHid);

    int insert(CheckinHistory record);

    int insertSelective(CheckinHistory record);

    CheckinHistory selectByPrimaryKey(Integer checkinHid);

    int updateByPrimaryKeySelective(CheckinHistory record);

    int updateByPrimaryKey(CheckinHistory record);

    List<CheckinHistory> selectCheckinHistoryForTeacher(String classCode, String userId);

    CheckinHistory selectByClassCodeAndDate(String classCode, Date checkinDate);
}