package com.dolphine.my_services.validation.validator;

import com.dolphine.my_services.dto.ServiceForm;
import com.dolphine.my_services.validation.annotation.ValidService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by PC on 4/10/2017.
 */
public class ServiceValidator implements ConstraintValidator<ValidService,ServiceForm> {


    @Override
    public void initialize(ValidService constraintAnnotation) {

    }

    @Override
    public boolean isValid(ServiceForm serviceForm, ConstraintValidatorContext context) {
        if(serviceForm==null)
            return false;

        return true;
    }
}
