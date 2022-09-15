package controller;

import io.tntra.service.messaging.controller.MessageController;
import io.tntra.service.messaging.model.Message;
import io.tntra.service.messaging.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static TestUtils.MockDataProvider.*;

@ExtendWith(MockitoExtension.class)
public class MessageControllerTest {

    @Mock
    MessageService messageService;

    @InjectMocks
    MessageController messageController;

    @Test
    public void getAllMessagesTest(){
        when(messageService.getAllMessages()).thenReturn(getDummyMessages());
        List<Message> messageList = messageController.getAllMessages();
        Message message = messageList.get(0);
        assertEquals(messageList.size(),4,"Missing Messages from List");
        assertEquals(message.getId(),2000L,"Message ID Not Matching");
        assertEquals(message.getUserId(),1L,"Message UserId Not Matching");
    }
    @Test
    public void getMessagesForUserTest(){
        when(messageService.getMessagesForUser(1L)).thenReturn(getDummyMessagesWithUserId(1L));
        Message myMessage = new Message(2000L,1L,"Sample title 2000","Sample Message 2000", LocalDateTime.now(),LocalDateTime.now());
        messageService.getMessagesForUser(1L).forEach(message -> {
                assertEquals(message.getUserId(),1L,"UserId Not matching for Message");
        });
    }
    @Test
    public void getMessagesForTimePeriod(){
            when(messageService.getMessagesForTimePeriod(Optional.empty(),
                    LocalDateTime.of(2022,01,01,14,20,30),
                    LocalDateTime.of(2022,01,01,18,20,30)))
                    .thenReturn(getDummyMessagesWithTimePeriod(LocalDateTime.of(2022,01,01,15,20,30),
                            LocalDateTime.of(2022,01,01,17,20,30)));
            List<Message> timeFilteredMessages = messageService.getMessagesForTimePeriod(Optional.empty(),
                    LocalDateTime.of(2022,01,01,14,20,30),
                    LocalDateTime.of(2022,01,01,18,20,30));
            timeFilteredMessages.forEach(message -> {
                            assertTrue(message.getCreatedDateTime().isAfter(LocalDateTime.of(2022,01,01,14,20,30)),"Message is Outside of Specific Time Period");
                            assertTrue(message.getCreatedDateTime().isBefore(LocalDateTime.of(2022,01,01,18,20,30)),"Message is Outside of Specific Time Period");
                    }
            );
    }

    @Test
    public void getUserMessagesForTimePeriodTest(){
        when(messageService.getUserMessagesForTimePeriod(1L,
                LocalDateTime.of(2022,01,01,14,20,30),
                LocalDateTime.of(2022,01,01,18,20,30)))
                .thenReturn(getDummyMessagesWithTimePeriodAndUserId(1L,
                        LocalDateTime.of(2022,01,01,17,50,30)));
        List<Message> messageList = messageService.getUserMessagesForTimePeriod(1L,
                LocalDateTime.of(2022,01,01,14,20,30),
                LocalDateTime.of(2022,01,01,18,20,30));
        messageList.forEach(message -> {
            assertEquals(message.getUserId(),1L,"User ID Not Matching");
            assertTrue(message.getCreatedDateTime().isAfter(LocalDateTime.of(2022,01,01,14,20,30)),"Message is Outside of Specific Time Period");
            assertTrue(message.getCreatedDateTime().isBefore(LocalDateTime.of(2022,01,01,18,20,30)),"Message is Outside of Specific Time Period");
        });
    }

}
