package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.InvalidCredentialsException;
import com.example.exception.MessageNotFoundException;
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

	@GetMapping("/messages")
	public List<Message> getAllMessages() {
		
		return messageService.getAllMessages();		
	}

	@GetMapping("/messages/{message_id}")
	public ResponseEntity<Message> getMessageById(@PathVariable String message_id) {
		
		try {
			return new ResponseEntity<>(messageService.getMessageById(Integer.parseInt(message_id)), HttpStatus.OK);
		} catch(MessageNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.OK);
		}		
	}

	@DeleteMapping("/messages/{message_id}")
	public ResponseEntity<Integer> deleteMessageById(@PathVariable String message_id) {
		
		try {
			return new ResponseEntity<>(messageService.deleteMessageById(Integer.parseInt(message_id)), HttpStatus.OK);
		} catch(MessageNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.OK);
		}	
	}

	@GetMapping("/accounts/{account_id}/messages")
	public List<Message> getAllMessagesByAccountId(@PathVariable String account_id) {
		
		return messageService.getAllMessagesByAccountId(Integer.parseInt(account_id));		
	}

}
