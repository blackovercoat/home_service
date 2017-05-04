package com.dolphine.my_services.validation.annotation;

import com.dolphine.my_services.validation.validator.ProviderValidator;
import com.dolphine.my_services.validation.validator.ServiceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by PC on 4/10/2017.
 */
@Documented
@Constraint(validatedBy = ServiceValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidService {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
