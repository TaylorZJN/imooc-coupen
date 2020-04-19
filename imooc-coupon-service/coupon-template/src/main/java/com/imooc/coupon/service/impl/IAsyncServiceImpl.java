package com.imooc.coupon.service.impl;

import com.google.common.base.Stopwatch;
import com.imooc.coupon.constant.Constant;
import com.imooc.coupon.dao.CouponTemplateDao;
import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 异步服务接口实现
 */
@Slf4j
@Service
public class IAsyncServiceImpl implements IAsyncService {

    @Autowired
    private CouponTemplateDao templateDao;

    /**
     * 注入redis 模板类
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    @Async("getAsyncExecutor")
    public void asyncConstructCouponByTemplate(CouponTemplate template) {
      Stopwatch stopwatch = Stopwatch.createStarted();
      Set<String> couponCodes = buildCouponCode(template);

      //imooc_coupon_template_code_1 （id）
      String redisKey = String.format("%s%s",
              Constant.RedisPrefix.COUPON_TEMPLATE,template.getId().toString());
      log.info("Push CouponCode To Reids:{}",
              redisTemplate.opsForList().rightPushAll(redisKey,couponCodes));
      template.setAvailable(true);
      templateDao.save(template);
      stopwatch.stop();
      log.info("Construct CouponCode By Template Cost: {}ms",
              stopwatch.elapsed(TimeUnit.MILLISECONDS));
      //TODO 发生短信或邮件通知运维人员等 优惠券模板已经可用
        log.info("CoupenTemplate({}) Id Available!",template.getId());

    }

    /**
     * 构建优惠券码
     * 对于优惠券（18位）
     * 前4位：产品线 + 类型
     * 中间6位：日期随机（190101）
     * 后8位：0 - 9 随机数
     * @param template {@link CouponTemplate} 实体类
     * @return Set<String>  与 template.count 相同个数的优惠券码
     */
    @SuppressWarnings("all")
    private Set<String> buildCouponCode(CouponTemplate template){
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<String> result = new HashSet<>(template.getCount());

        //前4位
        String prefix4 = template.getProductLine().getCode().toString()
                + template.getCategory().getCode();

        //中间6位 + 后8 位
        String date = new SimpleDateFormat("yyMMdd").format(template.getCreateTime()) ;
        for (int i = 0; i != template.getCount(); ++i) {
            result.add(prefix4 + buildCouponCodeSuffix14(date));
        }
        while (result.size() < template.getCount()){
            result.add(prefix4 + buildCouponCodeSuffix14(date));
        }
        assert  result.size() == template.getCount();
        stopwatch.stop();
        log.info("Build Coupon code Cost: {}ms",stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return result;
    }

    /**
     * 构造优惠券码的后14位
     * @param date 创建优惠券的日期
     * @return
     */
    private String buildCouponCodeSuffix14(String date){
        char[] bases = new char[]{'1','2','3','4','5','6','7','8','9'};
        //中间6位
        List<Character> chars = date.chars()
                .mapToObj(e -> (char)e).collect(Collectors.toList());
        Collections.shuffle(chars);
        String mid6 =  chars.stream()
                .map(Objects::toString).collect(Collectors.joining());
        //后8位
        String suffix8 = RandomStringUtils.random(1,bases)
                + RandomStringUtils.randomNumeric(7);
        return mid6 + suffix8 ;
    }
}
