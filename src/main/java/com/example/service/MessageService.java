package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
	
	private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Message> createMessage(Message message){
        if (message.getMessageText().trim().length() == 0 || message.getMessageText().length() >= 255) {
            
            return ResponseEntity.status(400).body(null);
        }

        Optional<Account> accountOptional = accountRepository.findById(message.getPostedBy());

        if(accountOptional.isPresent()){
            
            Message savedMessage = messageRepository.save(message);
            return ResponseEntity.status(200).body(savedMessage);
        }else{
              
            return ResponseEntity.status(400).body(null);
        }
    }

}
