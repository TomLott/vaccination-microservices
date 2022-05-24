package ru.tfs.person.service;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import ru.tfs.person.controller.PersonController;
import ru.tfs.person.model.entity.Contact;
import ru.tfs.person.model.entity.Person;
import ru.tfs.person.model.representation.PersonModel;

import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonModelAssembler extends RepresentationModelAssemblerSupport<Person, PersonModel> {

    public PersonModelAssembler() {
        super(PersonController.class, PersonModel.class);
    }

    @Override
    public PersonModel toModel(Person entity) {

        if (entity.isHide()) {
            return null;
        }

        PersonModel model = new PersonModel();

        model.add(
                linkTo(
                        methodOn(
                                PersonController.class
                        ).getPerson(entity.getId())
                ).withSelfRel()
        );
        model.setId(entity.getId());
        model.setFio(entity.getFio());
        model.setBirthDate(entity.getBirthDate());
        model.setContacts(entity.getContacts());
        model.setDocuments(entity.getIdentityDocuments());
        model.setAddresses(entity.getAddresses());
        return model;
    }
}
