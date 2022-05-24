package ru.tfs.qrcode.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tfs.qrcode.service.QrCodeService;


@Slf4j
@RestController
@RequestMapping("api/v1/qr")
@RequiredArgsConstructor
public class QrCodeController {

    private final QrCodeService qrCodeService;

    /**
     * @param passport Passport series and number, example:
     *                 1234-567890
     * @return qrcode
     */
    @GetMapping("/{passport}")
    public ResponseEntity<?> getQrCode(@PathVariable("passport") String passport) {
        log.info("Request GET api/v1/qr/{passport}, passport={}", passport);
        return ResponseEntity.ok(qrCodeService.findByPassportInfo(passport));
    }

    @GetMapping("/check")
    public ResponseEntity<?> qrCodeValidation(String code) {
        log.info("Request GET api/v1/qr/check?code, code={}", code);
        return ResponseEntity.ok(qrCodeService.validateByQrCode(code));
    }
}
