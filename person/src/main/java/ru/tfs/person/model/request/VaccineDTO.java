package ru.tfs.person.model.request;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import lombok.Data;

@Data
@JsonClassDescription("vaccine")
public class VaccineDTO {
    private Long id;
    private String name;
}
