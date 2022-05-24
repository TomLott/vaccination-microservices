package ru.tfs.person.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.tfs.person.model.dto.AddressDTO;
import ru.tfs.person.model.dto.ContactDTO;
import ru.tfs.person.model.dto.IdentityDocumentDTO;
import ru.tfs.person.model.dto.PersonInfoCreateOrUpdateDTO;
import ru.tfs.person.model.entity.Address;
import ru.tfs.person.model.entity.Contact;
import ru.tfs.person.model.entity.IdentityDocument;
import ru.tfs.person.model.entity.Person;
import ru.tfs.person.model.entity.Region;
import ru.tfs.person.service.entityservice.ContactService;

@Service
@RequiredArgsConstructor
public class ConverterService {

    private final ModelMapper modelMapper;

    public Person convert(PersonInfoCreateOrUpdateDTO personDTO, Class<Person> personClass) {
        Person person = modelMapper.map(personDTO, personClass);
        person.getAddresses().forEach(a->a.getPeople().add(person));
        person.getContacts().forEach(a->a.setPerson(person));
        person.getIdentityDocuments().forEach(a->a.setPerson(person));
        return person;
    }

    public Address convert(AddressDTO addressDTO, Class<Address> addressClass) {
        Region region = modelMapper.map(addressDTO.getRegion(), Region.class);
        Address address = modelMapper.map(addressDTO, addressClass);
        address.setRegion(region);
        return address;
    }

    public Contact convert(ContactDTO contactDTO, Class<Contact> contactClass) {
        return modelMapper.map(contactDTO, contactClass);
    }

    public IdentityDocument convert(IdentityDocumentDTO identityDocumentDTO, Class<IdentityDocument> identityDocumentClass) {
        return modelMapper.map(identityDocumentDTO, identityDocumentClass);
    }
}
