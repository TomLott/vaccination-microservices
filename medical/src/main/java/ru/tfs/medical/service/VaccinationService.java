package ru.tfs.medical.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.medical.model.Vaccination;
import ru.tfs.medical.repository.VaccinationRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccinationService {

    private final VaccinationRepository vaccinationRepository;

    private final EntityManager entityManager;

    public Vaccination get(Long id) {
        return vaccinationRepository.findById(id).orElse(null);
    }

    @Transactional
    public Vaccination save(Vaccination vaccination) {
        if (vaccinationRepository.findByUuid(vaccination.getUuid()) != null) {
            return null;
        }
        return vaccinationRepository.save(vaccination);
    }

    @Transactional(propagation = REQUIRES_NEW)
    public Vaccination update(Vaccination vaccination) {
        vaccination.setSendToQrServiceDate(LocalDateTime.now());
        vaccination = vaccinationRepository.save(vaccination);
        entityManager.flush();
        return vaccination;
    }

    public void delete(Long id) {
        vaccinationRepository.deleteById(id);
    }

    public Vaccination findByDocumentInfo(String documentInfo) {
        return vaccinationRepository.findByIdentityDocument(documentInfo);
    }

    public Set<Vaccination> findAllNotSent() {
        return vaccinationRepository.findAllBySendToQrServiceDateIsNull();
    }

}
