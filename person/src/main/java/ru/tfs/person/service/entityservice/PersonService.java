package ru.tfs.person.service.entityservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.tfs.person.model.entity.Person;
import ru.tfs.person.repository.PersonRepository;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person get(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person getEager(Long id) {
        var person =  personRepository.findPersonById(id);
        if (person == null) {
            throw new NotFoundException("User not found");
        }
        return person;
    }

    public Person getWithDocks(Long id) {
        return personRepository.getByIdWithDoc(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }

    public boolean delete(Long id) {
        if (personRepository.findPersonById(id) == null) {
            return false;
        }
        personRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Person update(Long id, Person person) {
        if (id != null) {
            var oldPerson = personRepository.findPersonById(id);
            BeanUtils.copyProperties(person, oldPerson);
            return personRepository.save(oldPerson);
        } else {
            return personRepository.save(person);
        }
    }

    public Page<Person> findAllPeople(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public Page<Person> findAllByRegion(Pageable pageable, String region) {
        return personRepository.findAllByRegion(region.toUpperCase(Locale.ROOT), pageable);
    }

    public Person findPersonInfoByDocumentInfo(String documentInfo) {
        String [] s  = documentInfo.split("-");
        if (s.length != 2) {
            return null;
        }
        return personRepository.findPersonByDocumentInfo(s[0], s[1]);
    }
}
