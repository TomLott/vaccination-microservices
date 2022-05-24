package ru.tfs.qrcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tfs.qrcode.model.QrCode;

import java.util.List;
import java.util.Optional;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, Long> {

    Optional<QrCode> findByVaccinationUuid(String uuid);

    @Query("select q from QrCode q where q.documentNumber=:documentInfo order by q.created desc")
    List<QrCode> findCodeByDocumentNumber(@Param("documentInfo") String documentInfo);

    Optional<QrCode> findByQrCode(String qrCode);
}
