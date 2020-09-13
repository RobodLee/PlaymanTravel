package com.playman.service;

import com.playman.entity.Category;

import java.util.List;

public interface CategoryService {

    //返回所有分类数据
    List<Category> findAll();

}
