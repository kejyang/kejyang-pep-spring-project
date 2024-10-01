package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.entity.Message;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    
    Optional<Account> findByUsername(String username);

}
