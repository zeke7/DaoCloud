package com.fzu.gcxl.daocloud.domain.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DBResponse <T> implements Serializable {
        //请求成功返回码为：0000
        private static final String successCode = "200";
        //返回数据
        private T data;

        private String code;
        private String msg;
}
