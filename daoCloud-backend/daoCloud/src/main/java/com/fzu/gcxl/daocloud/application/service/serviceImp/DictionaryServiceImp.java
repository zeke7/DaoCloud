package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.DictionaryService;
import com.fzu.gcxl.daocloud.domain.entity.Dictionary;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.repository.DictionaryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionaryServiceImp implements DictionaryService {
    @Autowired
    DictionaryRespository dictionaryRespository;

    public BaseResponse createNewDictionary(JSONObject dictionary){
        String diccode = dictionary.getString("dictionarycode");
        String dicname = dictionary.getString("dicname");
        String dicdiscp = dictionary.getString("dicdiscp");
        String dicdetailcode = dictionary.getString("dicdetailcode");
        String dicdetailname = dictionary.getString("dicdetailname");
        String dicdetaildiscp = dictionary.getString("dicdetaildiscp");
        String dicdetailvalue = dictionary.getString("dicdetailvalue");

        Dictionary newDictionary = new Dictionary();
        newDictionary.setDicCode(diccode);
        newDictionary.setDicName(dicname);
        newDictionary.setDicDiscription(dicdiscp);
        newDictionary.setDicDetailname(dicdetailname);
        newDictionary.setDicDetailcode(dicdetailcode);
        newDictionary.setDicDetaildescription(dicdetaildiscp);
        newDictionary.setDicValue(dicdetailvalue);

        if (dictionaryRespository.selectByPrimaryKey(diccode) != null) {
            return new BaseResponse(500, "添加字典类别失败，code已经存在。","");
        }
        int res = dictionaryRespository.insert(newDictionary);
        if(res==-1)
            return new BaseResponse(500, "添加字典类别失败，code已经存在。","");
        return new BaseResponse(200, "添加字典类别成功","");

    }

    public BaseResponse deleteDictionaryByCode(String code){
        int res = dictionaryRespository.deleteByPrimaryKey(code);
        if(res==-1)
            return new BaseResponse(500, "删除字典类别失败，dic_id不存在。","");
        return new BaseResponse(200, "删除字典类别成功","");
    }

    public BaseResponse updateDictionary(JSONObject dictionary){
        String diccode = dictionary.getString("dictionarycode");
        String dicname = dictionary.getString("dicname");
        String dicdiscp = dictionary.getString("dicdiscp");
        String dicdetailcode = dictionary.getString("dicdetailcode");
        String dicdetailname = dictionary.getString("dicdetailname");
        String dicdetaildiscp = dictionary.getString("dicdetaildiscp");
        String dicdetailvalue = dictionary.getString("dicdetailvalue");

        Dictionary newDictionary = new Dictionary();
        newDictionary.setDicCode(diccode);
        newDictionary.setDicName(dicname);
        newDictionary.setDicDiscription(dicdiscp);
        newDictionary.setDicDetailname(dicdetailname);
        newDictionary.setDicDetailcode(dicdetailcode);
        newDictionary.setDicDetaildescription(dicdetaildiscp);
        newDictionary.setDicValue(dicdetailvalue);


        if (dictionaryRespository.selectByPrimaryKey(diccode) != null) {
            int res = dictionaryRespository.updateByPrimaryKeySelective(newDictionary);
            if(res==-1)
                return new BaseResponse(500, "修改字典类别失败。","");
            return new BaseResponse(200, "修改字典类别成功","");
        }else {
            return new BaseResponse(500, "修改字典类别失败，dic_id不存在。","");
        }

    }

    public BaseResponse selectDictionaryByCode(String code){
        return new BaseResponse(200, "查询成功", dictionaryRespository.selectByPrimaryKey(code));
    }


    public BaseResponse createNewDictionaryDetail(JSONObject dictionary){
        String diccode = dictionary.getString("dictionarycode");
        String dicname = dictionary.getString("dicname");
        String dicdiscp = dictionary.getString("dicdiscp");
        String dicdetailcode = dictionary.getString("dicdetailcode");
        String dicdetailname = dictionary.getString("dicdetailname");
        String dicdetaildiscp = dictionary.getString("dicdetaildiscp");
        String dicdetailvalue = dictionary.getString("dicdetailvalue");

        Dictionary newDictionary = new Dictionary();
        newDictionary.setDicCode(diccode);
        newDictionary.setDicName(dicname);
        newDictionary.setDicDiscription(dicdiscp);
        newDictionary.setDicDetailname(dicdetailname);
        newDictionary.setDicDetailcode(dicdetailcode);
        newDictionary.setDicDetaildescription(dicdetaildiscp);
        newDictionary.setDicValue(dicdetailvalue);

        if (dictionaryRespository.selectByPrimaryKey(diccode) != null) {
            if (dictionaryRespository.selectByDetailCode(diccode, dicdetailcode) != null){
                return new BaseResponse(500, "添加字典详情失败，已经存在。","");
            }else{
                int res = dictionaryRespository.insert(newDictionary);
                if (res == -1)
                    return new BaseResponse(500, "添加字典详情失败","");
                return new BaseResponse(200, "添加字典详情成功","");
            }
        }else {
            return new BaseResponse(500, "添加字典详情失败，字典类别不存在存在。","");
        }
    }

    public BaseResponse deleteDictionaryDetailsByCode(String diccode, String detailcode){
        int res = dictionaryRespository.deleteByDetailCode(diccode, detailcode);
        if(res==-1)
            return new BaseResponse(500, "删除字典类别失败，dic_id不存在。","");
        return new BaseResponse(200, "删除字典类别成功","");
    }

    public BaseResponse updateDictionaryDetails(JSONObject dictionary){
        String diccode = dictionary.getString("dictionarycode");
        String dicname = dictionary.getString("dicname");
        String dicdiscp = dictionary.getString("dicdiscp");
        String dicdetailcode = dictionary.getString("dicdetailcode");
        String dicdetailname = dictionary.getString("dicdetailname");
        String dicdetaildiscp = dictionary.getString("dicdetaildiscp");
        String dicdetailvalue = dictionary.getString("dicdetailvalue");

        Dictionary newDictionary = new Dictionary();
        newDictionary.setDicCode(diccode);
        newDictionary.setDicName(dicname);
        newDictionary.setDicDiscription(dicdiscp);
        newDictionary.setDicDetailname(dicdetailname);
        newDictionary.setDicDetailcode(dicdetailcode);
        newDictionary.setDicDetaildescription(dicdetaildiscp);
        newDictionary.setDicValue(dicdetailvalue);

        if (dictionaryRespository.selectByPrimaryKey(diccode) != null) {
            if (dictionaryRespository.selectByDetailCode(diccode, dicdetailcode) == null){
                return new BaseResponse(500, "修改字典类别失败，dic_id不存在。","");
            }else{
                int res = dictionaryRespository.updateByPrimaryKey(newDictionary);
                if (res == -1)
                    return new BaseResponse(500, "修改字典类别失败","");
                return new BaseResponse(200, "修改字典类别成功","");
            }
        }else {
            return new BaseResponse(500, "修改字典类别失败，dic_id不存在。","");
        }
    }

    public BaseResponse selectDictionaryDetailsByCode(String diccode, String detailcode){
        return new BaseResponse(200, "查询成功", dictionaryRespository.selectByDetailCode(detailcode, diccode));
    }

    @Override
    public BaseResponse selectAllDictionaries() {
        return new BaseResponse(200, "查询成功", dictionaryRespository.selectAllDictionaries());
    }
}
