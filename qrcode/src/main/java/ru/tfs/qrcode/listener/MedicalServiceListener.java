package ru.tfs.qrcode.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.tfs.qrcode.model.kafka.QrRq;
import ru.tfs.qrcode.service.MedicalListenerHelpService;

@Slf4j
@Component
@RequiredArgsConstructor
public class MedicalServiceListener {

    private final MedicalListenerHelpService medicalListenerHelpService;

    @KafkaListener(
            topics = "medical-qr-topic",
            containerFactory = "qrRqKafkaListenerContainerFactory",
            groupId = "diploma-id"
    )
    public void medicalServiceListener(QrRq qrInfo) {
        log.info("medical-qr-topic listener. Got {}", qrInfo);
        medicalListenerHelpService.createQrCode(qrInfo);
    }
}
