package ru.tfs.person.model.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "addresses", itemRelation = "address")
@EqualsAndHashCode(callSuper = false)
public class AddressModel extends RepresentationModel<AddressModel> {

    private Long id;

    private String country;

    private String city;

    private String street;

    private String building;

    private String apartment;

    private String details;

    private String region;
}
