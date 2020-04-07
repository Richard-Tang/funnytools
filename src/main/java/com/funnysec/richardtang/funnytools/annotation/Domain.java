package com.funnysec.richardtang.funnytools.annotation;

import com.funnysec.richardtang.funnytools.validator.DomainValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验域名注解
 *
 * @author RichardTang
 * @date 2020年3月15日20:39:56
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = DomainValidator.class)
public @interface Domain {

    /**
     * 出现错误返回的信息
     */
    String message() default "域名格式不正确";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Domain[] value();
    }
}
