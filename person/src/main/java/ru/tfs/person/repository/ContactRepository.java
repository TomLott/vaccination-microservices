package ru.tfs.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.person.model.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long
        > {
}
