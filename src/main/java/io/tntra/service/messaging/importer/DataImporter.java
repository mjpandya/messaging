package io.tntra.service.messaging.importer;

import io.tntra.service.messaging.model.Message;
import io.tntra.service.messaging.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class DataImporter implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(DataImporter.class);

    @Value("${messaging.api.endpoint}")
    private String messageAPI;

    @Autowired
    MessageService messageService;

    @Override
    public void run(String... args) throws Exception {
        WebClient.Builder webClient = WebClient.builder();
        Mono<List<Message>> messageResponse = webClient.baseUrl(messageAPI)
                .build()
                .get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Message>>() {});
        List<Message> messageList = messageResponse.block();
        messageList.stream().forEach(message -> {
            messageService.saveMessage(message);
        });
      logger.info( messageList.size() + " Records has been loaded into Database.");
    }
}
