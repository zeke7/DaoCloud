package com.fzu.gcxl.daocloud.application.service.assembler;

import com.fzu.gcxl.daocloud.domain.entity.Dictionary;
import com.fzu.gcxl.daocloud.domain.entity.Dictionarydetail;
import com.fzu.gcxl.daocloud.domain.entity.User;
import com.fzu.gcxl.daocloud.domain.exception.UserFriendException;
import com.fzu.gcxl.daocloud.domain.repository.DictionaryRespository;
import com.fzu.gcxl.daocloud.domain.repository.DictionarydetailRepository;
import com.fzu.gcxl.daocloud.frontinterface.dto.DictionaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DictionaryDtoAssembler {
    @Autowired
    DictionaryRespository dictionaryRespository;

    @Autowired
    DictionarydetailRepository dictionarydetailRepository;

    /***
     *
     * @return 返回所有字典信息
     */
    public List<DictionaryDto> DictionaryInfoAssembler() {
        List<DictionaryDto> resultlist = new ArrayList<>();
        List<Dictionary> diclist = dictionaryRespository.selectAllDictionaries();
        if (diclist != null){
            for (Dictionary dic: diclist) {
                DictionaryDto dicdto = new DictionaryDto();
                try {
                    setDicDto(dic, dicdto);
                    resultlist.add(dicdto);
                }catch (Exception e){
                    throw new UserFriendException("存在空属性");
                }
            }
            return resultlist;
        }else {
            throw new UserFriendException("没有字典信息");
        }
    }

    /***
     *
     * @return 返回单个字典信息
     */
    public DictionaryDto DictionaryInfoByCodeAssembler(String dicCode) {
        Dictionary dic = dictionaryRespository.selectByPrimaryKey(dicCode);
        if (dic!= null){
            DictionaryDto dicdto = new DictionaryDto();
            try {
                setDicDto(dic, dicdto);
            }catch (Exception e){
                throw new UserFriendException("存在空属性");
            }
            return dicdto;
        }else {
            throw new UserFriendException("没有字典信息");
        }
    }



    private void setDicDto(Dictionary dic, DictionaryDto dicdto) {
        dicdto.setDicId(dic.getDicId());
        dicdto.setDicName(dic.getDicName());
        dicdto.setDicDiscription(dic.getDicDiscription());
        dicdto.setDicValue(dic.getDicValue());
        dicdto.setDicCode(dic.getDicCode());
        List<Dictionarydetail> dlist = dictionarydetailRepository.selectDetialsbyDicCode(dic.getDicCode());
        dicdto.setDetailist(dlist);
    }

}
