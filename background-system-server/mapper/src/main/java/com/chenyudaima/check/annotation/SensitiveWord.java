package com.chenyudaima.check.annotation;


import com.chenyudaima.check.validation.SensitiveWordValidation;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//注解生命周期
@Retention(RUNTIME)

//注解可以打在哪个位置
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})

//表明标记的注解可以多次应用于相同的声明或类型
@Repeatable(SensitiveWord.IsLeeks.class)

@Documented

//标明由哪个类执行校验逻辑
@Constraint(validatedBy = SensitiveWordValidation.class)

/**
 * 敏感词校验注解
 */
public @interface SensitiveWord {

    /**
     * 默认敏感词
     */
    String[] value() default {
        "爸","妈","爷","傻"
    };

    /**
     * 校验不通过时的报错信息
     */
    String message() default "非法昵称!";

    /**
     * 将validator进行分类，不同的类group中会执行不同的validator操作
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface IsLeeks {
        SensitiveWord[] value();
    }

}
