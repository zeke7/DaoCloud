package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.CheckinHistory;
import com.fzu.gcxl.daocloud.domain.entity.CheckinRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface CheckinRecordRepository extends IRepository<CheckinRecord>{
    int deleteByPrimaryKey(Integer checkinId);

    int insert(CheckinRecord record);

    int insertSelective(CheckinRecord record);

    CheckinRecord selectByPrimaryKey(Integer checkinId);

    int updateByPrimaryKeySelective(CheckinRecord record);

    int updateByPrimaryKey(CheckinRecord record);

    CheckinRecord selectByCodeandDateSingle(String classCode, Date checkinStartdate, String userId);

    List<CheckinRecord> selectByCodeDate(String classCode, Date checkinStartdate);

    List<CheckinRecord> selectCheckinRecordsForTeacher(String classCode, Date checkinStartdate, String teacherId);

    List<CheckinRecord> selectCheckinRecordsForStudent(String classCode, String studentId);


}