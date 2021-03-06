package com.imooc.coupon.converter;

import com.alibaba.fastjson.JSON;
import com.imooc.coupon.vo.TemplateRule;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

/**
 * 优惠券规则属性转换器
 */
@Convert
public class TemplateRuleConverter implements AttributeConverter<TemplateRule,String> {

    @Override
    public String convertToDatabaseColumn(TemplateRule rule) {
        return JSON.toJSONString(rule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String s) {
        return JSON.parseObject(s,TemplateRule.class);
    }
}
