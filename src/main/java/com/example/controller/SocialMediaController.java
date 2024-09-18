package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.InvalidCredentialsException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    private AccountService accountService;

	private MessageService messageService;

	@Autowired
	public SocialMediaController(AccountService accountService, MessageService messageService) {
		this.accountService = accountService;
		this.messageService = messageService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<Account> saveAccount(@RequestBody Account account) {
		try {
			return new ResponseEntity<>(accountService.persistAccount(account), HttpStatus.OK);
			
		} catch(AccountAlreadyExistsException | InvalidCredentialsException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
	}

	@PostMapping("/login")
	public ResponseEntity<Account> loginUser(@RequestBody Account account) {
		try {
			return new ResponseEntity<>(accountService.loginUser(account), HttpStatus.OK);
		} catch(InvalidCredentialsException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
	}

	@PostMapping("/messages")
	public ResponseEntity<Message> createMessage(@RequestBody Message message) {
		
		try {
			return new ResponseEntity<>(messageService.createMessage(message), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}

}
