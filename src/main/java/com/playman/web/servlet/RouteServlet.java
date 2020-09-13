package com.playman.web.servlet;

import com.playman.entity.PageBean;
import com.playman.entity.Route;
import com.playman.entity.User;
import com.playman.service.FavoriteService;
import com.playman.service.RouteService;
import com.playman.service.impl.FavoriteServiceImpl;
import com.playman.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cidString = request.getParameter("cid");
        String currentPageString = request.getParameter("currentPage");
        String pageSizeString = request.getParameter("pageSize");
        String rname = request.getParameter("rname");

        int cid = 0;
        int currentPage = 1;
        int pageSize = 5;

        if (cidString!=null && cidString.length()>0 && !"null".equals(cidString)) {
            cid = Integer.valueOf(cidString);
        }
        if (currentPageString!=null && currentPageString.length()>0) {
            currentPage = Integer.valueOf(currentPageString);
        }
        if (pageSizeString!=null && pageSizeString.length()>0) {
            pageSize = Integer.valueOf(pageSizeString);
        }
        PageBean<Route> pageBean = routeService.pageQuery(cid , currentPage , pageSize , rname);
        response.setContentType("application/json; charset=utf-8");
        writeValue(response , pageBean);
    }

    /**
     * 查询route对象
     */
    public void findOne(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        Route route = routeService.findOne(Integer.valueOf(rid));
        int count = favoriteService.findCountByRid(rid);
        route.setCount(count);
        writeValue(response , route);
    }

    /**
     *  判断是否收藏
     */
    public void isFavorite(HttpServletRequest request , HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("current_user");
        System.out.println(user);
        String rid = request.getParameter("rid");
        int uid ;
        if (user == null) {
            writeValue(response , false);
        } else {
            uid = user.getUid();
            writeValue(response , favoriteService.isFavorite(rid , uid));
        }
    }

    /**
     * 添加收藏
     */
    public void addFavorite(HttpServletRequest request , HttpServletResponse response) {
        String rid = request.getParameter("rid");
        int uid;
        User user = (User) request.getSession().getAttribute("current_user");
        if (user == null) {
            //用户没有登陆，不做任何操作
        } else {
            uid = user.getUid();
            favoriteService.addFavorite(Integer.valueOf(rid) , uid);
        }
    }
}
