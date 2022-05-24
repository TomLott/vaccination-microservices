package ru.tfs.qrcode.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import ru.tfs.qrcode.model.QrCode;
import ru.tfs.qrcode.model.kafka.QrRq;

@Service
@RequiredArgsConstructor
public class MedicalListenerHelpService {

    private final QrCodeService qrCodeService;

    public String getQrCode(QrRq request) {
        String toEncrypt = String.format("%s,%s,%s,%s", request.getVaccinationUuid(), request.getDocumentNumber(), request.getVaccinationDate(), request.getVaccineName());
        return DigestUtils.md5Hex(toEncrypt).toUpperCase();
    }

    public void createQrCode(QrRq qrInfo) {
        var alreadyExist = qrCodeService.findByVaccinationUuid(qrInfo.getVaccinationUuid()) != null;
        if (alreadyExist) {
            return ;
        }
        String hd5QrCode = getQrCode(qrInfo);
        var qrCode = QrCode.builder()
                .vaccinationUuid(qrInfo.getVaccinationUuid())
                .vaccinationDate(qrInfo.getVaccinationDate())
                .documentNumber(qrInfo.getDocumentNumber())
                .vaccineName(qrInfo.getVaccineName())
                .qrCode(hd5QrCode)
                .build();
        qrCodeService.save(qrCode);
    }
}
