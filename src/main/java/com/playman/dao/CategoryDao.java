package com.playman.dao;

import com.playman.entity.Category;

import java.util.List;

public interface CategoryDao {

    //返回所有分类数据
    List<Category> findAll();

}
