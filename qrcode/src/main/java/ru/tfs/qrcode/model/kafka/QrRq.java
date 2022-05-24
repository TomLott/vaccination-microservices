package ru.tfs.qrcode.model.kafka;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class QrRq {

    @JsonProperty(required = true)
    private String vaccinationUuid;

    @JsonProperty(required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate vaccinationDate;

    @JsonProperty(required = true)
    private String documentNumber;

    @JsonProperty(required = true)
    private String vaccineName;

}
