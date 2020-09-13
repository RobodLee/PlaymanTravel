package com.playman.service;

/**
 * @author 李迪
 * @date 2020/1/20 10:04
 */
public interface FavoriteService {

    /**
     * 判断用户是否收藏该线路
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(String rid , int uid);

    /**
     * 通过rid查询被收藏的次数
     * @param rid
     * @return
     */
    int findCountByRid(String rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void addFavorite(Integer rid, int uid);
}
