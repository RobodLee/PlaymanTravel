package com.playman.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playman.entity.ResultInfo;
import com.playman.entity.User;
import com.playman.service.UserService;
import com.playman.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    UserService service = new UserServiceImpl();

    //激活账号
    public void activate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        System.out.println("code:" + code);
        PrintWriter out = response.getWriter();

        String message;
        if (service.activate(code)) {
            message = "激活成功，请<a href='../login.html'>登录</a>";
        } else {
            message = "激活失败，请联系管理员";
        }
        out.write(message);
        out.close();
    }

    //退出登录
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    //查找用户
    public void findOneUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("current_user");
        writeValue(response , user);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        ResultInfo info = new ResultInfo();

        String check = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        String checkEx = request.getParameter("check");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if (check==null || checkEx==null || !check.equalsIgnoreCase(checkEx)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(response , info);
            return;
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(user , parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        user = service.login(user);
        if (user != null) {
            if ("N".equals(user.getStatus())) {
                info.setFlag(false);
                info.setErrorMsg("用户未激活");
            } else {
                info.setFlag(true);
                request.getSession().setAttribute("current_user" , user);//将当前的用户存入session中
            }
        } else {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        writeValue(response , info);
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        ResultInfo info = new ResultInfo();

        try {
            BeanUtils.populate(user , parameterMap);
            System.out.println(user.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //判断验证码的对错，如果验证码错误则不进行其它操作
        String checkCode = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        System.out.println(checkCode);
        String checkCodeEx = request.getParameter("check");
        System.out.println(checkCodeEx);
        if (!(checkCodeEx!=null && checkCode!=null && checkCode.equalsIgnoreCase(checkCodeEx))) {
            request.getSession().removeAttribute("CHECKCODE_SERVER");
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(response , info);
            return;
        }

        boolean flag = service.register(user);
        if (flag) {     //注册成功
            info.setFlag(true);
            info.setData("注册成功");
        } else {        //用户已存在，注册失败
            info.setFlag(false);
            info.setErrorMsg("用户已经存在");
        }
        System.out.println(info.toString());
       writeValue(response , info);     //将info对象转换为json字符串并写入输出流中
    }
}
