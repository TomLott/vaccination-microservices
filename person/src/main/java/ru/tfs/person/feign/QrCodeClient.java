package ru.tfs.person.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "qrcode-service", fallback = QrCodeClientFallback.class)
public interface QrCodeClient {

    /**
     * @param passport Passport series and number, example:
     *                 1234-567890
     * @return qrcode
     */
    @GetMapping("api/v1/qr/{passport}")
    ResponseEntity<?> getQrCode(@PathVariable("passport") String passport);
}

@Component
@Slf4j
class QrCodeClientFallback implements QrCodeClient {

    @Override
    public ResponseEntity<?> getQrCode(String passport) {
        log.error("Something went wrong during connection process to qrcode-service");
        return ResponseEntity.status(500).body("Something went wrong during connection process to qrcode-service");
    }
}
