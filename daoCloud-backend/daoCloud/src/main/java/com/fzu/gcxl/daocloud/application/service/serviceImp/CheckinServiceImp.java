package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.CheckinService;
import com.fzu.gcxl.daocloud.domain.entity.Checkin;
import com.fzu.gcxl.daocloud.domain.entity.CheckinHistory;
import com.fzu.gcxl.daocloud.domain.entity.CheckinRecord;
import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.exception.UserFriendException;
import com.fzu.gcxl.daocloud.domain.repository.CheckinHistoryRepository;
import com.fzu.gcxl.daocloud.domain.repository.CheckinRecordRepository;
import com.fzu.gcxl.daocloud.domain.repository.CheckinRepository;
import com.fzu.gcxl.daocloud.domain.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Service
public class CheckinServiceImp implements CheckinService {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    CheckinRepository checkinRepository;

    @Autowired
    CheckinHistoryRepository checkinHistoryRepository;

    @Autowired
    CheckinRecordRepository checkinRecordRepository;

    // 教师发起签到
    public BaseResponse teastartCheckin(@RequestBody JSONObject tcheckin){
        // 签到类型
//        Integer checkintype = tcheckin.getInteger("checkintype");
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
        // 班级的教师
        String userphone = tcheckin.getString("teacherphone");
        // 签到发起位置
        String location = tcheckin.getString("location");

        // 将信息存入数据库
        // 判断是否已经有同一门课程的实时签到记录
        try{
            if (checkinRepository.selcetByClassCode(classcode) == null ||
                    checkinRepository.selcetByClassCode(classcode).getIsClose() == 1){
                Checkin checkin = new Checkin();
                checkin.setType(1);
                checkin.setClassCode(classcode);
                checkin.setUserId(userphone);

                Date currentDate = new Date(System.currentTimeMillis());
                checkin.setCheckinDate(currentDate);
                checkin.setStartTime(currentDate);
                // 签到持续时间 签到持续时间 -> 从系统参数获得
                checkin.setDuration(5 * 60 * 1000);
                Date endDate = new Date(System.currentTimeMillis() + 5 * 60 * 1000);
                checkin.setEndTime(endDate);
                // 班级人数 班级人数 -> 从班课获得
                Class aClass = classRepository.selectByPrimaryKey(classcode);
                checkin.setClassMembers(Long.valueOf(aClass.getClassMember()));
                checkin.setCheckinLocation(location);
                // 0->false 1->true 发起的时候为 开启0
                checkin.setIsClose(0);

                int res = checkinRepository.insert(checkin);
                if (res == -1)
                    new BaseResponse(500, "发起签到失败","checkinfailed");
                return new BaseResponse(200, "发起签到成功","checkinsuccess");
            }
            else {
                return new BaseResponse(500, "发起签到失败，已存在","checkinexist");
            }
        }catch (Exception e){
            throw new UserFriendException("发起签到失败");
        }
    }

    // 结束签到
    public BaseResponse teaendCheckin(@RequestBody JSONObject tcheckin){
        // 修改checkin表 将签到关闭
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
        // 班级的教师
        String userphone = tcheckin.getString("teacherphone");
        // 课程开始签到的时间
        Date checkindate = tcheckin.getDate("checkindate");

        try{
            Checkin checkin = checkinRepository.selcetByClassCode(classcode);
            if (checkin != null){
                if (checkin.getIsClose() == 1){
                    return new BaseResponse(500, "关闭失败, 签到已经关闭","Closefailed");
                }else {
                    Checkin newCheckin = new Checkin();
                    // 0->false 1->true 发起的时候为 开启0
                    newCheckin.setIsClose(1);
                    newCheckin.setClassCode(classcode);
                    newCheckin.setCheckinId(checkinRepository.selcetIdByClassCodeDate(classcode, checkindate));
                    int res = checkinRepository.updateByPrimaryKeySelective(newCheckin);
                    if (res == -1)
                        return new BaseResponse(500, "关闭失败","Closefailed");

                    // 将关闭的签到记录到checkin_history
                    CheckinHistory checkinHistory = new CheckinHistory();
                    checkinHistory.setCheckinDate(checkindate);
                    checkinHistory.setCheckinType(checkin.getType());
                    checkinHistory.setCheckinNumrequired(Math.toIntExact(checkin.getClassMembers()));
                    List<CheckinRecord> checkintotal = checkinRecordRepository.selectByCodeDate(classcode, checkindate);
                    int checknumtotal = 0;
                    if (checkintotal != null){
                        checknumtotal = checkintotal.size();
                    }
                    checkinHistory.setCheckinNum(checknumtotal);
                    checkinHistory.setUserId(userphone);
                    int resh = checkinHistoryRepository.insert(checkinHistory);
                    if (resh == -1)
                        return new BaseResponse(500, "签到历史记录插入失败","CloseSuccess;HistoryFailed");
                    return new BaseResponse(200, "关闭成功, 课程已经关闭","CloseSuccess");
                }
            }else{
                return new BaseResponse(500, "关闭失败","Closefailed");
            }
        }catch (Exception e){
            throw new UserFriendException("关闭失败");
        }
    }

