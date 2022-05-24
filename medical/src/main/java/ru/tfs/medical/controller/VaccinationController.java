package ru.tfs.medical.controller;


import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.tfs.medical.service.VaccinationInformationHelpService;

import java.io.InputStreamReader;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class VaccinationController {

    private final VaccinationInformationHelpService helpService;

    @PostMapping(path = "/process-file", consumes = "multipart/form-data")
    public ResponseEntity<?> vaccinationInformationUploading(@RequestParam("file") MultipartFile file){
        log.info("Getting request in /process-file");
        try {
            var result = helpService.processCsvFile(file);
            log.info("Amount of vaccinations that has been saved = {}", result.size());
            return result.size() != 0 ? new ResponseEntity(HttpStatus.CREATED) : new ResponseEntity<>("Nothing has been saved", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok("Something went wrong");
        }
    }

    /**
     *
     * @param documentInfo - format passportSeries + "-" + passportNumber.
     *                     example: 1234-567890
     * @return vaccination - Full information about vaccine
     */
    @GetMapping(path = "/vaccination")
    public ResponseEntity<?> vaccinatedPersonInfo(@RequestParam(name = "document") String documentInfo) {
        log.info("Getting request to /vaccination, documentinfo={}", documentInfo);
        return ResponseEntity.ok(helpService.getVaccinationFullInfo(documentInfo));
    }
}
