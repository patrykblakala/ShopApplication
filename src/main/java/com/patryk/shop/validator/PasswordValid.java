package com.patryk.shop.validator;

import com.patryk.shop.validator.impl.PasswordValidImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidImpl.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {
    String message() default "Password does not match our records";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
