package com.playman.dao.impl;

import com.playman.dao.RouteImgDao;
import com.playman.entity.RouteImg;
import com.playman.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author 李迪
 * @date 2020/1/17 15:54
 */
public class RouteImgDaoImpl implements RouteImgDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 通过rid从数据库中查询Img_List
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findImgListByRid(int rid) {
        String sql = "select * from route_img where rid = ? ";
        return template.query(sql , new BeanPropertyRowMapper<RouteImg>(RouteImg.class) , rid);
    }
}
