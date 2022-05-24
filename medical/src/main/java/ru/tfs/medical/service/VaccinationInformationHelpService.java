package ru.tfs.medical.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.tfs.medical.feign.PersonClient;
import ru.tfs.medical.model.RequestDTO;
import ru.tfs.medical.model.Vaccination;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaccinationInformationHelpService {

    private final VaccineService vaccineService;
    private final VaccinationService vaccinationService;
    private final VaccinationPointService vaccinationPointService;
    private final PersonClient personClient;

    public List<Vaccination> processCsvFile(MultipartFile file) throws IOException {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .build();
        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream())).withCSVParser(parser).build();
        return csvReader.readAll().stream()
                .map(this::separate)
                .filter(Objects::nonNull)
                .filter(this::checkFioAndId)
                .map(this::saveEntities)
                .toList();
    }

    public RequestDTO separate(String[] fieldMassive) {
        if (fieldMassive.length != 8) {
            return null;
        }
        return RequestDTO.builder()
                .fio(fieldMassive[0].trim())
                .passport(fieldMassive[1].trim())
                .vaccinationDate(fieldMassive[2].trim())
                .vaccinationUuid(fieldMassive[3].trim())
                .vaccineName(fieldMassive[4].trim())
                .vaccinationPointUuid(fieldMassive[5].trim())
                .vaccinationPointName(fieldMassive[6].trim())
                .vaccinationPointAddr(fieldMassive[7].trim())
                .build();
    }

    @SneakyThrows
    public boolean checkFioAndId(RequestDTO a){
        var response = personClient.verifyDocs(a.getFio(), a.getPassport());
        return Boolean.TRUE.equals(response.getBody());
    }


    @Transactional
    public Vaccination saveEntities(RequestDTO requestDTO) {
        LocalDate vaccinationDate;
        try {
            vaccinationDate = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(requestDTO.getVaccinationDate())
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } catch (Exception e) {
            log.error("Cannot parse vaccination date {}", requestDTO.getVaccinationDate(), e);
            return null;
        }
        var vaccine = vaccineService.findByVaccineName(requestDTO.getVaccineName());
        if (vaccine == null) {
            log.error("vaccine is null");
            return null;
        }
        var vaccinationPoint = vaccinationPointService.findByVaccinationPointUuid(requestDTO.getVaccinationPointUuid());
        if (vaccinationPoint == null || !vaccinationPoint.getAddress().equals(requestDTO.getVaccinationPointAddr())
                || !vaccinationPoint.getName().equals(requestDTO.getVaccinationPointName())) {
            log.error("vaccinePoint info is invalid");
            return null;
        }
        var vaccination = Vaccination.builder()
                .vaccinationDate(vaccinationDate)
                .uuid(requestDTO.getVaccinationUuid())
                .patientFio(requestDTO.getFio())
                .identityDocument(requestDTO.getPassport())
                .vaccine(vaccine)
                .vaccinationPoint(vaccinationPoint)
                .build();
        vaccinationPoint.getVaccinations().add(vaccination);
        vaccinationService.save(vaccination);
        vaccinationPointService.save(vaccinationPoint);
        log.info("Vaccination info has been saved");
        return vaccination;
    }

    public Vaccination getVaccinationFullInfo(String documentInfo) {
        String documentNumbers[] = documentInfo.split("-");
        if (documentNumbers.length != 2 || documentNumbers[0].length() != 4 || documentNumbers[1].length() != 6
            || !StringUtils.isNumeric(documentNumbers[0]) || !StringUtils.isNumeric(documentNumbers[1])) {
            return null;
        }
        return vaccinationService.findByDocumentInfo(String.format("%s-%s", documentNumbers[0], documentNumbers[1]));
    }
}
