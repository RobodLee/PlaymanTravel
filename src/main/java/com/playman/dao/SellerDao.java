package com.playman.dao;

import com.playman.entity.Seller;

/**
 * @author 李迪
 * @date 2020/1/17 16:26
 */
public interface SellerDao {

    Seller findBySid(int sid);

}
