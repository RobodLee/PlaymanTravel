package com.playman.dao;

import com.playman.entity.Route;

import java.util.List;

public interface RouteDao {

    //查询总条数
    int findTotalCount(int cid , String rname);

    //根据页数查询页面信息
    List<Route> findByPage(int page , int start , int pageSize , String rname);

    Route findOne(int rid);
}
