package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;
	
	  @Autowired
	    public AccountService(AccountRepository accountRepository){
	        this.accountRepository = accountRepository;
	    }
	  
	  public ResponseEntity<Account> persistAccount(Account account){
		  
		  if ((account.getPassword().length() < 4) || (account.getUsername().trim().length() == 0)) {
			  return ResponseEntity.status(400).body(null);
		  }
		  
		  
		  
		  Account account1 = accountRepository.findByUsername(account.getUsername());
		  
		  if (account1 != null) {
			  return ResponseEntity.status(409).body(null);
		  }
		  
		  Account acc = accountRepository.save(account);
		  
	        return ResponseEntity.status(200).body(acc);
	    }

		public ResponseEntity<Account> loginUser(Account account){
			Account accountFromDB = accountRepository.findByUsername(account.getUsername());
			
			if (accountFromDB != null && account.getPassword().equals(accountFromDB.getPassword()) ) {
				return new ResponseEntity<>(accountFromDB, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

}
