package com.chenyudaima.validation.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * SensitiveWord注解的校验逻辑
 */
public class SensitiveWordValidation implements ConstraintValidator<SensitiveWord, String> {

    String[] strings;

    /**
     * 拿到注解的内容
     */
    @Override
    public void initialize(SensitiveWord constraintAnnotation) {
        strings = constraintAnnotation.value();
    }

    /**
     * 验证参数是否合法
     * @param param 需要验证的参数
     * @param constraintValidatorContext context in which the constraint is evaluated
     * @return true为合法,false为不合法并抛出异常
     */
    @Override
    public boolean isValid(String param, ConstraintValidatorContext constraintValidatorContext) {
        if(param == null) {
            return true;
        }

        for (String s : strings) {
            if(param.contains(s)) {
                return false;
            }
        }

        return true;
    }

}
