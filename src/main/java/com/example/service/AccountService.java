package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.InvalidCredentialsException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;
	
	  @Autowired
	    public AccountService(AccountRepository accountRepository){
	        this.accountRepository = accountRepository;
	    }
	  
	  public Account persistAccount(Account account) throws InvalidCredentialsException, AccountAlreadyExistsException{
		  
		  if ((account.getPassword().length() < 4) || (account.getUsername().trim().length() == 0)) {
			throw new InvalidCredentialsException("Invalid credentials");
		  }	  
		  
		  Account accountFromDB = accountRepository.findByUsername(account.getUsername());
		  
		  if (accountFromDB != null) {
			throw new AccountAlreadyExistsException("Account already exists with this username");
		  }
		  
		  return accountRepository.save(account);	  
	        
	    }

		public Account loginUser(Account account) throws InvalidCredentialsException{
			Account accountFromDB = accountRepository.findByUsername(account.getUsername());
		  
		  if (accountFromDB != null && account.getPassword().equals(accountFromDB.getPassword()) ) {
			  return accountFromDB;
		  } else {
			  throw new InvalidCredentialsException("invalid credentials");
		  }
		}

}
