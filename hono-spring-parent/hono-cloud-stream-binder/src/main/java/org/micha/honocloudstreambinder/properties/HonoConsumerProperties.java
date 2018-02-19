package org.micha.honocloudstreambinder.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.cloud.stream.hono")
public class HonoConsumerProperties extends HonoCommonProperties {

}
