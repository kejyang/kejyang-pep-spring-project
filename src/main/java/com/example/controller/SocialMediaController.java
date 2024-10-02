package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    // 1. Register a new account
    @PostMapping("/register")
    public ResponseEntity registerAccount(@RequestBody Account account) {
        try {
            Account newAccount = accountService.register(account);
            return ResponseEntity.status(200).body(newAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

        // 2. Login an account
        @PostMapping("/login")
        public ResponseEntity loginAccount(@RequestBody Account account) {
            Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());
            if (loggedInAccount != null) {
                return ResponseEntity.ok(loggedInAccount);
            }
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        // 3. Post a new message
        @PostMapping("/messages")
        public ResponseEntity createMessage(@RequestBody Message message) {
            try {
                Message newMessage = messageService.createMessage(message);
                return ResponseEntity.ok(newMessage);
            } catch (Exception e) {
                return ResponseEntity.status(400).body(e.getMessage());
            }
        }

        // 4. Get all messages
        @GetMapping("/messages")
        public ResponseEntity getAllMessages() {
            return ResponseEntity.status(200).body(messageService.getAllMessages());
        }

        // 5. Get message by ID
        @GetMapping("/messages/{messageId}")
        public ResponseEntity getMessageById(@PathVariable int messageId) {
            Message message = messageService.getMessageById(messageId);
            if(message!=null){
                return ResponseEntity.status(200).body(message);
            }
            return ResponseEntity.status(200).build();
        }

        // 6. Delete a message by ID
        @DeleteMapping("/messages/{messageId}")
        public ResponseEntity deleteMessage(@PathVariable int messageId) {
            int rowsDeleted = messageService.deleteMessage(messageId);
            if(rowsDeleted == 1){
                return ResponseEntity.status(200).body(rowsDeleted);
            }
            return ResponseEntity.status(200).build();
        }

        // 7. Update a message by ID
        @PatchMapping("/messages/{messageId}")
        public ResponseEntity updateMessage(@PathVariable int messageId, @RequestBody Message messageText) {
            int rowsUpdated = messageService.updateMessage(messageId, messageText.getMessageText());
            if(rowsUpdated == 0){
                return ResponseEntity.status(400).build();
            }
            return ResponseEntity.status(200).body(rowsUpdated);
        }

        // 8. Get all messages by a specific user (by accountId)
        @GetMapping("/accounts/{accountId}/messages")
        public ResponseEntity getMessagesByUser(@PathVariable int accountId) {
            return ResponseEntity.status(200).body(messageService.getMessagesByUser(accountId));
        }

}
