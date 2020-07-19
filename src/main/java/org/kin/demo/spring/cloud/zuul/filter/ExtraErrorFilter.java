package org.kin.demo.spring.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
@Component
public class ExtraErrorFilter extends SendErrorFilter {

    @Override
    public int filterOrder() {
        return 30;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        ZuulFilter zuulFilter = (ZuulFilter) currentContext.get("failed.filter");
        if (Objects.nonNull(zuulFilter) && zuulFilter.filterType().equals("post")) {
            return true;
        }
        return false;
    }
}
