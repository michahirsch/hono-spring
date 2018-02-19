/**
 * 
 */
package org.micha.honocloudstreambinder;

import org.eclipse.hono.client.HonoClient;
import org.micha.honocloudstreambinder.properties.HonoConsumerProperties;
import org.micha.honocloudstreambinder.properties.HonoProducerProperties;
import org.springframework.cloud.stream.binder.AbstractMessageChannelBinder;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.binder.ExtendedPropertiesBinder;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import io.vertx.core.Vertx;

/**
 *
 */
public class HonoChannelBinder extends
        AbstractMessageChannelBinder<ExtendedConsumerProperties<HonoConsumerProperties>, ExtendedProducerProperties<HonoProducerProperties>, HonoProvisioner>
        implements ExtendedPropertiesBinder<MessageChannel, HonoConsumerProperties, HonoProducerProperties> {

    private final HonoClient honoClient;
    private final Vertx vertx;
    private final HonoConsumerProperties consumerProperties;
    private final HonoProducerProperties producerProperties;

    public HonoChannelBinder(final boolean supportsHeadersNatively, final String[] headersToEmbed,
            final HonoProvisioner provisioningProvider, final HonoClient honoClient, final Vertx vertx,
            final HonoConsumerProperties consumerProperties, final HonoProducerProperties producerProperties) {
        super(supportsHeadersNatively, headersToEmbed, provisioningProvider);
        this.honoClient = honoClient;
        this.vertx = vertx;
        this.consumerProperties = consumerProperties;
        this.producerProperties = producerProperties;
    }

    @Override
    public HonoConsumerProperties getExtendedConsumerProperties(final String arg0) {
        return consumerProperties;
    }

    @Override
    public HonoProducerProperties getExtendedProducerProperties(final String arg0) {
        return producerProperties;
    }

    @Override
    protected MessageProducer createConsumerEndpoint(final ConsumerDestination destination, final String arg1,
            final ExtendedConsumerProperties<HonoConsumerProperties> consumerProperties) throws Exception {
        return new HonoInboundChannelAdapter(honoClient, vertx, consumerProperties.getExtension().getTenant());
    }

    @Override
    protected MessageHandler createProducerMessageHandler(final ProducerDestination arg0,
            final ExtendedProducerProperties<HonoProducerProperties> arg1, final MessageChannel arg2) throws Exception {
        return null;
    }

}
