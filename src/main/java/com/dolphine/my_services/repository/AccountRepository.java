package com.dolphine.my_services.repository;

import com.dolphine.my_services.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PC on 3/12/2017.
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Integer>{
    AccountEntity findByEmail(String email);
}
