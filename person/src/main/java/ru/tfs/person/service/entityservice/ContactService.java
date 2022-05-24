package ru.tfs.person.service.entityservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.person.model.entity.Contact;
import ru.tfs.person.repository.ContactRepository;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public Contact get(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Transactional
    public Contact update(Long id, Contact contact) {
        var oldContact = contactRepository.findById(id).orElse(null);
        if (oldContact == null) {
            return contactRepository.save(contact);
        } else {
            Long oldId = oldContact.getId();
            BeanUtils.copyProperties(contact, oldContact);
            oldContact.setId(oldId);
            return contactRepository.save(oldContact);
        }
    }

    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
