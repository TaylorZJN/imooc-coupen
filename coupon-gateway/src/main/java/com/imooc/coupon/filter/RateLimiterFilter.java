package com.imooc.coupon.filter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>限流过滤器</h1>
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class RateLimiterFilter extends AbstractPreZuulFilter {

    /**
     * 通过 令牌统算法 实现：一秒内在令牌桶取令牌 ，取不到令牌代表当前的流量 已经满了，需要做限流
     * RateLimiter.create(2.0) ：限流器： 每秒可以获取到2个令牌。
     */
    RateLimiter rateLimiter = RateLimiter.create(2.0);

    @Override
    protected Object cRun() {
        HttpServletRequest request =context.getRequest();
        if (rateLimiter.tryAcquire())
        {
            log.info("get rate token success!");
            return success();
        }else {
            log.error("rate limit : {}",request.getRequestURL());
            return fail(402,"error: rate limit!");
        }
    }

    @Override
    public int filterOrder() {
        return 2;
    }
}
