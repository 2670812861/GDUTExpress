package filter;

import com.kaikeba.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-11 22:01
 */
@WebFilter({"/admin/index.html","/admin/views/*","/express/*"})
public class AccessControlFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request  = (HttpServletRequest) req;
        HttpServletResponse response  = (HttpServletResponse) resp;
        String username = UserUtil.getUserName(request.getSession());
        if (username!=null){
            chain.doFilter(req, resp);
        }else{
            response.sendError(404,"很遗憾，权限不足");
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
