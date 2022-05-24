package ru.tfs.person.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.description.field.FieldList;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tfs.person.converter.ConverterService;
import ru.tfs.person.feign.QrCodeClient;
import ru.tfs.person.feign.VaccinationClient;
import ru.tfs.person.model.dto.ContactDTO;
import ru.tfs.person.model.entity.Address;
import ru.tfs.person.model.entity.Contact;
import ru.tfs.person.model.entity.IdentityDocument;
import ru.tfs.person.model.entity.Person;
import ru.tfs.person.model.representation.PersonModel;
import ru.tfs.person.model.dto.PersonInfoCreateOrUpdateDTO;
import ru.tfs.person.model.request.QrCodeDTO;
import ru.tfs.person.model.request.VaccinationDTO;
import ru.tfs.person.model.request.VaccinationPointDTO;
import ru.tfs.person.model.response.FullInfoRs;
import ru.tfs.person.service.PersonControllerHelperService;
import ru.tfs.person.service.entityservice.AddressService;
import ru.tfs.person.service.entityservice.ContactService;
import ru.tfs.person.service.entityservice.IdentityDocumentService;
import ru.tfs.person.service.PersonModelAssembler;
import ru.tfs.person.service.entityservice.PersonService;
import ru.tfs.person.service.entityservice.RegionService;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController {

    private final PersonModelAssembler personModelAssembler;
    private final PersonService personService;
    private final IdentityDocumentService identityDocumentService;
    private final PersonControllerHelperService personControllerHelperService;
    private final PagedResourcesAssembler<Person> pagedResourcesAssembler;
    private final ConverterService converterService;

    private final QrCodeClient qrCodeClient;
    private final VaccinationClient vaccinationClient;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody @Valid PersonInfoCreateOrUpdateDTO personDTO) {
        log.info("Request POST /api/v1/person");
        Person person = personControllerHelperService.convertEntitiesAndSave(personDTO);
        log.info("Response from POST /api/v1/person, person={}", person);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPerson(@PathVariable("id") Long id) {
        log.info("Request GET /api/v1/person/{id}, id={}", id);
        var person = personService.getEager(id);
        var personModel =  personModelAssembler.toModel(person);
        log.info("Response from POST /api/v1/person/{id}, person={}", personModel);
        return ResponseEntity.ok(personModel);
    }

    @PutMapping
    public ResponseEntity<?> updatePerson(@RequestBody @Valid PersonInfoCreateOrUpdateDTO personDTO) {
        log.info("Request PUT /api/v1/person");
        Person person = personControllerHelperService.convertEntitiesAndUpdate(personDTO);
        if (person == null) {
            log.error("Person is null. Cannot update");
            return ResponseEntity.ok("Something went wrong");
        }
        return ResponseEntity.ok("Updated");
    }

    @GetMapping
    public ResponseEntity<?> peopleList(@RequestParam(value = "region", required = false) String region, Pageable pageable) {
        log.info("Request GET /api/v1/person, region={}", region);
        Page<Person> people;
        if (region == null) {
            people = personService.findAllPeople(pageable);
        } else {
            people = personService.findAllByRegion(pageable, region);
        }
        PagedModel<PersonModel> personModels = pagedResourcesAssembler.toModel(people, personModelAssembler);
        return ResponseEntity.ok(personModels);
    }

    /**
     *
     * @param name lastName + firstName + middleName
     * @param passport format series + "-" + number, example: "1234-567890"
     * @return are there any matches
     */

    @GetMapping(value = "/verify")
    public ResponseEntity<?> verifyDocs(@RequestParam(value = "name") String name,
                                        @RequestParam(value = "passport") String passport) {
        name = URLDecoder.decode(name, StandardCharsets.UTF_8);
        passport = URLDecoder.decode(passport, StandardCharsets.UTF_8);
        log.info("Request GET /api/v1/person/verify, name={}, passport={}", name, passport);
        return ResponseEntity.ok(identityDocumentService.verify(name, passport));
    }

    /**
     * @param passportInfo Passport series and number, example:
     *                 1234-567890
     * @return fullInfoRs Full information about person and vaccination
     */
    @GetMapping("full-info")
    public ResponseEntity<?> getFullInfo(@RequestParam(value = "passport") String passportInfo) {
        log.info("Request GET /api/v1/person/full-info, passport={}", passportInfo);
        VaccinationDTO vaccinationDTO = modelMapper.map(vaccinationClient.vaccinatedPersonInfo(passportInfo).getBody(), VaccinationDTO.class);
        QrCodeDTO qrCodeDTO = modelMapper.map(qrCodeClient.getQrCode(passportInfo).getBody(), QrCodeDTO.class);
        Person person = personService.findPersonInfoByDocumentInfo(passportInfo);
        FullInfoRs fullInfoRs = FullInfoRs.builder()
                .qrCodeDTO(qrCodeDTO)
                .vaccinationDTO(vaccinationDTO)
                .person(person)
                .build();
        return ResponseEntity.ok(fullInfoRs);
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<?> deletePerson(@PathVariable("id") Long id) {
        var isDeleted = personService.delete(id);
        return ResponseEntity.ok(isDeleted ? "User has been delete" : "Something went wrong");
    }
}
