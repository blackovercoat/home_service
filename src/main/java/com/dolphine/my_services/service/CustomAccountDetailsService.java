package com.dolphine.my_services.service;


import com.dolphine.my_services.model.AccountEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomAccountDetailsService extends UserDetailsService {
    AccountEntity getCurrentUser();
}
