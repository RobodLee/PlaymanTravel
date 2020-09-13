package com.playman.dao.impl;

import com.playman.dao.RouteDao;
import com.playman.entity.Route;
import com.playman.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid , String rname) {
        StringBuilder builder = new StringBuilder("select count(*) from route where 1 = 1 ");
        List parameters = new ArrayList();
        if (cid!=0) {   //判断是否有cid，为0则说明没有该参数
            builder.append(" and cid = ? ");
            parameters.add(cid);
        }
        if (rname!=null && rname.length()!=0) {
            builder.append(" and rname like ? ");
            parameters.add("%"+rname+"%");
        }
        System.out.println(builder.toString());
        return template.queryForObject(builder.toString() , Integer.class , parameters.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize , String rname) {
        List<Route> routeList = null;
        try {
            StringBuilder builder = new StringBuilder("select * from route where 1 = 1 ");
            List parameters = new ArrayList();
            if (cid != 0) {
                builder.append(" and cid = ? ");
                parameters.add(cid);
            }
            if (rname!=null && rname.length()!=0) {
                builder.append(" and rname like ? ");
                parameters.add("%"+rname+"%");
            }
            builder.append(" limit ? , ?");
            System.out.println(builder.toString());
            parameters.add(start);
            parameters.add(pageSize);
            routeList = template.query(builder.toString() , new BeanPropertyRowMapper<>(Route.class) , parameters.toArray() );
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return routeList;
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from route where rid = ? ";
        return template.queryForObject(sql , new BeanPropertyRowMapper<Route>(Route.class) , rid);
    }
}
