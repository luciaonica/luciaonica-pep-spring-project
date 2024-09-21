package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	
	/**
	 * Handler method to create a new account
	 * @param account
	 * @return the newly created account if the operation was successful or HttpStatusCode 400 or 409
	 */
	@PostMapping("/register")
	public ResponseEntity<Account> saveAccount(@RequestBody Account account) {
		try {
			return new ResponseEntity<>(accountService.persistAccount(account), HttpStatus.OK);
			
		} catch(AccountAlreadyExistsException e ) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (InvalidCredentialsException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

	/**
	 * Handler method to login user
	 * @param account
	 * @return the account of logged in user if login was successful or HttpStatusCode 401
	 */
	@PostMapping("/login")
	public ResponseEntity<Account> loginUser(@RequestBody Account account) {
		try {
			return new ResponseEntity<>(accountService.loginUser(account), HttpStatus.OK);
		} catch(InvalidCredentialsException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
	}

	/**
	 * Handler method to post a new message
	 * @param message
	 * @return the newly created message if the operation was successful or HttpStatusCode 400
	 */
	@PostMapping("/messages")
	public ResponseEntity<Message> createMessage(@RequestBody Message message) {
		
		try {
			return new ResponseEntity<>(messageService.createMessage(message), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}

	/**
	 * Handler method to retrieve all messages
	 * @return A list of messages or an empty list
	 */
	@GetMapping("/messages")
	public List<Message> getAllMessages() {
		
		return messageService.getAllMessages();		
	}

	/**
	 * Handler method to retrieve a message with a given id.
	 * @param message_id
	 * @return message with the given id. If no message exists with this id return empty body.
	 */
	@GetMapping("/messages/{message_id}")
	public ResponseEntity<Message> getMessageById(@PathVariable String message_id) {
		
		try {
			return new ResponseEntity<>(messageService.getMessageById(Integer.parseInt(message_id)), HttpStatus.OK);
		} catch(MessageNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.OK);
		}		
	}

	/**
	 * Handler method to delete a message with given id.
	 * @param message_id
	 * @return 1 if the message was deleted. If no message exists with this id return empty body.
	 */
	@DeleteMapping("/messages/{message_id}")
	public ResponseEntity<Integer> deleteMessageById(@PathVariable String message_id) {
		
		try {
			return new ResponseEntity<>(messageService.deleteMessageById(Integer.parseInt(message_id)), HttpStatus.OK);
		} catch(MessageNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.OK);
		}	
	}

	/**
	 * Handler method to retrieve all messages posted by user with given account id
	 * @param account_id
	 * @return A list of messages
	 */
	@GetMapping("/accounts/{account_id}/messages")
	public List<Message> getAllMessagesByAccountId(@PathVariable String account_id) {
		
		return messageService.getAllMessagesByAccountId(Integer.parseInt(account_id));		
	}

	/**
	 * Handler method to update the message text by given id
	 * @param message_id
	 * @param message
	 * @return 1 if the update was successful or HttpStatusCode 400
	 */
	@PatchMapping("/messages/{message_id}")
	public ResponseEntity<Integer> updateMessageById(@PathVariable String message_id, @RequestBody Message message) {
		
		try {
			return new ResponseEntity<>(messageService.updateMessage(Integer.parseInt(message_id), message), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
	}

}
