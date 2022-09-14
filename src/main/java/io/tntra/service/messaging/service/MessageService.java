package io.tntra.service.messaging.service;

import io.tntra.service.messaging.exceptions.CustomMessageException;
import io.tntra.service.messaging.model.Message;
import io.tntra.service.messaging.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MessageService {

    Logger logger = LoggerFactory.getLogger(MessageService.class);
    @Autowired
    MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        if (message.getCreatedDateTime() == null) {
            message.setCreatedDateTime(LocalDateTime.now());
        }
        if (message.getUpdatedDateTime() == null) {
            message.setUpdatedDateTime(LocalDateTime.now());
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        List<Message> messageList = new ArrayList<>();
        messageRepository.findAll().forEach(messageList::add);
        return messageList;
    }

    public List<Message> getMessagesForUser(Long userId) {
        List<Message> messageList = getAllMessages();
        return messageList.stream().filter(message -> message.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Message> getUserMessagesForTimePeriod(Long userId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        List<Message> messageList = getMessagesForUser(userId);
        return getMessagesForTimePeriod(Optional.of(messageList),fromDateTime,toDateTime);
    }
    public List<Message> getMessagesForTimePeriod(Optional<List<Message>> messageList,LocalDateTime fromDateTime, LocalDateTime toDateTime){
        if(messageList.isEmpty()){
            messageList = Optional.of(getAllMessages());
        }
        Predicate<Message> isAfterFromDateTime = message -> message.getCreatedDateTime().isAfter(fromDateTime);
        Predicate<Message> isBeforeToDateTime = message -> message.getCreatedDateTime().isBefore(toDateTime);
        return messageList.get().stream()
                .filter(isAfterFromDateTime)
                .filter(isBeforeToDateTime)
                .collect(Collectors.toList());
    }
    public Message updateMessage(Message message) throws NoSuchElementException {
        Optional<Message> existingMessage = messageRepository.findById(message.getId());
        logger.info("Found Existing Message with ID: " + message.getId());
        //logger.info("Found Existing Message with ID: " + existingMessage.get());
        if(existingMessage.isPresent()){
            Message updatedMessage = existingMessage.get();
            updatedMessage.setTitle(message.getTitle());
            updatedMessage.setBody(message.getBody());
            messageRepository.save(updatedMessage);
        }else {
            throw new NoSuchElementException(String.format("No Message Found with Id {%d}",message.getId()));
        }

        return messageRepository.findById(message.getId()).get();
    }
}
