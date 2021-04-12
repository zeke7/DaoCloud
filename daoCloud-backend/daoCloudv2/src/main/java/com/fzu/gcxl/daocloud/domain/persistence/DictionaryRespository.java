package com.fzu.gcxl.daocloud.domain.persistence;

import com.fzu.gcxl.daocloud.domain.entity.Dictionary;
import com.fzu.gcxl.daocloud.domain.entity.DictionaryDetail;

import java.util.List;

public interface DictionaryRespository {

    // 大类别
    int add(Dictionary dictionary);

    int del(int dic_id);

    int edit(Dictionary dictionary);

    List<Dictionary> findAll();

    Dictionary find(int dic_id);

    //详情增删改查
    int detail_add(int typeid,String code, String name,int serialNumber);

    int detail_del(int dicd_id);

    int detail_edit(int dicd_id,int typeid,String code,String name, int serialNumber,int state);

    List<DictionaryDetail> detail_findAll(int typeid);


}
