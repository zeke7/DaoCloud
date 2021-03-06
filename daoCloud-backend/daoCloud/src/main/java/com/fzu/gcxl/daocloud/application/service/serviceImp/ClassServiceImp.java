package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.ClassService;
import com.fzu.gcxl.daocloud.application.service.assembler.ClassDtoAssembler;
import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.entity.ClassInfo;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.exception.UserFriendException;
import com.fzu.gcxl.daocloud.domain.repository.ClassInfoRepository;
import com.fzu.gcxl.daocloud.domain.repository.ClassRepository;
import com.fzu.gcxl.daocloud.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClassServiceImp implements ClassService {
    @Autowired
    ClassRepository classRepository;

    @Autowired
    ClassInfoRepository classInfoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassDtoAssembler classDtoAssembler;

    public BaseResponse createNewClass(JSONObject createClass){
        String classname = createClass.getString("classname");
        String userPhone = createClass.getString("userphone");
        Integer classmember = createClass.getInteger("classmembers");
//        String classno = createClass.getString("classcode");
        String semester = createClass.getString("classsemester");

        String classNo = String.valueOf((int)((Math.random()*9+1)*100000));
        while (classRepository.selectByPrimaryKey(classNo) != null){
            classNo = String.valueOf((int)((Math.random()*9+1)*100000));
        }

        try{
            Class newClass = new Class();
            newClass.setClassName(classname);
            newClass.setUserPhone(userPhone);
            newClass.setClassMember(classmember);

            newClass.setClassCode(classNo);
            newClass.setCreateTime(new Date());
            newClass.setClassSemester(semester);
            // 0-> not allowed 1->allow
            newClass.setClassIsallowed("1");
            // 0->false 1->true
            newClass.setClassIsclose("0");

            if (classRepository.selectByClassName(classname) == null && classRepository.selectByPrimaryKey(classNo) == null){
                int res = classRepository.insert(newClass);
                if(res==-1)
                    return new BaseResponse(500, "??????????????????","");
                return new BaseResponse(HttpStatus.OK.value(), "??????????????????", classNo);
            }else {
                return new BaseResponse(HttpStatus.OK.value(), "??????????????????, ?????????????????????????????????", "CreateClassFail_Num_Name");
            }
        }catch (Exception e){
            throw new UserFriendException("??????????????????");
        }
    }

    public BaseResponse getClassesByCode(String classCode){
        try{
            Class classes = classRepository.selectByPrimaryKey(classCode);
            return new BaseResponse(HttpStatus.OK.value(), "??????????????????", classes);
        }catch (Exception e){
            throw new UserFriendException("????????????????????????");
        }
    }

    public BaseResponse getClassesByUser(String userphone){
        try{
            List<Class> classes = classRepository.selectAllClassesByUser(userphone);
            return new BaseResponse(HttpStatus.OK.value(), "??????????????????", classes);
        }catch (Exception e){
            throw new UserFriendException("????????????????????????");
        }
    }

    public BaseResponse delClassByCode(String classcode){
        try{
            int res = classRepository.deleteByPrimaryKey(classcode);
            if(res==-1)
                return new BaseResponse(500, "????????????","");
            return new BaseResponse(HttpStatus.OK.value(), "????????????", "");
        }catch (Exception e){
            throw new UserFriendException("????????????");
        }

    }

    public BaseResponse updateClassByCode(JSONObject createClass){

        String classname = createClass.getString("classname");
        Integer classmember = createClass.getInteger("classmembers");
        String classcode = createClass.getString("classcode");
        String semester = createClass.getString("classsemester");
        String userPhone = createClass.getString("userPhone");
        try{
            Class newClass = new Class();
            newClass.setClassName(classname);
            newClass.setClassMember(classmember);
            newClass.setCreateTime(new Date());
            newClass.setClassCode(classcode);
            newClass.setClassSemester(semester);
            newClass.setUserPhone(userPhone);
            // 0->false 1->true
            newClass.setClassIsclose("0");

            if (classRepository.selectByClassName(classname) == null){
                int res = classRepository.updateByPrimaryKeySelective(newClass);
                if(res==-1)
                    return new BaseResponse(500, "????????????","");
                return new BaseResponse(HttpStatus.OK.value(), "????????????", "");
            }else {
                return new BaseResponse(500, "????????????","");
            }
        }catch (Exception e){
            throw new UserFriendException("???????????????????????????????????????????????????");
        }

    }

    public void updateClassMemberByCode(JSONObject createClass){

        String classcode = createClass.getString("classcode");

        Integer currentMembers = classRepository.selectByPrimaryKey(classcode).getClassMember();
        Class newClass = new Class();
        newClass.setClassMember(currentMembers + 1);
        newClass.setCreateTime(new Date());
        newClass.setClassCode(classcode);

        classRepository.updateByPrimaryKeySelective(newClass);
//        if(res==-1)
//            return new BaseResponse(500, "????????????","");
//        return new BaseResponse(HttpStatus.OK.value(), "????????????", "");
    }

    public BaseResponse closeClassByCode(JSONObject closeClass){
        String classcode = closeClass.getString("classcode");
        String isClose = closeClass.getString("isclose");
        try{
            Class newClass = new Class();
            newClass.setCreateTime(new Date());
            newClass.setClassCode(classcode);
            // 0->false 1->true
            newClass.setClassIsclose(isClose);

            int res = classRepository.updateByPrimaryKeySelective(newClass);
            if(res==-1)
                return new BaseResponse(500, "????????????","");
            return new BaseResponse(HttpStatus.OK.value(), "????????????", "");
        }catch (Exception e){
            throw new UserFriendException("???????????????????????????????????????????????????");
        }

    }

    public BaseResponse ClassNotAllowedByCode(JSONObject closeClass) {
        String classcode = closeClass.getString("classcode");
        String isallowed = closeClass.getString("isallowed");

        try{
            Class newClass = new Class();
            newClass.setCreateTime(new Date());
            newClass.setClassCode(classcode);
            // 0->false 1->true
            newClass.setClassIsallowed(isallowed);

            int res = classRepository.updateByPrimaryKeySelective(newClass);
            if(res==-1)
                return new BaseResponse(500, "??????????????????????????????","");
            return new BaseResponse(HttpStatus.OK.value(), "??????????????????????????????", "");
        }catch (Exception e){
            throw new UserFriendException("?????????????????????????????????????????????????????????????????????");
        }
    }

    public BaseResponse joinClassByCode(JSONObject joinClass){

        String classcode = joinClass.getString("classcode");
        String username = joinClass.getString("username");
        String userphone = joinClass.getString("userphone");

        try{
            List<ClassInfo> currentclassInfo = classInfoRepository.selectUserByphonecode(classcode, userphone);
            System.out.println(currentclassInfo);
            if (currentclassInfo.size() == 0){
                String isallowd = classRepository.selectByPrimaryKey(classcode).getClassIsallowed();
                if(isallowd == null)
                    isallowd = "1";
                if (isallowd.equals("0")){
                    return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "?????????????????????", "CreateClassFail");
                }else {
                    ClassInfo classInfo = new ClassInfo();
                    classInfo.setClassCode(classcode);
                    classInfo.setUserName(username);
                    classInfo.setUserPhone(userphone);
                    int success = classInfoRepository.insert(classInfo);
                    if(success == -1){
                        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "??????????????????", "CreateClassFail");
                    }else{
                        // ????????????+1
//                        updateClassMemberByCode(joinClass);
                        return new BaseResponse(HttpStatus.OK.value(), "??????????????????", "CreateClassSuccess");
                    }
                }
            }else {
                return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "???????????????????????????", "JoinClassFail");
            }
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("?????????????????????????????????????????????????????????");
        }
    }
    @Override
    public BaseResponse selectAllClasses() {
        try{
            return new BaseResponse(200, "????????????", classRepository.selectAllClasses());
        }catch (Exception e){
            throw new UserFriendException("????????????");
        }
    }

    @Override
    public BaseResponse selectAllClassesInfo(String userphone) {
        try{
            return new BaseResponse(200, "????????????", classDtoAssembler.classinfoAssembler(userphone));
        }catch (Exception e){
            throw new UserFriendException("????????????");
        }

    }


    @Override
    public BaseResponse selectAllClassesInfoPerson(String userphone) {
        try{
            return new BaseResponse(200, "????????????", classDtoAssembler.userClassinfoAssembler(userphone));
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("????????????");
        }

    }

    @Override
    public BaseResponse selectAllInfo() {
        try{
            return new BaseResponse(200, "????????????", classDtoAssembler.allClassinfoAssembler());
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("????????????");
        }
    }

    @Override
    public BaseResponse getStudentCountsByClassCode(String classCode) {
        try{
            List<ClassInfo> classInfoList = classInfoRepository.selectAllClassInfoByClassCode(classCode);
            return new BaseResponse(200, "??????????????????", classInfoList.size());
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("??????????????????");
        }
    }

    @Override
    public BaseResponse getStudentByClassCode(String classCode) {
        try{
            List<ClassInfo> classInfoList = classInfoRepository.selectAllClassInfoByClassCode(classCode);
            return new BaseResponse(200, "????????????????????????", classInfoList);
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("????????????");
        }
    }
}
