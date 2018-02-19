package org.micha.honocloudstreambinder;

import org.apache.qpid.proton.amqp.messaging.Data;
import org.apache.qpid.proton.message.Message;
import org.eclipse.hono.client.HonoClient;
import org.eclipse.hono.client.MessageConsumer;
import org.eclipse.hono.util.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.endpoint.MessageProducerSupport;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.proton.ProtonClientOptions;

public class HonoInboundChannelAdapter extends MessageProducerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(HonoInboundChannelAdapter.class);
    private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 1000;

    private final HonoClient honoClient;
    private final Vertx vertx;
    private final String tenant;

    public HonoInboundChannelAdapter(final HonoClient honoClient, final Vertx vertx, final String tenant) {
        this.honoClient = honoClient;
        this.vertx = vertx;
        this.tenant = tenant;
    }

    @Override
    protected void onInit() {

        final Future<MessageConsumer> startupTracker = Future.future();
        startupTracker.setHandler(startup -> {
            if (startup.succeeded()) {
                LOG.info("Receiver started successfully");
            } else {
                LOG.error("Error occurred during initialization of receiver: {}", startup.cause().getMessage());
                vertx.close();
            }
        });

        honoClient.connect(getClientOptions()).compose(connectedClient -> createConsumer(connectedClient))
                .setHandler(startupTracker);

        super.onInit();
    }

    private final ProtonClientOptions getClientOptions() {
        return new ProtonClientOptions().setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS).setReconnectAttempts(1);
    }

    private Future<MessageConsumer> createConsumer(final HonoClient connectedClient) {

        return connectedClient.createTelemetryConsumer(tenant, this::handleMessage, closeHandler -> vertx
                .setTimer(DEFAULT_CONNECT_TIMEOUT_MILLIS, reconnect -> createConsumer(connectedClient)));
    }

    private Void handleMessage(final Message msg) {
        final String deviceId = MessageHelper.getDeviceId(msg);
        final String content = ((Data) msg.getBody()).getValue().toString();
        LOG.info("received message [device: {}, content-type: {}]: {}", deviceId, msg.getContentType(), content);
        sendMessage(getMessageBuilderFactory().withPayload(content).build());
        return null;
    }

}
