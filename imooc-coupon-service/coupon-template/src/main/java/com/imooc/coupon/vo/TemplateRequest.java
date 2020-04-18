package com.imooc.coupon.vo;

import com.imooc.coupon.constant.CouponCategory;
import com.imooc.coupon.constant.DistributeTarget;
import com.imooc.coupon.constant.ProductLine;
import com.imooc.coupon.converter.CouponCategoryConverter;
import com.imooc.coupon.converter.DistributeTargetConverter;
import com.imooc.coupon.converter.ProductLineConverter;
import com.imooc.coupon.converter.TemplateRuleConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import java.util.Date;

/**
 * 优惠券模板创建请求对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateRequest {

    /**
     * 优惠券名字
     */
    private String name;

    /**
     * 优惠券logo
     */
    private String logo;

    /**
     * 优惠券描述
     */
    private String desc;

    /**
     * 优惠券分类
     */
    private String category;

    /**
     * 产品线
     */
    private Integer productLine;

    /**
     * 总数
     */
    private Integer count;

    /**
     *用户ID
     */
    private Long userId;

    /**
     *模板用户
     */
    private Integer target;
    /**
     *优惠券规则
     */
    private TemplateRule rule;

    /**
     * 校验对象的合法性
     * @return
     */
    public boolean validata(){
        boolean stringValid = StringUtils.isNotEmpty(name)
                && StringUtils.isNotEmpty(logo)
                && StringUtils.isNotEmpty(desc);

        boolean enumVaild =  null != CouponCategory.of(category)
                && null != ProductLine.of(productLine)
                && null != DistributeTarget.of(target);

        boolean numVaild = count >0 && userId >0 ;

        return stringValid && enumVaild && numVaild && rule.validata();
    }
}
