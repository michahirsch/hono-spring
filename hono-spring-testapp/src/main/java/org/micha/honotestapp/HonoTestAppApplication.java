package org.micha.honotestapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class HonoTestAppApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HonoTestAppApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void handle(final String input) {
        System.out.println("Retrieved Input : " + input);
    }

}
