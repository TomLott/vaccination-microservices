package ru.tfs.person.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.person.converter.ConverterService;
import ru.tfs.person.model.dto.PersonInfoCreateOrUpdateDTO;
import ru.tfs.person.model.entity.Address;
import ru.tfs.person.model.entity.Contact;
import ru.tfs.person.model.entity.IdentityDocument;
import ru.tfs.person.model.entity.Person;
import ru.tfs.person.service.entityservice.AddressService;
import ru.tfs.person.service.entityservice.ContactService;
import ru.tfs.person.service.entityservice.IdentityDocumentService;
import ru.tfs.person.service.entityservice.PersonService;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonControllerHelperService {

    private final ConverterService converterService;
    private final AddressService addressService;
    private final ContactService contactService;
    private final IdentityDocumentService identityDocumentService;
    private final PersonService personService;

    @Transactional
    public Person convertEntitiesAndSave(PersonInfoCreateOrUpdateDTO personDTO) {
        Person person = converterService.convert(personDTO, Person.class);
        person.setAddresses(
                personDTO.getAddresses().stream()
                        .map(input -> converterService.convert(input, Address.class))
                        .peek(input -> input.getPeople().add(person))
                        .map(addressService::save)
                        .collect(Collectors.toSet())
        );
        person.setContacts(
                personDTO.getContacts().stream()
                        .map(input -> converterService.convert(input, Contact.class))
                        .peek(input -> input.setPerson(person))
                        .map(contactService::save)
                        .collect(Collectors.toSet())
        );
        person.setIdentityDocuments(
                personDTO.getIdentityDocuments().stream()
                        .map(input -> converterService.convert(input, IdentityDocument.class))
                        .peek(input -> input.setPerson(person))
                        .map(identityDocumentService::save)
                        .collect(Collectors.toSet())
        );
        return personService.save(person);
    }

    @Transactional
    public Person convertEntitiesAndUpdate(PersonInfoCreateOrUpdateDTO personDTO) {
        Person person = converterService.convert(personDTO, Person.class);
        Person oldPerson = personService.get(person.getId());
        if (oldPerson == null) {
            return null;
        }
        BeanUtils.copyProperties(person, oldPerson);
        oldPerson.setAddresses(oldPerson.getAddresses().stream().peek(a-> addressService.update(a.getId(), a)).collect(Collectors.toSet()));
        oldPerson.setContacts(oldPerson.getContacts().stream().peek(a->contactService.update(a.getId(), a)).collect(Collectors.toSet()));
        oldPerson.setIdentityDocuments(oldPerson.getIdentityDocuments().stream().peek(a->identityDocumentService.update(a.getId(), a)).collect(Collectors.toSet()));
        person = personService.save(oldPerson);
        return person;
    }
}
