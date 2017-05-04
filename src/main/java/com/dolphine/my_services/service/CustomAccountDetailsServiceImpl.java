package com.dolphine.my_services.service;


import com.dolphine.my_services.model.AccountEntity;
import com.dolphine.my_services.model.RoleEntity;
import com.dolphine.my_services.repository.AccountRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("customAccountDetailsService")
public class CustomAccountDetailsServiceImpl implements CustomAccountDetailsService {
    private final AccountRepository accountRepository;

    public CustomAccountDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity acc = accountRepository.findByEmail(username);
        if (null == acc) {
            throw new UsernameNotFoundException("No User present with this email:" + username);
        } else {
            List<String> accRoles = new ArrayList<>();
            for (RoleEntity re : acc.getRoles()) {
                accRoles.add(re.getName());
            }
            return new CustomAccountDetail(acc, accRoles);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public AccountEntity getCurrentUser() {
        CustomAccountDetail customAccountDetail = (CustomAccountDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountEntity currentAccount = accountRepository.findByEmail(customAccountDetail.getUsername());
        return currentAccount;
    }
}
