package ru.tfs.medical.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tfs.medical.model.Vaccine;
import ru.tfs.medical.repository.VaccineRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;

    public Vaccine get(Long id) {
        return vaccineRepository.findById(id).orElse(null);
    }

    public Vaccine save(Vaccine vaccine) {
        return vaccineRepository.save(vaccine);
    }

    public void delete(Long id) {
        vaccineRepository.deleteById(id);
    }

    public Vaccine findByVaccineName(String vaccineName) {
        return vaccineRepository.findByName(vaccineName);
    }
}
