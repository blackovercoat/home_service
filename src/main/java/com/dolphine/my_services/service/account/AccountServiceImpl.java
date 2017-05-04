package com.dolphine.my_services.service.account;

import com.dolphine.my_services.model.AccountEntity;
import com.dolphine.my_services.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by PC on 3/14/2017.
 */
@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Transactional
    @Override
    public AccountEntity getAccountByEmail(String mail) {
        AccountEntity account = accountRepository.findByEmail(mail);
        if(account==null)
            return null;
        else{
            account.getRoles().size();
            return account;
        }
    }
}
