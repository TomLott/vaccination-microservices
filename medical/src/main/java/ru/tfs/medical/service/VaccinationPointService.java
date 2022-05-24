package ru.tfs.medical.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tfs.medical.model.VaccinationPoint;
import ru.tfs.medical.repository.VaccinationPointRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccinationPointService {

    private final VaccinationPointRepository vaccinationPointRepository;

    public VaccinationPoint get(Long id) {
        return vaccinationPointRepository.findById(id).orElse(null);
    }

    public VaccinationPoint save(VaccinationPoint vaccinationPoint) {
        return vaccinationPointRepository.save(vaccinationPoint);
    }

    public void delete(Long id) {
        vaccinationPointRepository.deleteById(id);
    }

    public VaccinationPoint findByVaccinationPointUuid(String vaccinationPointUuid) {
        return vaccinationPointRepository.findByUuid(vaccinationPointUuid);
    }
}
