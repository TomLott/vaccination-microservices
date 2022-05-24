package ru.tfs.medical.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private String fio;
    private String passport;
    private String vaccinationDate;
    private String vaccinationUuid;
    private String vaccineName;
    private String vaccinationPointUuid;
    private String vaccinationPointName;
    private String vaccinationPointAddr;
}
