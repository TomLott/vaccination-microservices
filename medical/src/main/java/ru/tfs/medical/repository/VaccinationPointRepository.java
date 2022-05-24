package ru.tfs.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.medical.model.VaccinationPoint;

@Repository
public interface VaccinationPointRepository extends JpaRepository<VaccinationPoint, Long> {

    VaccinationPoint findByUuid(String uuid);
}
