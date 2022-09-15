package io.tntra.service.messaging.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.tntra.service.messaging.model.Message;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@OpenAPIDefinition(info = @Info(title = "Messaging API", version = "1.0", description = "Messages Information"))
public interface MessageControllerAPI {
    @GetMapping("/getAllMessages")
    List<Message> getAllMessages();
    @GetMapping("/getUserMessage/{userId}")
    List<Message> getMessagesForUser(@PathVariable Long userId);
    @GetMapping("/getUserMessagesForTimePeriod/userId/{userId}/from/{fromDateTime}/to/{toDateTime}")
    List<Message> getUserMessagesForTimePeriod(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime fromDateTime,
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime toDateTime
    );
    @GetMapping("/getMessagesForTimePeriod/from/{fromDateTime}/to/{toDateTime}")
    List<Message> getMessagesForTimePeriod(
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime fromDateTime,
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime toDateTime
    );
    @PostMapping("/createMessage")
    ResponseEntity<Message> createMessage(@Validated @RequestBody Message message);
    @PutMapping("/updateMessage")
    ResponseEntity<Message> updateMessage(@Validated @RequestBody Message message);
}