    // 学生参与签到
    public BaseResponse stuCheckin(@RequestBody JSONObject tcheckin){
        // 1. 限时一键签到
        // 判断签到是否关闭 查询checkin表格
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
        String checkinlocation = tcheckin.getString("location");
        // 学生的手机号
        String userphone = tcheckin.getString("studentphone");
        Date checkindate = tcheckin.getDate("checkindate");

        try{
            Integer checkinId = checkinRepository.selcetIdByClassCodeDate(classcode, checkindate);
            Checkin checkin = checkinRepository.selectByPrimaryKey(checkinId);

            if (checkin.getIsClose() == 1){
                return new BaseResponse(500, "签到失败, 课程已经关闭","Closefailed");
            }else {
                // 判断学生是否签到过
                if (checkinRecordRepository.selectByCodeandDateSingle(classcode, checkindate, userphone) != null){
                    return new BaseResponse(500, "学生已签到成功，请勿重复签到","Checkinfailed");
                }
                // 修改checkin_record表 记录签到
                CheckinRecord checkinRecord = new CheckinRecord();
                checkinRecord.setCheckinLocation(checkinlocation);
                checkinRecord.setCheckinTime(new Date(System.currentTimeMillis()));
                checkinRecord.setUserId(userphone);
                checkinRecord.setClassCode(classcode);
                checkinRecord.setCheckinStartdate(checkindate);
                // 传入教师的手机号
                int classId = checkinRepository.selcetIdByClassCodeDate(classcode, checkindate);
                checkinRecord.setTeacherId(checkinRepository.selectByPrimaryKey(classId).getUserId());

                int res = checkinRecordRepository.insert(checkinRecord);
                if (res == -1)
                    return new BaseResponse(500, "学生签到失败","CheckinFailed");
                return new BaseResponse(200, "学生签到成功", "CheckinSuccess");
            }
        }catch (Exception e){
            throw new UserFriendException("学生签到失败");
        }
    }

    // 教师获取某门课的签到记录
    public BaseResponse recordCheckinteacher(@RequestBody JSONObject tcheckin){
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
        // 班级的教师
        String userphone = tcheckin.getString("teacherphone");
        // 课程开始签到的时间
        Date checkindate = tcheckin.getDate("checkindate");
        try{
            List<CheckinRecord> checkinRecords = checkinRecordRepository.selectCheckinRecordsForTeacher(classcode, checkindate, userphone);
            return new BaseResponse(200, "获取成功", checkinRecords);
        }catch (Exception e){
            throw new UserFriendException("获取失败");
        }
    }

    // 学生 获取签到记录
    public BaseResponse recordCheckinstudent(@RequestBody JSONObject tcheckin){
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
        // 班级的教师
        String userphone = tcheckin.getString("studentphone");
        // 课程开始签到的时间
        Date checkindate = tcheckin.getDate("checkindate");
        List<CheckinRecord> checkinRecords = checkinRecordRepository.selectCheckinRecordsForStudent(classcode, checkindate, userphone);

        return new BaseResponse(200, "获取成功", checkinRecords);
    }

    // 教师进行手动补签
    @Override
    public BaseResponse recordModifiedByTeacher(@RequestBody JSONObject tcheckin){
        // 判断签到是否关闭 查询checkin表格
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
        String checkinlocation = tcheckin.getString("location");
        // 学生的手机号
        String studentphone = tcheckin.getString("studentphone");
        Date checkindate = tcheckin.getDate("checkindate");
        String teacherphone = tcheckin.getString("teacherphone");

        try{
            Checkin checkin = checkinRepository.selcetByClassCode(classcode);
            if (checkin != null){
                if (checkin.getIsClose() == 1){
                    return new BaseResponse(500, "补签失败, 已经关闭","Closefailed");
                }else {
                    // 判断学生是否签到过
                    if (checkinRecordRepository.selectByCodeandDateSingle(classcode, checkindate, studentphone) != null){
                        return new BaseResponse(500, "学生已签到成功，请勿重复签到","Checkinfailed");
                    }
                    // 修改checkin_record表 记录签到
                    CheckinRecord checkinRecord = new CheckinRecord();
                    checkinRecord.setCheckinLocation(checkinlocation);
                    checkinRecord.setCheckinTime(new Date(System.currentTimeMillis()));
                    checkinRecord.setUserId(studentphone);
                    checkinRecord.setClassCode(classcode);
                    checkinRecord.setCheckinStartdate(checkindate);
                    checkinRecord.setTeacherId(teacherphone);

                    int res = checkinRecordRepository.insert(checkinRecord);
                    if (res == -1)
                        return new BaseResponse(500, "学生签到失败","CheckinFailed");
                    return new BaseResponse(200, "学生签到成功", "CheckinSuccess");
                }
            }else{
                return new BaseResponse(500, "补签失败，不存在该签到记录","CheckinFailed");
            }
        }catch (Exception e){
            throw new UserFriendException("错误的要求，参数没有通过有效性验证。");
        }
    }

    @Override
    public BaseResponse stuexp(JSONObject tcheckin) {
        return null;
    }

    // 学生获得的经验值

}
