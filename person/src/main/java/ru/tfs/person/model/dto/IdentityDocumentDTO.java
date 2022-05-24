package ru.tfs.person.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdentityDocumentDTO {

    private Long id;

    @NotBlank
    private String documentType;

    @NotBlank
    private String documentSeries;

    @NotBlank
    private String documentNumber;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate documentExpDate;

    @JsonProperty(required = false)
    private Long personId;
}
