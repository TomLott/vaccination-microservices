package ru.tfs.person.model.request;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import lombok.Data;

@Data
@JsonClassDescription("vaccinationPoint")
public class VaccinationPointDTO {

    private Long id;
    private String uuid;
    private String name;
    private String city;
    private String address;
}
