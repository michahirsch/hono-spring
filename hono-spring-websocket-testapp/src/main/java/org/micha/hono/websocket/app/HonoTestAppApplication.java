package org.micha.hono.websocket.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
@EnableBinding(Sink.class)
public class HonoTestAppApplication {

    @Autowired
    private SimpMessagingTemplate template;

    public static void main(final String[] args) {
        SpringApplication.run(HonoTestAppApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void handle(final String input) {
        template.convertAndSend("/topic/message", input);
    }
}
