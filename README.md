# hono-spring
Hono Spring Integration

The hono spring integration makes [eclipse/hono](https://github.com/eclipse/hono) very easy to use with spring-boot and spring-cloud-stream.

### Hono Spring Boot Autoconfiguration
The `hono-spring-autoconfiguration` autoconfigures a hono-client as spring bean automatically based on the hono-client properties.
```
hono.client.host=###honoHost###
hono.client.password=###secret###
hono.client.username=###secret###
hono.client.tlsEnabled=true
```

### Hono Spring Cloud Stream Binder
The `hono-cloud-stream-binder` is an implementation of the binder integration into spring-cloud-stream.
```
spring.cloud.stream.bindings.input.binder=hono
spring.cloud.stream.hono.tenant=###my hono tenant####
```

Currently the message sent on the cloud-stream-bus are only String based. 
Check out the `HonoInboundChannelAdapter`
```
private Void handleMessage(final Message msg) {
   final String deviceId = MessageHelper.getDeviceId(msg);
   final String content = ((Data) msg.getBody()).getValue().toString();
   LOG.info("received message [device: {}, content-type: {}]: {}", deviceId, msg.getContentType(), content);
   sendMessage(getMessageBuilderFactory().withPayload(content).build());
   return null;
}
```

## Spring Boot Test-Application
The `hono-spring-testapp` just uses the hono spring integration dependency to start a spring-boot app and test the spring-cloud-stream binder implementation. This allows to consume telementry very easily by using the spring-cloud-stream abstraction like:
```
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

```