package service;

import io.tntra.service.messaging.model.Message;
import io.tntra.service.messaging.repository.MessageRepository;
import io.tntra.service.messaging.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static TestUtils.MockDataProvider.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {


    @Mock
    MessageRepository messageRepository;

    @InjectMocks
    MessageService messageService;

    @Test
    public void getAllMessagesTest(){
        when(messageRepository.findAll()).thenReturn(getDummyMessages());
        List<Message> myMessages = messageService.getAllMessages();
        assertEquals(myMessages.size(),4,"Not Received All the Messages");
    }
}
