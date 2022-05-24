package ru.tfs.medical.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.tfs.medical.model.Vaccination;
import ru.tfs.medical.model.kafka.QrRq;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccinationJobService {

    private final KafkaTemplate kafkaTemplate;

    private final VaccinationService vaccinationService;

    public void executeVaccinationJob() {
        Set<Vaccination> vaccinationSet = vaccinationService.findAllNotSent();
        var topic = "medical-qr-topic";
        for (Vaccination v :vaccinationSet) {
            QrRq qrRq = QrRq.builder()
                    .vaccinationDate(v.getVaccinationDate())
                    .vaccinationUuid(v.getUuid())
                    .documentNumber(v.getIdentityDocument())
                    .vaccineName(v.getVaccine().getName())
                    .build();
            try {
                var producerRecord = new ProducerRecord<>(topic, qrRq);
                var listenableFuture  = kafkaTemplate.send(producerRecord);
                listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, QrRq>>() {
                    @Override
                    public void onSuccess(SendResult<String, QrRq> result) {
                        log.info("Message has been sent");
                        v.setSendToQrServiceDate(LocalDateTime.now());
                        vaccinationService.update(v);
                    }
                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Failed to send message.", ex);
                    }
                });
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
