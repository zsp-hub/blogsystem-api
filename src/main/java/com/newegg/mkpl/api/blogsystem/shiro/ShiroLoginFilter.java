package com.newegg.mkpl.api.blogsystem.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.newegg.mkpl.api.blogsystem.enums.StateEnum;
import com.newegg.mkpl.api.blogsystem.pojo.Pack;
import com.newegg.mkpl.api.blogsystem.util.PoolStatic;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 处理未登录重定向
 *
 * @author vz04
 * @date 8/14/2019 3:55 PM
 **/

public class ShiroLoginFilter extends FormAuthenticationFilter {


    /**
     * 如果isAccessAllowed返回false 则执行onAccessDenied
     *
     * @param request     ServletRequest
     * @param response    ServletResponse
     * @param mappedValue Object
     * @return boolean
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (request instanceof HttpServletRequest) {
            String options = "OPTIONS";
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals(options)) {
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws IOException IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //这里是个坑，如果不设置的接受的访问源，那么前端都会报跨域错误，因为这里还没到corsConfig里面
        httpServletResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");

        Pack pack = new Pack();
        pack.fail(StateEnum.NO_AUTHORITY.value(), PoolStatic.PLEASE_LOGIN);

        httpServletResponse.getWriter().write(pack.toString());
        return false;
    }
}
