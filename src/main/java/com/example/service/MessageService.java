package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    // Create a new message
    public Message createMessage(Message message) throws Exception {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new Exception("Invalid message content");
        }
        return messageRepository.save(message);
    }

    // Get all messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Get message by ID
    public Message getMessageById(int messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isEmpty()){
            return null;
        }
        return message.get();  
    }

    // Delete message by ID
    public int deleteMessage(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    // Update message by ID
    public int updateMessage(int messageId, String newMessageText) {
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (newMessageText.isEmpty() || newMessageText==" " || newMessageText.length() > 255) {
            return 0;
        }
        Message message = existingMessage.get();
        message.setMessageText(newMessageText);
        messageRepository.save(message);
        return 1;
    }

    // Get messages by user ID
    public List<Message> getMessagesByUser(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
