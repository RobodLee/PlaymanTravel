package com.playman.dao.impl;

import com.playman.dao.SellerDao;
import com.playman.entity.Seller;
import com.playman.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author 李迪
 * @date 2020/1/17 16:26
 */
public class SellerDaoImpl implements SellerDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public Seller findBySid(int sid) {
        Seller seller = null;
        String sql = "select * from seller where sid = ? ";
        seller = template.queryForObject(sql , new BeanPropertyRowMapper<Seller>(Seller.class) , sid);
        return seller;
    }
}
