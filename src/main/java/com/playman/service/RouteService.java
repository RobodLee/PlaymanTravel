package com.playman.service;

import com.playman.entity.PageBean;
import com.playman.entity.Route;

public interface RouteService {

    PageBean<Route> pageQuery(int cid , int currentPage  , int pageSize , String rname);

    /**
     * 根据rid从数据库中查询出完整的Route对象
     * @param rid
     * @return Route对象
     */
    Route findOne(int rid);
}
