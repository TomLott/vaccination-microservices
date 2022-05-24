package ru.tfs.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.medical.model.Vaccine;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

    Vaccine findByName(String name);
}
