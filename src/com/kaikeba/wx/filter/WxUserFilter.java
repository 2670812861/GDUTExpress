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
 * @Author: ������
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
            //������û����ڣ�˵���û��Ѿ���¼�����С�
            chain.doFilter(req, resp);
        }else{
            //�û�δ��¼����ת����¼ҳ��
            ((HttpServletResponse)resp).sendRedirect("login.html");
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
