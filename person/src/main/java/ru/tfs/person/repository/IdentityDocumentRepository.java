package ru.tfs.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.person.model.entity.IdentityDocument;

import java.util.List;

public interface IdentityDocumentRepository extends JpaRepository<IdentityDocument, Long> {

    List<IdentityDocument> findByDocumentSeriesAndDocumentNumber(String series, String number);
}
