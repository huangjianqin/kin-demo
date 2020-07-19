package org.kin.demo.spring.cloud.zuul.filter;

import com.netflix.zuul.FilterProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@Component
public class ExceptionFilterProcessor extends FilterProcessor {
    @Override
    public Object processZuulFilter(ZuulFilter filter) throws ZuulException {
        try {
            return super.processZuulFilter(filter);
        } catch (Exception e) {
            RequestContext currentContext = RequestContext.getCurrentContext();
            currentContext.set("failed.filter", filter);
            throw e;
        }
    }
}
