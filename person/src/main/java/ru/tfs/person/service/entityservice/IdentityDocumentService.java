package ru.tfs.person.service.entityservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.tfs.person.model.entity.IdentityDocument;
import ru.tfs.person.repository.IdentityDocumentRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IdentityDocumentService {

    private final IdentityDocumentRepository documentRepository;

    public IdentityDocument get(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    public IdentityDocument save(IdentityDocument identityDocument) {
        return documentRepository.save(identityDocument);
    }

    public IdentityDocument update(Long id, IdentityDocument identityDocument) {
        var oldIdentityDocument = documentRepository.findById(id).orElse(null);
        if (oldIdentityDocument == null) {
            return documentRepository.save(identityDocument);
        } else {
            Long oldId = oldIdentityDocument.getId();
            BeanUtils.copyProperties(identityDocument, oldIdentityDocument);
            oldIdentityDocument.setId(oldId);
            return documentRepository.save(oldIdentityDocument);
        }
    }

    public void delete(IdentityDocument identityDocument) {
        documentRepository.delete(identityDocument);
    }

    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    public boolean verify(String fio, String document) {
        String [] a = document.split("-");
        if (a.length != 2) {
            return false;
        }
        var docs = documentRepository.findByDocumentSeriesAndDocumentNumber(a[0],a[1]);
        if (!docs.isEmpty()) {
            var people = docs.stream().map(IdentityDocument::getPerson).filter(input->input.getFio().equals(fio)).collect(Collectors.toSet());
            return people.size() == 1;
        }
        return false;
    }
}
