package ru.tfs.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.medical.model.Vaccination;

import java.util.Set;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {

    Vaccination findByIdentityDocument(String documentInfo);

    Vaccination findByUuid(String uuid);

    Set<Vaccination> findAllBySendToQrServiceDateIsNull();
}
