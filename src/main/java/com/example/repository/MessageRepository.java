package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

    /**
     * Retrieve messages posted by user with given ID
     * @param postedBy
     * @return list of messages
     */
    List<Message> findByPostedBy(int postedBy);
}
