package ru.tfs.person.model.request;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonClassDescription("vaccination")
public class VaccinationDTO {

    private Long id;
    private String uuid;
    private LocalDate vaccinationDate;
    private String patientFio;
    private String identityDocument;
    private VaccinationPointDTO vaccinationPoint;
    private VaccineDTO vaccine;
    private QrCodeDTO qrCode;
}
