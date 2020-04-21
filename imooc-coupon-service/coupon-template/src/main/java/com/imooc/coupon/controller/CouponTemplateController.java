package com.imooc.coupon.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.exception.CouponException;
import com.imooc.coupon.service.IBuildTemplateService;
import com.imooc.coupon.service.ITemplateBaseService;
import com.imooc.coupon.vo.CouponTemplateSDK;
import com.imooc.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 优惠券模板相关的功能控制器
 */
@Slf4j
@RestController
public class CouponTemplateController {

    /**
     * 构建优惠券模板服务
     */
    private final IBuildTemplateService buildTemplateService;

    /**
     * 优惠券模板基础服务
     */
    private final ITemplateBaseService templateBaseService;
    public CouponTemplateController(IBuildTemplateService buildTemplateService, ITemplateBaseService templateBaseService) {
        this.buildTemplateService = buildTemplateService;
        this.templateBaseService = templateBaseService;
    }

    /**
     * 构建优惠券模板
     * 127.0.0.1:7001/coupon-template/template/build
     *
     * 127.0.0.1:9000/imooc/coupon-template/template/build
     * @param request
     * @return
     * @throws CouponException
     */
    @PatchMapping("/template/build")
    public CouponTemplate buildTemplate(TemplateRequest request) throws CouponException{
        log.info("Build Template:{}", JSON.toJSONString(request));
        return buildTemplateService.buildTemplate(request);
    }

    /**
     * 构造优惠券模板详情
     *      * 127.0.0.1/coupon-template/template/info/?id=1
     * @param id
     * @return
     * @throws CouponException
     */
    @PostMapping("/template/info")
    public CouponTemplate buildTemplateInfo(@RequestParam("id") Integer id) throws CouponException{
        log.info("build template info for:{}",id);
        return templateBaseService.buildTemplateInfo(id);
    }

    /**
     * 查找所有可用的优惠券模板
     *      * 127.0.0.1/coupon-template/template/sdk/all
     * @return
     */
    @GetMapping("/template/sdk/all")
    public List<CouponTemplateSDK> findAllUsableTemplate(){
        log.info("Find All Usable Template!");
        return templateBaseService.findAllUsableTemplate();
    }

    /**
     * 获取模板IDS到coupontemplateSDK的映射
     *  127.0.0.1/coupon-template/template/sdk/infos
     * @param ids
     * @return
     * @throws CouponException
     */
    @GetMapping("/template/sdk/infos")
    public Map<Integer,CouponTemplateSDK>  findIdsTemplateSDK(@RequestParam("ids") Collection<Integer> ids) throws CouponException{
        log.info("FindIds2TemplateSDK:{}",JSON.toJSONString(ids));

        return templateBaseService.findIds2TemplateSDK(ids);

    }




}