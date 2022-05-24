package ru.tfs.qrcode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tfs.qrcode.model.QrCode;
import ru.tfs.qrcode.repository.QrCodeRepository;

@Service
@RequiredArgsConstructor
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;

    public QrCode get(Long id) {
        return qrCodeRepository.findById(id).orElse(null);
    }

    public QrCode save(QrCode qrCode) {
        return qrCodeRepository.save(qrCode);
    }

    public QrCode findByVaccinationUuid(String uuid) {
        return qrCodeRepository.findByVaccinationUuid(uuid).orElse(null);
    }

    public QrCode findByPassportInfo(String passport) {
        return qrCodeRepository.findCodeByDocumentNumber(passport).stream().findFirst().orElse(null);
    }

    public boolean validateByQrCode(String code) {
        var qrCode = qrCodeRepository.findByQrCode(code).orElse(null);
        return qrCode != null;
    }
}
