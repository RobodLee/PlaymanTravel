package com.playman.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        System.out.println(uri);
        String methodName = uri.substring(uri.lastIndexOf("/")+1);
        //methodName = methodName.substring(0 , methodName.lastIndexOf('&'));
        System.out.println(methodName);
        try {
            Method method = this.getClass().getMethod(methodName , HttpServletRequest.class , HttpServletResponse.class);
            method.invoke(this , req , resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void writeValue(HttpServletResponse response , Object object) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        mapper.writeValue(writer , object);
        if (object != null) {
            System.out.println(object.toString());
        }
    }

}
