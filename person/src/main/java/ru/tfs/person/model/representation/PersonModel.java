package ru.tfs.person.model.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.tfs.person.model.entity.Address;
import ru.tfs.person.model.entity.Contact;
import ru.tfs.person.model.entity.IdentityDocument;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "people", itemRelation = "person")
@EqualsAndHashCode(callSuper = false)
public class PersonModel extends RepresentationModel<PersonModel> {

    private Long id;

    private String fio;

    private LocalDate birthDate;

    private Set<Contact> contacts;

    private Set<IdentityDocument> documents;

    private Set<Address> addresses;
}
