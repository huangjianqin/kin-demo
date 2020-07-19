package org.kin.demo.spring.cloud.zuul;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @author huangjianqin
 * @date 2020-07-19
 */
public class SimpleErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
        //不返回客户端异常信息
        map.remove("exception");
        return map;
    }
}
