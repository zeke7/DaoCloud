package com.fzu.gcxl.daocloud.application.service.assembler;

import com.fzu.gcxl.daocloud.domain.repository.DictionaryRespository;
import com.fzu.gcxl.daocloud.domain.repository.DictionarydetailRepository;
import com.fzu.gcxl.daocloud.frontinterface.dto.ClassInfoDto;
import com.fzu.gcxl.daocloud.frontinterface.dto.DictionaryDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DictionaryDtoAssembler {
    @Autowired
    DictionaryRespository dictionaryRespository;

    @Autowired
    DictionarydetailRepository dictionarydetailRepository;

    public List<DictionaryDto> DictionaryInfoAssembler(String dicCode) {



        return null;
    }
}
