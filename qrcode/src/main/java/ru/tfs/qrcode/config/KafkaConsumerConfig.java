package ru.tfs.qrcode.config;


import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.tfs.qrcode.model.kafka.QrRq;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.springframework.kafka.support.serializer.DelegatingSerializer.VALUE_SERIALIZATION_SELECTOR_CONFIG;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public ConsumerFactory<String, QrRq> qrRqConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        var deserializer = new JsonDeserializer<>(QrRq.class, false);
        deserializer.addTrustedPackages("ru.tfs.medical.model.kafka");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, QrRq> qrRqKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, QrRq> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(qrRqConsumerFactory());
        return factory;
    }
}
