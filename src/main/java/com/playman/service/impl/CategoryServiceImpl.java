package com.playman.service.impl;

import com.playman.dao.CategoryDao;
import com.playman.dao.impl.CategoryDaoImpl;
import com.playman.entity.Category;
import com.playman.service.CategoryService;
import com.playman.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
//        Set<String> categoriesRedis = jedis.zrange("category", 0, -1);
        Set<Tuple> categoriesRedis = jedis.zrangeWithScores("category", 0, -1);
        List<Category> categoriesMySQL;
        if (categoriesRedis==null || categoriesRedis.size()==0) {
            //redis中没有数据，从MySQL中查询数据
            System.out.println("从数据库中查询");
            categoriesMySQL = dao.findAll();
            for (int i=0 ; i<categoriesMySQL.size() ; i++) {
                jedis.zadd("category" , categoriesMySQL.get(i).getCid() , categoriesMySQL.get(i).getCname());
            }
            //sort方法的实现原理是一个冒泡排序
            Collections.sort(categoriesMySQL);
        } else {
            //redis中有数据
            System.out.println("从redis中查询");
            categoriesMySQL = new ArrayList<>();
            for (Tuple tuple : categoriesRedis) {
                Category category = new Category();
                category.setCid((int) tuple.getScore());
                category.setCname(tuple.getElement());
                categoriesMySQL.add(category);
            }
        }
        return categoriesMySQL;
    }
}
