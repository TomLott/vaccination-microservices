package ru.tfs.person.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonInfoCreateOrUpdateDTO {

        private Long id;

        @NotNull
        private String firstName;

        @NotNull
        private String lastName;

        @NotNull
        private String middleName;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private LocalDate birthDate;

        private Set<IdentityDocumentDTO> identityDocuments = new HashSet<>();

        private Set<AddressDTO> addresses = new HashSet<>();

        private Set<ContactDTO> contacts = new HashSet<>();

        private boolean hide;
}
