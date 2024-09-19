package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountNotFoundException;
import com.example.exception.InvalidMessageFormatException;
import com.example.exception.MessageNotFoundException;
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

    public Message createMessage(Message message) throws InvalidMessageFormatException, AccountNotFoundException{
        if (message.getMessageText().trim().length() == 0 || message.getMessageText().length() >= 255) {
            
            throw new InvalidMessageFormatException("Invalid message format");
        }

        Optional<Account> accountOptional = accountRepository.findById(message.getPostedBy());

        if(accountOptional.isPresent()){
            
            return messageRepository.save(message);
        }else{
              
            throw new AccountNotFoundException("invalid message format");
        }
    }

    public List<Message> getAllMessages() {
			
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) throws MessageNotFoundException {
        
        Optional<Message> messageOptional = messageRepository.findById(id);
            
            if(messageOptional.isPresent()){
                
                return messageOptional.get();
            }else{
                throw new MessageNotFoundException("message not found");
            }
        
    }

}
