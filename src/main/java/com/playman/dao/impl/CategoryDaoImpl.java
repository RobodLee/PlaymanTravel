package com.playman.dao.impl;

import com.playman.dao.CategoryDao;
import com.playman.entity.Category;
import com.playman.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //返回所有分类数据
    @Override
    public List<Category> findAll() {
        List<Category> list;
        try {
            String sql = "select * from category";
            list = template.query(sql , new BeanPropertyRowMapper<>(Category.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
}
