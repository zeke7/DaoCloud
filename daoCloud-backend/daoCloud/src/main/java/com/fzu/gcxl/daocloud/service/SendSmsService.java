package com.fzu.gcxl.daocloud.service;

import java.util.Map;

public interface SendSmsService {
    boolean send(String phoneNum, String templateCode, Map<String, Object> code);
}
