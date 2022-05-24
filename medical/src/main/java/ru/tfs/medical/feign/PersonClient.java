package ru.tfs.medical.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("person-service")
public interface PersonClient {

    @GetMapping(value = "api/v1/person/verify")
    ResponseEntity<?> verifyDocs(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "passport") String passport);
}

@Slf4j
@Component
class PersonClientFallback implements PersonClient {

    @Override
    public ResponseEntity<?> verifyDocs(String name, String passport) {
        log.error("Something went wrong during connection process to person-service");
        return ResponseEntity.status(500).body("Something went wrong during connection process to person-service");
    }
}