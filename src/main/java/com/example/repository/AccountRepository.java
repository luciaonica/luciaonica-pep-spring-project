package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

    /**
     * Retrieve an account by its username
     * @param username
     * @return
     */
    Account findByUsername(String username);
}
