package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "FilterAuth")
public class FilterAuth implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
       if(((HttpServletRequest) req).getSession().getAttribute("uemail") ==null){
           ((HttpServletResponse) resp).sendRedirect("/JSF_Web_Application_war_exploded/Login.xhtml");
       }else
       {
           chain.doFilter(req,resp);
       }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
