package ru.tfs.person.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tfs.person.model.enums.ContactType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {

    private Long id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private ContactType contactType;

    @NotBlank
    private String value;

    private Long personId;
}
