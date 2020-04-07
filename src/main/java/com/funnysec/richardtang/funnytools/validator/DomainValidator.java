package com.funnysec.richardtang.funnytools.validator;

import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.annotation.Domain;
import com.funnysec.richardtang.funnytools.constant.Regular;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 域名格式校验逻辑
 *
 * @author RichardTang
 * @date 2020年3月16日14:23:30
 */
public class DomainValidator implements ConstraintValidator<Domain, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isEmpty(value)) {
            return true;
        }
        return value.matches(Regular.DOMAIN_PATTERN.toString());
    }
}