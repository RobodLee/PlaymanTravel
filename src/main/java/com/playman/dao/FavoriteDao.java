package com.playman.dao;

import com.playman.entity.Favorite;

/**
 * @author 李迪
 * @date 2020/1/20 10:07
 */
public interface FavoriteDao {

    /**
     * 查询是否有相应的Favorite对象
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid , int uid);

    /**
     * 通过rid查询被收藏的次数
     * @param rid
     * @return
     */
    int findCountByRid(Integer rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void addFavorite(Integer rid, int uid);
}
