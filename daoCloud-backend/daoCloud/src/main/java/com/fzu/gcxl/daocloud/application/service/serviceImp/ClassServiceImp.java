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
import com.fzu.gcxl.daocloud.frontinterface.dto.ClassInfoDto;
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
        String classno = createClass.getString("classcode");
        String semester = createClass.getString("classsemester");
        try{
            Class newClass = new Class();
            newClass.setClassName(classname);
            newClass.setUserPhone(userPhone);
            newClass.setClassMember(classmember);
            newClass.setClassCode(classno);
            newClass.setCreateTime(new Date());
            newClass.setClassSemester(semester);
            // 0->false 1->true
            newClass.setClassIsclose("0");

            if (classRepository.selectByClassName(classname) == null && classRepository.selectByPrimaryKey(classno) == null){
                int success = classRepository.insert(newClass);
                if(success != 0){
                    return new BaseResponse(HttpStatus.OK.value(), "班课创建成功", "CreateClassSuccess");
                }else{
                    return new BaseResponse(HttpStatus.OK.value(), "班课创建失败", "CreateClassFail");
                }
            }else {
                return new BaseResponse(HttpStatus.OK.value(), "班课创建失败, 班课名称或班课编号重复", "CreateClassFail_Num_Name");
            }
        }catch (Exception e){
            throw new UserFriendException("班课创建失败");
        }
    }

    public BaseResponse getClassesByCode(String classCode){
        try{
            Class classes = classRepository.selectByPrimaryKey(classCode);
            return new BaseResponse(HttpStatus.OK.value(), "获取当前班课", classes);
        }catch (Exception e){
            throw new UserFriendException("获取当前班课失败");
        }
    }

    public BaseResponse getClassesByUser(String userphone){
        try{
            List<Class> classes = classRepository.selectAllClassesByUser(userphone);
            return new BaseResponse(HttpStatus.OK.value(), "获取当前班课", classes);
        }catch (Exception e){
            throw new UserFriendException("获取当前班课失败");
        }
    }

    public BaseResponse delClassByCode(String classcode){
        try{
            int res = classRepository.deleteByPrimaryKey(classcode);
            if(res==-1)
                return new BaseResponse(500, "删除失败","");
            return new BaseResponse(HttpStatus.OK.value(), "删除成功", "");
        }catch (Exception e){
            throw new UserFriendException("删除失败");
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
                    return new BaseResponse(500, "更新失败","");
                return new BaseResponse(HttpStatus.OK.value(), "更新成功", "");
            }else {
                return new BaseResponse(500, "更新失败","");
            }
        }catch (Exception e){
            throw new UserFriendException("更新失败，参数没有通过有效性验证。");
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
//            return new BaseResponse(500, "更新失败","");
//        return new BaseResponse(HttpStatus.OK.value(), "更新成功", "");
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
                return new BaseResponse(500, "关闭失败","");
            return new BaseResponse(HttpStatus.OK.value(), "关闭成功", "");
        }catch (Exception e){
            throw new UserFriendException("关闭失败，参数没有通过有效性验证。");
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
            newClass.setClassIsclose(isallowed);

            int res = classRepository.updateByPrimaryKeySelective(newClass);
            if(res==-1)
                return new BaseResponse(500, "班课加入权限更改失败","");
            return new BaseResponse(HttpStatus.OK.value(), "班课加入权限更改成功", "");
        }catch (Exception e){
            throw new UserFriendException("班课加入权限更改失败，参数没有通过有效性验证。");
        }
    }

    public BaseResponse joinClassByCode(JSONObject joinClass){

        String classcode = joinClass.getString("classcode");
        String username = joinClass.getString("username");
        String userphone = joinClass.getString("userphone");

        try{
            if (classInfoRepository.selectUserByphonecode(userphone, classcode) == null){
                if (classRepository.selectByPrimaryKey(classcode).getClassIsallowed().equals("1")){
                    return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "班课不允许加入", "CreateClassFail");
                }else {
                    ClassInfo classInfo = new ClassInfo();
                    classInfo.setClassCode(classcode);
                    classInfo.setUserName(username);
                    classInfo.setUserPhone(userphone);
                    int success = classInfoRepository.insert(classInfo);
                    if(success == -1){
                        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "班课加入失败", "CreateClassFail");
                    }else{
                        // 班级人数+1
                        updateClassMemberByCode(joinClass);
                        return new BaseResponse(HttpStatus.OK.value(), "班课加入成功", "CreateClassSuccess");
                    }
                }
            }else {
                return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "该用户已在该班课中", "JoinClassFail");
            }
        }catch (Exception e){
            throw new UserFriendException("班课加入失败，参数没有通过有效性验证。");
        }
    }
    @Override
    public BaseResponse selectAllClasses() {
        try{
            return new BaseResponse(200, "查询成功", classRepository.selectAllClasses());
        }catch (Exception e){
            throw new UserFriendException("查询失败");
        }
    }

    @Override
    public BaseResponse selectAllClassesInfo(String userphone) {
        try{
            return new BaseResponse(200, "查询成功", classDtoAssembler.classinfoAssembler(userphone));
        }catch (Exception e){
            throw new UserFriendException("查询失败");
        }

    }

    @Override
    public BaseResponse selectAllInfo() {
        try{
            return new BaseResponse(200, "查询成功", classDtoAssembler.allClassinfoAssembler());
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("查询失败");
        }
    }
}
