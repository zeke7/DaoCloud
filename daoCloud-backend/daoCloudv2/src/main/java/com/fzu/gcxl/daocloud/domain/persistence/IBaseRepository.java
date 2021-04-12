package com.fzu.gcxl.daocloud.domain.persistence;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface IBaseRepository<T> {
    int add(T t);
    int update (T t);
    int getCount(@Param("searchName")String search);
    List<T> getPage(@Param("searchName")String search, @Param("start")int start, @Param("num")int num);
    T getById(@Param("id")String id);
    List<T> getAll();
    int delete(@Param("id")int id);
}
