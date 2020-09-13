package com.playman.dao.impl;

import com.playman.dao.FavoriteDao;
import com.playman.entity.Favorite;
import com.playman.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 李迪
 * @date 2020/1/20 10:07
 */
public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from favorite where rid = ? and uid = ? ";
            favorite = template.queryForObject(sql , new BeanPropertyRowMapper<Favorite>(Favorite.class) , rid , uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return favorite;
    }

    /**
     * 通过rid查询被收藏的次数
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(Integer rid) {
        String sql = "select count(*) from favorite where rid = ? ";
        int count = template.queryForObject(sql , Integer.class , rid);
        return count;
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(Integer rid, int uid) {
        String sql = "insert into favorite values (? , ? , ?)";
        template.update(sql , rid , new Date(), uid);
        System.out.println(LocalDateTime.now());
    }
}
