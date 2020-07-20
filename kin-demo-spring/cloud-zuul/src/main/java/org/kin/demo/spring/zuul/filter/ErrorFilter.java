package org.kin.demo.spring.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * 处理除了post以外的异常
 *
 * @author huangjianqin
 * @date 2020-07-19
 */
@Component
public class ErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        Throwable throwable = currentContext.getThrowable();
        currentContext.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        currentContext.set("error.exception", throwable.getCause());
        return null;
    }
}
