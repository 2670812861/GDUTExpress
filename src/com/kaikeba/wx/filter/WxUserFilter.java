package com.kaikeba.wx.filter;

import com.kaikeba.bean.User;
import com.kaikeba.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-18 12:45
 */
@WebFilter("/index.html")
public class WxUserFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)req).getSession();
        User wxUser = UserUtil.getWXUser(session);
        if (wxUser!=null){
            //如果有用户存在，说明用户已经登录，放行。
            chain.doFilter(req, resp);
        }else{
            //用户未登录，跳转到登录页面
            ((HttpServletResponse)resp).sendRedirect("login.html");
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
