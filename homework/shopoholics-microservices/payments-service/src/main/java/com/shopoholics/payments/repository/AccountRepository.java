package com.shopoholics.payments.repository;

import com.shopoholics.payments.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByUserId(Long userId);
    
    @Query("SELECT a FROM Account a WHERE a.userId = :userId")
    Optional<Account> findAccountByUserId(@Param("userId") Long userId);
}