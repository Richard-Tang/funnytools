package com.funnysec.richardtang.funnytools.validator;

import cn.hutool.core.util.ObjectUtil;
import com.funnysec.richardtang.funnytools.annotation.IpOrDomain;
import com.funnysec.richardtang.funnytools.constant.Regular;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * IP或者域名格式校验逻辑
 *
 * @author RichardTang
 * @date 2020年3月16日14:26:43
 */
public class IpOrDomainValidator implements ConstraintValidator<IpOrDomain, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (ObjectUtil.isNull(value)) {
            return true;
        }

        // 处理单个字符类型
        if (value instanceof String) {
            String v = (String) value;
            return v.matches(Regular.DOMAIN_PATTERN.toString()) || v.matches(Regular.IP_PATTERN.toString());
        }

        // 处理一组
        if (value instanceof List) {
            List<String> v = (List<String>) value;
            for (String i : v) {
                if (i.matches(Regular.DOMAIN_PATTERN.toString()) || i.matches(Regular.IP_PATTERN.toString())) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }
}