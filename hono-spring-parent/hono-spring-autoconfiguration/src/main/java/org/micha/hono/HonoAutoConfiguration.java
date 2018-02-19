/**
 * 
 */
package org.micha.hono;

import org.eclipse.hono.client.HonoClient;
import org.eclipse.hono.client.impl.HonoClientImpl;
import org.eclipse.hono.config.ClientConfigProperties;
import org.eclipse.hono.connection.ConnectionFactory;
import org.eclipse.hono.connection.ConnectionFactoryImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.dns.AddressResolverOptions;

/**
 *
 */
@Configuration
public class HonoAutoConfiguration {

    private static final int DEFAULT_ADDRESS_RESOLUTION_TIMEOUT = 2000;

    @Bean
    public Vertx vertx() {
        final VertxOptions options = new VertxOptions().setAddressResolverOptions(
                new AddressResolverOptions().setCacheNegativeTimeToLive(0).setCacheMaxTimeToLive(0)
                        .setRotateServers(true).setQueryTimeout(DEFAULT_ADDRESS_RESOLUTION_TIMEOUT));
        return Vertx.vertx(options);
    }

    @ConfigurationProperties(prefix = "hono.client")
    @Bean
    ClientConfigProperties honoClientConfig() {
        return new ClientConfigProperties();
    }

    @Bean
    ConnectionFactory honoConnectionFactory() {
        return new ConnectionFactoryImpl(vertx(), honoClientConfig());
    }

    @Bean
    HonoClient honoClient() {
        return new HonoClientImpl(vertx(), honoConnectionFactory(), honoClientConfig());
    }
}
