package com.imooc.coupon.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * <h1>通用的抽象过滤器类</h1>
 */
public abstract class AbstractZuulFilter extends ZuulFilter {

//    用于早过滤器之间传递消息，数据保持在每个请求的 ThreadLocal 中
//    拓展map
    RequestContext context;

    private static final String NEXT = "next";

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return (boolean) context.getOrDefault(NEXT,true);
    }

    @Override
    public Object run() throws ZuulException {
        context = RequestContext.getCurrentContext();

        return cRun();
    }

    protected abstract Object cRun();

    Object fail(int code,String msg){
        context.set(NEXT,false);
        context.setSendZuulResponse(false);
        context.getResponse().setContentType("text/html;charset=UTF-8");
        context.setResponseStatusCode(code);
        context.setResponseBody(String.format("{\"result\": \"%s!\"}",msg));
        return null;
    }

    Object success(){
        context.set(NEXT,true);
        return null;
    }
}
