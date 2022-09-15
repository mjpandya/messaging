package controller;

import io.tntra.service.messaging.MessagingApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MessagingApplication.class)
public class MessageControllerSanity {
    @Test
    public void contextLoads() {
    }
}
