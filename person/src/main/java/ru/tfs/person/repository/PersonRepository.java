package ru.tfs.person.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tfs.person.model.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    @Query(value = "select p from Person p left join fetch p.identityDocuments where p.id=:id")
    Person getByIdWithDoc(@Param("id") Long id);

    @EntityGraph(attributePaths = {"addresses", "identityDocuments", "contacts"})
    Person findPersonById(Long id);

    @Query("select distinct (a.people) from Address a left join a.people left join a.region ar where ar.name=:city")
    Page<Person> findAllByRegion(String city, Pageable pageable);

    @Query("select p from Person p left join fetch p.identityDocuments pi left join fetch p.addresses left join fetch p.contacts where pi.documentNumber=:documentNumber and pi.documentSeries=:documentSeries")
    Person findPersonByDocumentInfo(@Param("documentSeries") String documentSeries, @Param("documentNumber") String documentNumber);
}
