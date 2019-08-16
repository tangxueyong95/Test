package com.itheima.travel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itheima.travel.domain.Category;

import java.io.IOException;
import java.util.List;

/**
 * 包名:com.itheima.travel.service
 * 作者:Leevi
 * 日期2019-05-29  08:40
 */
public interface ICategoryService {
    /*List<Category> findAll() throws IOException;*/
    String findAll() throws JsonProcessingException;
}
