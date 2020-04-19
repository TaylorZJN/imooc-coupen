package com.imooc.coupon.constant;

/**
 *
 * 通用常量定义
 */
public class Constant {

    /**
     * kafka 消息的 Topic
     */
    public static final String TOPIC = "imooc_user_coupon_op";

    /**
     * Redis Key 前缀定义
     */
    public static class RedisPrefix{

        public static final String COUPON_TEMPLATE= "imooc_coupon_template_code_";
        /**
         * 用户当前所有可用的优惠券key前缀
         */
        public static final String  USER_COUPON_USABLE = "imooc_user_coupon_usable_";
        /**
         * 用户当前所有已使用的优惠券 key 前缀
         */
        public static final String  USER_COUPON_USED = "imooc_user_coupon_used_";

        /**
         * 用户当前所有已过期的优惠券 key 前缀
         */
        public static final String  USER_COUPON_EXPIRED = "imooc_user_coupon_expired_";

    }


}
