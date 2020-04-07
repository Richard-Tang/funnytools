package com.funnysec.richardtang.funnytools.validator;

import cn.hutool.core.util.StrUtil;
import com.funnysec.richardtang.funnytools.annotation.Phone;
import com.funnysec.richardtang.funnytools.constant.Regular;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号格式校验逻辑
 *
 * @author RichardTang
 * @date 2020年3月15日22:31:54
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isEmpty(value)) {
            return true;
        }
        return value.matches(Regular.PHONE_PATTERN.toString());
    }
}