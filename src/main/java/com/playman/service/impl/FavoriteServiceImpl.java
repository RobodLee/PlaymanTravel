package com.playman.service.impl;

import com.playman.dao.FavoriteDao;
import com.playman.dao.impl.FavoriteDaoImpl;
import com.playman.entity.Favorite;
import com.playman.service.FavoriteService;

/**
 * @author 李迪
 * @date 2020/1/20 10:04
 */
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 判断用户是否收藏该线路
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(String rid , int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.valueOf(rid) , uid);
        return favorite != null;
    }

    /**
     * 通过rid查询被收藏的次数
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(String rid) {
        return favoriteDao.findCountByRid(Integer.valueOf(rid));
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(Integer rid, int uid) {
        favoriteDao.addFavorite(rid , uid);
    }

}
