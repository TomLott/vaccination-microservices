package ru.tfs.person.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tfs.person.model.request.VaccinationDTO;

@FeignClient(name = "medical-service", fallback = VaccinationClientFallback.class)
public interface VaccinationClient {

    /**
     * @param documentInfo - format passportSeries + "-" + passportNumber.
     *                     example: 1234-567890
     * @return
     */
    @GetMapping(path = "api/v1/vaccination")
    ResponseEntity<?> vaccinatedPersonInfo(@RequestParam(name = "document") String documentInfo);
}

@Slf4j
@Component
class VaccinationClientFallback implements VaccinationClient {

    @Override
    public ResponseEntity<?> vaccinatedPersonInfo(String documentInfo) {
        log.error("Something went wrong during connection process to medical-service");
        return ResponseEntity.status(500).body("Something went wrong during connection process to medical-service");
    }
}
