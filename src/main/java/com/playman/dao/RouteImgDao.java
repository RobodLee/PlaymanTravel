package com.playman.dao;

import com.playman.entity.RouteImg;

import java.util.List;

/**
 * @author 李迪
 * @date 2020/1/17 15:53
 */
public interface RouteImgDao {

    /**
     * 通过rid从数据库中查询Img_List
     * @param rid
     * @return
     */
    List<RouteImg> findImgListByRid(int rid);

}
