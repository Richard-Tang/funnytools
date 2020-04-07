package com.funnysec.richardtang.funnytools.annotation;

import com.funnysec.richardtang.funnytools.validator.IpOrDomainValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验IP或者域名两种格式
 *
 * @author RichardTang
 * @date 2020年3月16日14:25:49
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = IpOrDomainValidator.class)
public @interface IpOrDomain {

    String message() default "仅支持IP或者域名的格式";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        IpOrDomain[] value();
    }
}
