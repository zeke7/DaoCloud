package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.CheckinService;
import com.fzu.gcxl.daocloud.domain.entity.*;
import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.exception.UserFriendException;
import com.fzu.gcxl.daocloud.domain.repository.*;
import com.fzu.gcxl.daocloud.frontinterface.dto.CheckinSingleDto;
import com.fzu.gcxl.daocloud.frontinterface.dto.CheckinStatusDto;
import com.fzu.gcxl.daocloud.frontinterface.dto.CheckinStuDto;
import com.fzu.gcxl.daocloud.frontinterface.dto.CheckinTeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
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

    @Autowired
    SystemParameterRepository systemParameterRepository;

    @Autowired
    ClassInfoRepository classInfoRepository;

    // 教师发起签到
    public BaseResponse teastartCheckin(@RequestBody JSONObject tcheckin){
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
        // 班级的教师
        String userphone = tcheckin.getString("teacherphone");
        // 签到发起位置
        String locx = tcheckin.getString("location_x");
        String locy = tcheckin.getString("location_y");
        // 签到经验值
//        Integer exp = tcheckin.getInteger("checkinexp") != null? tcheckin.getInteger("checkinexp"): 2;
        String checkintype = tcheckin.getString("checkintype");
        // 将信息存入数据库
        // 判断是否已经有同一门课程的实时签到记录

        try{
            boolean checkinClose = true;
            List<Checkin> curCheckin = checkinRepository.selcetCheckinByClassCode(classcode);
            Date currentDate = new Date(System.currentTimeMillis());

            for (Checkin check: curCheckin) {
                // 限时签到未过期
                if (check == null)
                    break;
                if (check.getIsClose() == null){
                    if (check.getEndTime().after(currentDate)){
                        checkinClose = false;
                        break;
                    }else {
                        continue;
                    }
                }
                // 一键签到未关闭
                if (check.getIsClose() == 0){
                    checkinClose = false;
                    break;
                }
            }


            if (curCheckin.size() == 0 || checkinClose){

                Checkin checkin = new Checkin();
                checkin.setClassCode(classcode);
                checkin.setUserId(userphone);
                checkin.setCheckinDate(currentDate);
                checkin.setStartTime(currentDate);
                Class aClass = classRepository.selectByPrimaryKey(classcode);
                List<ClassInfo> classInfoList = classInfoRepository.selectAllClassInfoByClassCode(classcode);
//                SystemParameter systime = systemParameterRepository.selectBysname("签到时间");
//                String sysParameter = systime != null? systime.getSysParameter() : "5";
                // 签到持续时间 签到持续时间 -> 从系统参数获得
                if (checkintype != null && checkintype.equals("limited")){
                    checkin.setDuration(5 * 60 * 1000);
                    Date endDate = new Date(System.currentTimeMillis() + 5 * 60 * 1000);
                    checkin.setEndTime(endDate);
                    // 1->限时签到 2->一键签到
                    checkin.setType(1);

                    CheckinHistory checkinHistory = new CheckinHistory();
                    checkinHistory.setCheckinDate(currentDate);
                    checkinHistory.setCheckinType(1);
                    checkinHistory.setCheckinNumrequired(Math.toIntExact(classInfoList.size()));
                    checkinHistory.setUserId(userphone);
                    checkinHistory.setClassCode(classcode);
                    int resh = checkinHistoryRepository.insert(checkinHistory);
                    if (resh == -1)
                        return new BaseResponse(500, "签到历史记录插入失败","CloseSuccess;HistoryFailed");
                }else {
                    checkin.setIsClose(0);
                    checkin.setType(2);
                }
                // 班级人数 班级人数 -> 从班课获得
                checkin.setCheckinLocx(locx);
                checkin.setCheckinLocy(locy);
                // 0->false 1->true 发起的时候为 开启0
                checkin.setClassMembers((long) classInfoList.size());

                int res = checkinRepository.insert(checkin);
                if (res == -1)
                    new BaseResponse(500, "发起签到失败","checkinfailed");
                return new BaseResponse(200, "发起签到成功",currentDate.toString());
            }
            else {
                return new BaseResponse(500, "发起签到失败，已存在","checkinexist");
            }
        }catch (Exception e){
            System.out.println(e);
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
            int checkinId = checkinRepository.selcetIdByClassCodeDate(classcode, checkindate);
            Checkin checkin = checkinRepository.selectByPrimaryKey(checkinId);
            if (checkin != null){
                int isclode = checkin.getIsClose() != null? checkin.getIsClose():0;
                if (isclode == 1){
                    return new BaseResponse(500, "关闭失败, 签到已经关闭","Closefailed");
                }else {
                    Checkin newCheckin = new Checkin();
                    // 0->false 1->true 发起的时候为 开启0
                    newCheckin.setIsClose(1);
                    newCheckin.setClassCode(classcode);
                    newCheckin.setCheckinId(checkinId);
                    int res = checkinRepository.updateByPrimaryKeySelective(newCheckin);
                    if (res == -1)
                        return new BaseResponse(500, "关闭失败","Closefailed");

                    CheckinHistory checkinHistory = new CheckinHistory();
                    List<CheckinRecord> checkintotal = checkinRecordRepository.selectByCodeDate(classcode, checkindate);
                    int checknumtotal = 0;
                    if (checkintotal != null){
                        checknumtotal = checkintotal.size();
                    }
                    checkinHistory.setCheckinNum(checknumtotal);
                    // 将关闭的签到记录到checkin_history
                    if (checkinHistoryRepository.selectByClassCodeAndDate(classcode, checkindate) != null){
                        checkinHistory.setCheckinHid(checkinHistoryRepository.selectByClassCodeAndDate(classcode, checkindate).getCheckinHid());
                        int resh = checkinHistoryRepository.updateByPrimaryKeySelective(checkinHistory);
                        if (resh == -1)
                            return new BaseResponse(500, "签到历史记录插入失败","CloseSuccess;HistoryFailed");
                        return new BaseResponse(200, "关闭成功, 课程已经关闭","CloseSuccess");
                    }else{
                        checkinHistory.setCheckinDate(checkindate);
                        checkinHistory.setCheckinType(checkin.getType());
                        checkinHistory.setCheckinNumrequired(Math.toIntExact(checkin.getClassMembers()));
                        checkinHistory.setUserId(userphone);
                        checkinHistory.setClassCode(classcode);
                        int resh = checkinHistoryRepository.insert(checkinHistory);
                        if (resh == -1)
                            return new BaseResponse(500, "签到历史记录插入失败","CloseSuccess;HistoryFailed");
                        return new BaseResponse(200, "关闭成功, 课程已经关闭","CloseSuccess");
                    }

                }
            }else{
                return new BaseResponse(500, "关闭失败,当前签到为限时签到自动关闭","Closefailed");
            }
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("关闭失败");
        }
    }

    // 学生参与签到
    public BaseResponse stuCheckin(@RequestBody JSONObject tcheckin){
        // 1. 限时一键签到
        // 判断签到是否关闭 查询checkin表格
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
        String locx = tcheckin.getString("location_x");
        String locy = tcheckin.getString("location_y");

        // 学生的手机号
        String userphone = tcheckin.getString("studentphone");
        // 签到开始时间
        Date checkindate = tcheckin.getDate("checkindate");

        try{
            Integer checkinId = checkinRepository.selcetIdByClassCodeDate(classcode, checkindate);
            Checkin checkin = checkinRepository.selectByPrimaryKey(checkinId);
            Date stuCheckinDate = new Date(System.currentTimeMillis());

            if (checkin.getEndTime() != null){
                System.out.println(checkin.getEndTime());
                // 签到超时
                boolean after = stuCheckinDate.after(checkin.getEndTime());
                if (after)
                    return new BaseResponse(500, "学生签到超时","CheckinLate");
            }
            if (checkin.getIsClose() != null){
                // 课程关闭
                if (checkin.getIsClose() == 1)
                    return new BaseResponse(500, "签到失败, 课程已经关闭","Closefailed");
            }
            // 判断学生是否签到过
            if (checkinRecordRepository.selectByCodeandDateSingle(classcode, checkindate, userphone) != null){
                return new BaseResponse(500, "学生已签到成功，请勿重复签到","Checkinfailed");
            }

            // 判断学生签到时间是否超时
            // 修改checkin_record表 记录签到
            CheckinRecord checkinRecord = new CheckinRecord();
            checkinRecord.setCheckinLocx(locx);
            checkinRecord.setCheckinLocy(locy);
            checkinRecord.setCheckinTime(stuCheckinDate);
            checkinRecord.setUserId(userphone);
            checkinRecord.setClassCode(classcode);
            checkinRecord.setCheckinStartdate(checkindate);
            System.out.println("checkinrecord 1 : "+checkinRecord.toString());
            // 传入教师的手机号
            int classId = checkinRepository.selcetIdByClassCodeDate(classcode, checkindate);
            System.out.println("传入教师的手机号:" + String.valueOf(classId));
            checkinRecord.setTeacherId(checkinRepository.selectByPrimaryKey(classId).getUserId());
            System.out.println("checkinrecord 2 : "+checkinRecord.toString());
            int res = checkinRecordRepository.insert(checkinRecord);
            if (res == -1)
                return new BaseResponse(500, "学生签到失败","CheckinFailed");
            return new BaseResponse(200, "学生签到成功", "CheckinSuccess");

        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("学生签到失败");
        }
    }

    // 教师获取某门课的历史签到记录
    public BaseResponse CheckinHistoryteacher(@RequestBody JSONObject tcheckin){
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
        // 班级的教师
        String userphone = tcheckin.getString("teacherphone");
        // 课程开始签到的时间
        try{
            List<CheckinHistory> checkinRecords = checkinHistoryRepository.selectCheckinHistoryForTeacher(classcode, userphone);
            return new BaseResponse(200, "获取成功", checkinRecords);
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("获取失败");
        }
    }

    // 教师获取某门课的签到记录
