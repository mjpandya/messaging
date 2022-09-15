package TestUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.tntra.service.messaging.model.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockDataProvider {
    public static List<Message> getDummyMessages(){
        List<Message> dummyMessageList = new ArrayList<>();
        dummyMessageList.add(new Message(2000L,1L,"Sample title 2000","Sample Message 2000", LocalDateTime.now(),LocalDateTime.now()));
        dummyMessageList.add(new Message(2001L,2L,"Sample title 2001","Sample Message 2001L", LocalDateTime.now(),LocalDateTime.now()));
        dummyMessageList.add(new Message(2002L,3L,"Sample title 2002","Sample Message 2002", LocalDateTime.now(),LocalDateTime.now()));
        dummyMessageList.add(new Message(2003L,1L,"Sample title 2003","Sample Message 2003", LocalDateTime.now(),LocalDateTime.now()));
        return dummyMessageList;
    }
    public static List<Message> getDummyMessagesWithUserId(Long userId){
        List<Message> dummyMessageList = new ArrayList<>();
        dummyMessageList.add(new Message(2000L,userId,"Sample title 2000","Sample Message 2000", LocalDateTime.now(),LocalDateTime.now()));
        dummyMessageList.add(new Message(2001L,userId,"Sample title 2001","Sample Message 2001L", LocalDateTime.now(),LocalDateTime.now()));
        dummyMessageList.add(new Message(2002L,userId,"Sample title 2002","Sample Message 2002", LocalDateTime.now(),LocalDateTime.now()));
        dummyMessageList.add(new Message(2003L,userId,"Sample title 2003","Sample Message 2003", LocalDateTime.now(),LocalDateTime.now()));
        return dummyMessageList;
    }
    public static List<Message> getDummyMessagesWithTimePeriod(LocalDateTime fromDateTime,LocalDateTime toDateTime){
        List<Message> dummyMessageList = new ArrayList<>();
        dummyMessageList.add(new Message(2000L,1L,"Sample title 2000","Sample Message 2000", fromDateTime,toDateTime));
        dummyMessageList.add(new Message(2001L,2L,"Sample title 2001","Sample Message 2001L", fromDateTime,toDateTime));
        dummyMessageList.add(new Message(2002L,3L,"Sample title 2002","Sample Message 2002", fromDateTime,toDateTime));
        dummyMessageList.add(new Message(2003L,4L,"Sample title 2003","Sample Message 2003", fromDateTime,toDateTime));
        return dummyMessageList;
    }
    public static List<Message> getDummyMessagesWithTimePeriodAndUserId(Long userId,LocalDateTime createdDate){
        List<Message> messageListForUser = getDummyMessagesWithUserId(userId);
        messageListForUser.forEach(message -> {
            message.setCreatedDateTime(createdDate);
        });
        return messageListForUser;
    }
    public static String getArrayToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String arrayToJson = objectMapper.writeValueAsString(getDummyMessages());
        return arrayToJson;
    }
    public static String getArrayToJsonForUSer(Long userId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String arrayToJson = objectMapper.writeValueAsString(getDummyMessagesWithUserId(userId));
        return arrayToJson;
    }
    public static String getArrayToJsonForTimePeriod(LocalDateTime fromDateTime,LocalDateTime toDateTime) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String arrayToJson = objectMapper.writeValueAsString(getDummyMessagesWithTimePeriod(fromDateTime,toDateTime));
        return arrayToJson;
    }
}
