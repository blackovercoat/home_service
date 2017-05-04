package com.dolphine.my_services.validation.validator;

import com.dolphine.my_services.dto.ProviderForm;
import com.dolphine.my_services.service.provider.ProviderService;
import com.dolphine.my_services.validation.annotation.ValidProvider;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by PC on 4/10/2017.
 */
public class ProviderValidator implements ConstraintValidator<ValidProvider,ProviderForm> {

    @Autowired
    final private ProviderService providerService;

    public ProviderValidator(ProviderService providerService) {
        this.providerService = providerService;
    }

    @Override
    public void initialize(ValidProvider constraintAnnotation) {

    }

    @Override
    public boolean isValid(ProviderForm providerForm, ConstraintValidatorContext context) {
        if(providerForm == null)
            return false;
        String password = providerForm.getPassword();
        String repassword = providerForm.getRepassword();
        String email = providerForm.getEmail();
        String phoneNumber = providerForm.getPhoneNumber();
        int id = providerForm.getId();

        if(providerService.getProviderByEmail(email)!=null&&id==0)
            return false;

        if(providerService.getProviderByPhoneNumber(phoneNumber)!=null&&id==0)
            return false;

        if(!password.equals(repassword))
            return false;

        return true;
    }
}
