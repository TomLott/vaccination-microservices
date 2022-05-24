package ru.tfs.medical.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QrRq {

    private String vaccinationUuid;
    private LocalDate vaccinationDate;
    private String documentNumber;
    private String vaccineName;
}
