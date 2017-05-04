package com.dolphine.my_services.service.account;

import com.dolphine.my_services.model.AccountEntity;

/**
 * Created by PC on 3/14/2017.
 */
public interface AccountService {

    AccountEntity getAccountByEmail(String email);
}
