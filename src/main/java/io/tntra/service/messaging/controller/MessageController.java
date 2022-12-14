package io.tntra.service.messaging.controller;

import io.tntra.service.messaging.model.Message;
import io.tntra.service.messaging.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController implements MessageControllerAPI{

    @Autowired
    MessageService messageService;

    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }


    public List<Message> getMessagesForUser(@PathVariable Long userId){
      return messageService.getMessagesForUser(userId);
    }

    public List<Message> getUserMessagesForTimePeriod(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime fromDateTime,
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime toDateTime
            ){
        return messageService.getUserMessagesForTimePeriod(userId,fromDateTime,toDateTime);
    }

    public List<Message> getMessagesForTimePeriod(
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime fromDateTime,
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime toDateTime
    ){
        return messageService.getMessagesForTimePeriod(Optional.empty(),fromDateTime,toDateTime);
    }

    public ResponseEntity<Message> createMessage(@Validated @RequestBody Message message){
            Message savedMessage = messageService.saveMessage(message);
            return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    public ResponseEntity<Message> updateMessage(@Validated @RequestBody Message message)  {
        Message savedMessage = messageService.updateMessage(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.OK);
    }

}
