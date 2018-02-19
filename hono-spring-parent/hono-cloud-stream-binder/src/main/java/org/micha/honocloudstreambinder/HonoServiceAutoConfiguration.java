package org.micha.honocloudstreambinder;

import org.eclipse.hono.client.HonoClient;
import org.micha.honocloudstreambinder.properties.HonoConsumerProperties;
import org.micha.honocloudstreambinder.properties.HonoProducerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.vertx.core.Vertx;

@Configuration
@EnableConfigurationProperties({ HonoConsumerProperties.class, HonoProducerProperties.class })
public class HonoServiceAutoConfiguration {

    @Bean
    HonoProvisioner honoProvisioner() {
        return new HonoProvisioner();
    }

    @Bean
    HonoChannelBinder honoChannelBinder(final HonoProvisioner honoProvisioner, final HonoClient honoClient,
            final Vertx vertx, final HonoConsumerProperties consumerProperties,
            final HonoProducerProperties producerProperties) {
        return new HonoChannelBinder(true, new String[0], honoProvisioner, honoClient, vertx, consumerProperties,
                producerProperties);
    }

}
