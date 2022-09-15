package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.tntra.service.messaging.MessagingApplication;
import io.tntra.service.messaging.controller.MessageController;
import io.tntra.service.messaging.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static TestUtils.MockDataProvider.*;

@SpringBootTest(classes = MessagingApplication.class)
@AutoConfigureMockMvc
public class MessageControllerHttpTest {
    @Autowired
    private MessageController messageController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MessageService messageService;

    @Test
    public void controllerNotNullTest() throws Exception {
        assertThat(messageController).isNotNull();
    }


    @Test
    public void getAllMessagesTest() throws Exception {
        String arrayToJson = getArrayToJson();

        when(messageService.getAllMessages()).thenReturn(getDummyMessages());
        this.mockMvc.perform(get("/getAllMessages")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(arrayToJson));
    }
    @Test
    public void getMessagesForUserTest() throws Exception{
        String arrayToJson = getArrayToJson();
        when(messageService.getMessagesForUser(1L)).thenReturn(getDummyMessagesWithUserId(1L));
        when(messageService.getAllMessages()).thenReturn(getDummyMessages());
        this.mockMvc.perform(get("/getUserMessage/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(arrayToJson));
    }



    @Test
    public void getMessagesForTimePeriodTest() throws Exception{
        String arrayToJson = getArrayToJsonForTimePeriod(LocalDateTime.of(2022,01,01,15,20,30),
                LocalDateTime.of(2022,01,01,17,20,30));

        when(messageService.getMessagesForTimePeriod(Optional.empty(),
                LocalDateTime.of(2022,01,01,14,20,30),
                LocalDateTime.of(2022,01,01,18,20,30)))
                .thenReturn(getDummyMessagesWithTimePeriod(LocalDateTime.of(2022,01,01,15,20,30),
                        LocalDateTime.of(2022,01,01,17,20,30)));

        this.mockMvc.perform(get("/getMessagesForTimePeriod/from/2022-01-01 14:20:30/to/2022-01-01 18:20:30"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(arrayToJson));
    }
}
