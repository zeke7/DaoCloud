package com.fzu.gcxl.daocloud.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse <T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int errCode;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;
}
