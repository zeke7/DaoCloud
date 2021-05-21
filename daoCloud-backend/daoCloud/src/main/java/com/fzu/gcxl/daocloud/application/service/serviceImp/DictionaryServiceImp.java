package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.DictionaryService;
import com.fzu.gcxl.daocloud.domain.entity.Dictionary;
import com.fzu.gcxl.daocloud.domain.entity.Dictionarydetail;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.exception.UserFriendException;
import com.fzu.gcxl.daocloud.domain.repository.DictionaryRespository;
import com.fzu.gcxl.daocloud.domain.repository.DictionarydetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryServiceImp implements DictionaryService {

    @Autowired
    DictionaryRespository dictionaryRespository;

    @Autowired
    DictionarydetailRepository dictionarydetailRepository;

    public BaseResponse createNewDictionary(JSONObject dictionary){
        // 创建大类别
        String diccode = dictionary.getString("dictionarycode");
        String dicname = dictionary.getString("dicname");
        String dicdiscp = dictionary.getString("dicdiscp");
        String dicvalue = dictionary.getString("dicdetailvalue");

        try{
            Dictionary newDictionary = new Dictionary();
            newDictionary.setDicCode(diccode);
            newDictionary.setDicName(dicname);
            newDictionary.setDicDiscription(dicdiscp);
            newDictionary.setDicValue(dicvalue);

            if (dictionaryRespository.selectByPrimaryKey(diccode) != null) {
                return new BaseResponse(500, "添加字典类别失败，code已经存在","");
            }
            int res = dictionaryRespository.insert(newDictionary);
            if(res==-1)
                return new BaseResponse(500, "添加字典类别失败","");
            return new BaseResponse(200, "添加字典类别成功","");
        }catch (Exception e){
            throw new UserFriendException("添加字典类别失败");
        }

    }

    public BaseResponse deleteDictionaryByCode(String code){
        try{
            if (dictionarydetailRepository.selectDetialsbyDicCode(code) != null){
                return new BaseResponse(500, "删除字典类别失败","");
            }else {
                if (dictionaryRespository.selectByPrimaryKey(code) != null){
                    int res = dictionaryRespository.deleteByPrimaryKey(code);
                    if (res == -1)
                        return new BaseResponse(500, "删除字典类别失败", "");
                    return new BaseResponse(200, "删除字典类别成功", "");
                }else{
                    return new BaseResponse(500, "删除字典类别失败，dic不存在。", "");
                }
            }
        }catch (Exception e){
            throw new UserFriendException("删除字典类别失败");
        }
    }

    public BaseResponse updateDictionary(JSONObject dictionary){
        String diccode = dictionary.getString("dictionarycode");
        String dicname = dictionary.getString("dicname");
        String dicdiscp = dictionary.getString("dicdiscp");
        String dicvalue = dictionary.getString("dicdetailvalue");

        try{
            Dictionary newDictionary = new Dictionary();
            newDictionary.setDicCode(diccode);
            newDictionary.setDicName(dicname);
            newDictionary.setDicDiscription(dicdiscp);
            newDictionary.setDicValue(dicvalue);

            if (dictionaryRespository.selectByPrimaryKey(diccode) != null) {
                int res = dictionaryRespository.updateByPrimaryKeySelective(newDictionary);
                if(res==-1)
                    return new BaseResponse(500, "修改字典类别失败。","");
                return new BaseResponse(200, "修改字典类别成功","");
            }else {
                return new BaseResponse(500, "修改字典类别失败，dic_id不存在。","");
            }
        }catch (Exception e){
            throw new UserFriendException("修改字典类别失败");
        }

    }

    public BaseResponse selectDictionaryByCode(String code){
        try {
            return new BaseResponse(200, "查询成功", dictionaryRespository.selectByPrimaryKey(code));
        }catch (Exception e){
            throw new UserFriendException("查询失败");
        }
    }

    // 创建字典细节数据
    public BaseResponse createNewDictionaryDetail(JSONObject dictionary){
        String diccode = dictionary.getString("dictionarycode");
        String dicdetailcode = dictionary.getString("dicdetailcode");
        String dicdetailname = dictionary.getString("dicdetailname");
        String dicdetaildiscp = dictionary.getString("dicdetaildiscp");
        String dicdetailvalue = dictionary.getString("dicdetailvalue");
        try{
            if (dictionaryRespository.selectByPrimaryKey(diccode) == null){
                return new BaseResponse(500, "添加字典详情失败,字典类别不存在","");
            }else{
                Dictionarydetail newdicdetail= new Dictionarydetail();
                newdicdetail.setDicCode(diccode);
                newdicdetail.setDicdetailCode(dicdetailcode);
                newdicdetail.setDicdetailName(dicdetailname);
                newdicdetail.setDicdetailDescription(dicdetaildiscp);
                newdicdetail.setDicdetailValue(dicdetailvalue);

                if (dictionarydetailRepository.selectByPrimaryKey(dicdetailcode) != null){
                    return new BaseResponse(500, "添加字典详情失败，已经存在。","");
                }else{
                    int res = dictionarydetailRepository.insert(newdicdetail);
                    if (res == -1)
                        return new BaseResponse(500, "添加字典详情失败","");
                    return new BaseResponse(200, "添加字典详情成功","");
                }
            }
        }catch (Exception e){
            throw new UserFriendException("添加字典详情失败");
        }

    }

    public BaseResponse deleteDictionaryDetailsByCode(String detailcode){
        if (dictionarydetailRepository.selectByPrimaryKey(detailcode) != null){
            int res = dictionarydetailRepository.deleteByPrimaryKey(detailcode);
            if(res==-1)
                return new BaseResponse(500, "删除字典详情失败。","");
            return new BaseResponse(200, "删除字典详情成功","");
        }else{
            return new BaseResponse(500, "删除字典详情失败，不存在。","");
        }

    }

    public BaseResponse updateDictionaryDetails(JSONObject dictionary){

        String diccode = dictionary.getString("dictionarycode");
        String dicdetailcode = dictionary.getString("dicdetailcode");
        String dicdetailname = dictionary.getString("dicdetailname");
        String dicdetaildiscp = dictionary.getString("dicdetaildiscp");
        String dicdetailvalue = dictionary.getString("dicdetailvalue");

        try{
            Dictionarydetail newDictionary = new Dictionarydetail();
            newDictionary.setDicdetailCode(dicdetailcode);
            newDictionary.setDicdetailValue(dicdetailvalue);
            newDictionary.setDicdetailName(dicdetailname);
            newDictionary.setDicdetailDescription(dicdetaildiscp);

            if (dictionarydetailRepository.selectByDicAndDetialCode(diccode, dicdetailcode) == null){
                return new BaseResponse(500, "修改字典类别失败，dicdetail不存在。","");
            }else{
                int res = dictionarydetailRepository.updateByPrimaryKeySelective(newDictionary);
                if (res == -1)
                    return new BaseResponse(500, "修改字典类别失败","");
                return new BaseResponse(200, "修改字典类别成功","");
            }
        }catch (Exception e){
            throw new UserFriendException("修改字典类别失败");
        }

    }

    public BaseResponse selectDictionaryDetailsByCode(String detailcode) {
        try {
            return new BaseResponse(200, "查询成功", dictionarydetailRepository.selectByPrimaryKey(detailcode));
        }catch (Exception e){
            throw new UserFriendException("查询失败");
        }
    }

    @Override
    public BaseResponse selectAllDictionaries() {
        try {
            return new BaseResponse(200, "查询成功", dictionaryRespository.selectAllDictionaries());
        }catch (Exception e){
            throw new UserFriendException("查询失败");
        }
    }

    @Override
    public BaseResponse selectAllDictionaryDetails(String dicCode) {
        try{
            return new BaseResponse(200, "查询成功", dictionarydetailRepository.selectDetialsbyDicCode(dicCode));
        }catch (Exception e){
            throw new UserFriendException("查询失败");
        }
    }
}
