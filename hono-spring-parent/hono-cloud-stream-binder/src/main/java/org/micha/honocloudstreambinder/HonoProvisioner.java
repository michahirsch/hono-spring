package org.micha.honocloudstreambinder;

import org.micha.honocloudstreambinder.properties.HonoConsumerProperties;
import org.micha.honocloudstreambinder.properties.HonoProducerProperties;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningException;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;

public class HonoProvisioner implements
        ProvisioningProvider<ExtendedConsumerProperties<HonoConsumerProperties>, ExtendedProducerProperties<HonoProducerProperties>> {

    @Override
    public ConsumerDestination provisionConsumerDestination(final String name, final String group,
            final ExtendedConsumerProperties<HonoConsumerProperties> properties) throws ProvisioningException {
        return new HonoConsumerDestination();
    }

    @Override
    public ProducerDestination provisionProducerDestination(final String name,
            final ExtendedProducerProperties<HonoProducerProperties> properties) throws ProvisioningException {
        return new HonoProducerDestination();
    }

    private static class HonoConsumerDestination implements ConsumerDestination {
        @Override
        public String getName() {
            return "";
        }
    }

    private static class HonoProducerDestination implements ProducerDestination {
        @Override
        public String getName() {
            return "";
        }

        @Override
        public String getNameForPartition(final int arg0) {
            return getName();
        }
    }
}
