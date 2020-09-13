package com.playman.service.impl;

import com.playman.dao.RouteDao;
import com.playman.dao.RouteImgDao;
import com.playman.dao.SellerDao;
import com.playman.dao.impl.RouteDaoImpl;
import com.playman.dao.impl.RouteImgDaoImpl;
import com.playman.dao.impl.SellerDaoImpl;
import com.playman.entity.PageBean;
import com.playman.entity.Route;
import com.playman.entity.RouteImg;
import com.playman.entity.Seller;
import com.playman.service.RouteService;

import java.util.List;

/**
 * @author 李迪
 */
public class RouteServiceImpl implements RouteService {

    RouteDao dao = new RouteDaoImpl();
    RouteImgDao imgDao = new RouteImgDaoImpl();
    SellerDao sellerDao = new SellerDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize , String rname) {
        PageBean<Route> pageBean = new PageBean<Route>();
        int totalCount = dao.findTotalCount(cid , rname);
        int totalPage = totalCount%pageSize==0 ? totalCount/pageSize : (totalCount/pageSize)+1;
        int start = (currentPage-1) * pageSize;
        List<Route> list = dao.findByPage(cid , start , pageSize , rname);

        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setList(list);
        return pageBean;
    }

    /**
     * 根据rid从数据库中查询出完整的Route对象
     * @param rid
     * @return Route对象
     */
    @Override
    public Route findOne(int rid) {
        Route route = null;
        route = dao.findOne(rid);
        List<RouteImg> imgList = imgDao.findImgListByRid(rid);
        route.setRouteImgList(imgList);
        Seller seller = sellerDao.findBySid(route.getSid());
        route.setSeller(seller);
        return route;
    }
}