//    public BaseResponse recordCheckinteacher(@RequestBody JSONObject tcheckin){
//        // 进行签到的班级代码
//        String classcode = tcheckin.getString("classcode");
//        // 班级的教师
//        String userphone = tcheckin.getString("teacherphone");
//
//        Date checkindate = tcheckin.getDate("checkindate");
//
//        try{
//            // 获取所有签到记录
////            List<Checkin> allCheckin = checkinRepository.selcetCheckinByClassCode(classcode);
//
//
//            Checkin classnotclose = checkinRepository.getOpenCheckClass(classcode, 0);
//            List<Checkin> limitCheckinClass = checkinRepository.getLimitedCheckin(classcode);
//            for (Checkin ck: limitCheckinClass) {
//                if(ck.getEndTime().after(new Date(System.currentTimeMillis()))){
//                    classnotclose = ck;
//                }
//            }
//
//            if (classnotclose == null){
//                return new BaseResponse(500, "签到已关闭", "CheckinClosed");
//            }
//            // 获取班课所有学生
//            List<ClassInfo> classInfoList = classInfoRepository.selectAllClassInfoByClassCode(classcode);
//            List<CheckinSingleDto> checkinSingleDtoList = new ArrayList<>();
//
//
//            for (ClassInfo cinfo: classInfoList) {
//                CheckinSingleDto checkinSingleDto = new CheckinSingleDto();
//                checkinSingleDto.setStudentName(cinfo.getUserName());
//                checkinSingleDto.setStudentPhone(cinfo.getUserPhone());
////                for (Checkin check: allCheckin) {
////                    CheckinStatusDto checkinStatusDto = new CheckinStatusDto();
////                    checkinStatusDto.setCheckinDate(check.getCheckinDate());
////                    checkinStatusDto.setIsCheckin("0");
////                    for (CheckinRecord cRecord: checkinRecords) {
////                        if (check.getCheckinDate().equals(cRecord.getCheckinStartdate())){
////                            checkinStatusDto.setIsCheckin("1");
////                        }
////                    }
////                    ListCheckinStatus.add(checkinStatusDto);
////                }
//                CheckinRecord checkinRecords = checkinRecordRepository.selectSingleCheckinRecordsForTeacher(classcode, checkindate, cinfo.getUserPhone());
//                CheckinStatusDto checkinStatusDto = new CheckinStatusDto();
//                checkinStatusDto.setCheckinDate(checkindate);
//                checkinStatusDto.setIsCheckin("0");
//                checkinStatusDto.setCheckinType(classnotclose.getType());
//                if (checkinRecords != null){
//                    if (checkindate.equals(checkinRecords.getCheckinStartdate())){
//                        checkinStatusDto.setIsCheckin("1");
//                    }
//                }
//                checkinSingleDto.setStatusDto(checkinStatusDto);
//                checkinSingleDtoList.add(checkinSingleDto);
//            }
//
//            return new BaseResponse(200, "获取成功", checkinSingleDtoList);
//        }catch (Exception e){
//            System.out.println(e);
//            throw new UserFriendException("获取失败");
//        }
//    }
    public BaseResponse recordCheckinteacher(@RequestBody JSONObject tcheckin){
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
        // 班级的教师
//        String userphone = tcheckin.getString("teacherphone");
//
//        Date checkindate = tcheckin.getDate("checkindate");

        try{
            // 获取所有签到记录
            List<Checkin> allCheckin = checkinRepository.selcetCheckinByClassCode(classcode);
            // 获取班课所有学生
            List<ClassInfo> classInfoList = classInfoRepository.selectAllClassInfoByClassCode(classcode);
            List<CheckinTeacherDto> checkinTeacherDtoList = new ArrayList<>();

            // 对于每次签到
            for (Checkin check: allCheckin) {
                CheckinTeacherDto checkinStatusDto = new CheckinTeacherDto();
                List<CheckinStuDto> stuDtoList = new ArrayList<>();
                checkinStatusDto.setCheckinDate(check.getCheckinDate());
                checkinStatusDto.setCheckinType(check.getType());

                // 对于班课的学生
                for (ClassInfo cinfo: classInfoList) {
                    CheckinStuDto checkinStu = new CheckinStuDto();
                    checkinStu.setStudentName(cinfo.getUserName());
                    checkinStu.setStudentPhone(cinfo.getUserPhone());
                    checkinStu.setIsCheckin("0");
                    List<CheckinRecord> checkinRecords = checkinRecordRepository.selectCheckinRecordsForStudent(classcode, cinfo.getUserPhone());
                    // 当前学生本门课的所有签到记录
                    for (CheckinRecord cr: checkinRecords) {
                        // 单次签到是否存在
                        if (check.getCheckinDate().equals(cr.getCheckinStartdate())) {
                            checkinStu.setIsCheckin("1");
                        }
                    }
                    stuDtoList.add(checkinStu);
                }
                checkinStatusDto.setStudentList(stuDtoList);
                checkinTeacherDtoList.add(checkinStatusDto);
            }


            return new BaseResponse(200, "获取成功", checkinTeacherDtoList);
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("获取失败");
        }
    }

    // 学生 获取签到记录
    public BaseResponse recordCheckinstudent(@RequestBody JSONObject tcheckin){
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
        // 班级的教师
        String userphone = tcheckin.getString("studentphone");

        List<CheckinRecord> checkinRecords = checkinRecordRepository.selectCheckinRecordsForStudent(classcode, userphone);
        List<Checkin> allCheckin = checkinRepository.selcetCheckinByClassCode(classcode);
        List<CheckinStatusDto> ListCheckinStatus = new ArrayList<>();

        for (Checkin check: allCheckin) {
            CheckinStatusDto checkinStatusDto = new CheckinStatusDto();
            checkinStatusDto.setCheckinDate(check.getCheckinDate());
            checkinStatusDto.setIsCheckin("0");
            for (CheckinRecord cRecord: checkinRecords) {
                if (check.getCheckinDate().equals(cRecord.getCheckinStartdate())){
                    checkinStatusDto.setIsCheckin("1");
                }
            }
            ListCheckinStatus.add(checkinStatusDto);
        }

        return new BaseResponse(200, "获取成功", ListCheckinStatus);
    }

    // 教师进行手动补签
    @Override
    public BaseResponse recordModifiedByTeacher(@RequestBody JSONObject tcheckin){
        // 判断签到是否关闭 查询checkin表格
        // 进行签到的班级代码
        String classcode = tcheckin.getString("classcode");
//        String locx = tcheckin.getString("location_x");
//        String locy = tcheckin.getString("location_y");
        // 学生的手机号
        String studentphone = tcheckin.getString("studentphone");
        Date checkindate = tcheckin.getDate("checkindate");
        String teacherphone = tcheckin.getString("teacherphone");

        try{
            int checkinId = checkinRepository.selcetIdByClassCodeDate(classcode, checkindate);
            Checkin checkin = checkinRepository.selectByPrimaryKey(checkinId);
            if (checkin != null){
//                if (checkin.getIsClose() == 1){
//                    return new BaseResponse(500, "补签失败, 已经关闭","Closefailed");
//                }else {
                    // 判断学生是否签到过
                    if (checkinRecordRepository.selectByCodeandDateSingle(classcode, checkindate, studentphone) != null){
                        return new BaseResponse(500, "学生已签到成功，请勿重复签到","Checkinfailed");
                    }
                    // 修改checkin_record表 记录签到
                    CheckinRecord checkinRecord = new CheckinRecord();
//                    checkinRecord.setCheckinLocx(locx);
//                    checkinRecord.setCheckinLocy(locy);
                    checkinRecord.setCheckinTime(new Date(System.currentTimeMillis()));
                    checkinRecord.setUserId(studentphone);
                    checkinRecord.setClassCode(classcode);
                    checkinRecord.setCheckinStartdate(checkindate);
//                    checkinRecord.setTeacherId(teacherphone);

                    int res = checkinRecordRepository.insert(checkinRecord);
                    if (res == -1)
                        return new BaseResponse(500, "学生签到失败","CheckinFailed");
                    return new BaseResponse(200, "学生签到成功", "CheckinSuccess");
//                }
            }else{
                return new BaseResponse(500, "补签失败，不存在该签到记录","CheckinFailed");
            }
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("错误的要求，参数没有通过有效性验证。");
        }
    }

    // 学生获得的经验值
    @Override
    public BaseResponse stuexp(JSONObject tcheckin) {
        try{
            // 进行签到的班级代码
            String classcode = tcheckin.getString("classcode");
            String stuphone = tcheckin.getString("studentphone");

            List<CheckinRecord> checkinRecords = checkinRecordRepository.selectCheckinRecordsForStudent(classcode, stuphone);
            SystemParameter sysparam = systemParameterRepository.selectBysname("签到经验");
            int exp = 2;
            if (sysparam.getSysParameter() != null)
                exp = Integer.parseInt(sysparam.getSysParameter());

            return new BaseResponse(200, "", exp*checkinRecords.size());
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("获取经验值失败");
        }

    }


    @Override
    public BaseResponse stuexpall(JSONObject tcheckin) {
        try{
            // 进行签到的班级代码
            List<Class> classList = classRepository.selectAllClasses();
            String stuphone = tcheckin.getString("studentphone");
            SystemParameter sysparam = systemParameterRepository.selectBysname("签到经验");
            int exp = 2;
            if (sysparam.getSysParameter() != null)
                exp = Integer.parseInt(sysparam.getSysParameter());

            int totalexp = 0;

            for (Class c: classList) {
                List<CheckinRecord> checkinRecords = checkinRecordRepository.selectCheckinRecordsForStudent(c.getClassCode(), stuphone);
                int cexp = checkinRecords.size() * exp;
                totalexp += cexp;
            }

            return new BaseResponse(200, "", totalexp);
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("获取经验值失败");
        }

    }

    @Override
    public BaseResponse getCheckinfo(String cinfo) {
        try{
            Checkin openCheckClass = checkinRepository.getOpenCheckClass(cinfo, 0);
            List<Checkin> limitCheckinClass = checkinRepository.getLimitedCheckin(cinfo);
            for (Checkin ck: limitCheckinClass) {
                if(ck.getEndTime().after(new Date(System.currentTimeMillis()))){
                    openCheckClass = ck;
                }
            }


            if (openCheckClass != null){
                return new BaseResponse(200, "签到信息", openCheckClass);
            }
            return new BaseResponse(200, "签到信息", "");
        }catch (Exception e){
            throw new UserFriendException("获取签到信息失败");
        }

    }


}
