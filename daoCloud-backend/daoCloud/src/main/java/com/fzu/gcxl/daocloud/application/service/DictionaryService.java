package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface DictionaryService {

    public BaseResponse createNewDictionary(JSONObject dictionary);

    public BaseResponse deleteDictionaryByCode(String code);

    public BaseResponse updateDictionary(JSONObject dictionary);

    public BaseResponse selectDictionaryByCode(String code);

    public BaseResponse createNewDictionaryDetail(JSONObject dictionary);

    public BaseResponse deleteDictionaryDetailsByCode(String detailcode);

    public BaseResponse updateDictionaryDetails(JSONObject dictionary);

    public BaseResponse selectDictionaryDetailsByCode(String detailcode);

    // 选择所有的参数
    public BaseResponse selectAllDictionaries();
    public BaseResponse selectAllDictionaryDetails(String dicCode);

    // 返回dto
    public BaseResponse selectAllDicdto();
    public BaseResponse selectAllDicdtoBycode(String dicCode);

}
