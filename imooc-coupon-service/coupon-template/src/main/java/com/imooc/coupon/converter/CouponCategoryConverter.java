package com.imooc.coupon.converter;

import com.imooc.coupon.constant.CouponCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

/**
 * 优惠券分类枚举属性转换器
 * AttributeConverter<X,Y>
 *     X:是实体属性的类型
 *     Y: 数据库对应字段的类型
 */
@Convert
public class CouponCategoryConverter implements AttributeConverter<CouponCategory,String> {

    /**
     * 把实体属性X转换为数据库的Y，存储到数据库中
     * @param couponCategory
     * @return
     */
    @Override
    public String convertToDatabaseColumn(CouponCategory couponCategory) {
        return couponCategory.getCode();
    }

    /**
     * 将数据库的字段Y 转换为实体属性X
     * 查询操作时执行的动作
     * @param s
     * @return
     */
    @Override
    public CouponCategory convertToEntityAttribute(String code) {

        return CouponCategory.of(code);
    }
}
