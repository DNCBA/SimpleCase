package com.mhc.spring.framework.servlet;


import com.mhc.spring.framework.context.MHCContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MHCDispatcherServlet extends HttpServlet {




    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);

        //根据请求的url进行映射

        //执行对应的方法

        //根据结果进行渲染

    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        //初始化context容器
        MHCContext context = new MHCContext();




    }
}
