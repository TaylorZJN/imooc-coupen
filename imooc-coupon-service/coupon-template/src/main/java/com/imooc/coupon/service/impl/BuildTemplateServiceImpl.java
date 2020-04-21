package com.imooc.coupon.service.impl;

import com.imooc.coupon.dao.CouponTemplateDao;
import com.imooc.coupon.entity.CouponTemplate;
import com.imooc.coupon.exception.CouponException;
import com.imooc.coupon.service.IAsyncService;
import com.imooc.coupon.service.IBuildTemplateService;
import com.imooc.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 构建优惠券模板实现
 */
@Slf4j
@Service
public class BuildTemplateServiceImpl implements IBuildTemplateService {
    /**
     * 异步服务
     */
    private final IAsyncService asyncService;
    /**
     * Coupente
     */
    private final CouponTemplateDao templateDao;

    public BuildTemplateServiceImpl(IAsyncService asyncService, CouponTemplateDao templateDao) {
        this.asyncService = asyncService;
        this.templateDao = templateDao;
    }

    /**
     * 创建优惠券模板
     *
     * @param request {@link TemplateRequest} 模板请求对象
     * @return
     * @throws CouponException
     */
    @Override
    public CouponTemplate buildTemplate(TemplateRequest request) throws CouponException {
        //参数合法性校验
        if (!request.validata()){
            throw  new CouponException("BuildTemplate Param Is Not Valid!");
        }
        //判断同名的优惠券模板是否存在
        if (null != templateDao.findByName(request.getName())){
            throw new CouponException("Exist Same Name Template!");
        }

        //构造 CouponTemplate 并保存到数据库中
        CouponTemplate template = requestToTemplate(request);
        template = templateDao.save(template);
        asyncService.asyncConstructCouponByTemplate(template);
        return template;
    }

    /**
     * TemplateRequest 转换为 CouponTemplate
     * @param request
     * @return
     */
    private CouponTemplate requestToTemplate(TemplateRequest request){

        return new CouponTemplate(
                request.getName(),
                request.getLogo(),
                request.getDesc(),
                request.getCategory(),
                request.getProductLine(),
                request.getCount(),
                request.getUserId(),
                request.getTarget(),
                request.getRule()
        );

    }
}
