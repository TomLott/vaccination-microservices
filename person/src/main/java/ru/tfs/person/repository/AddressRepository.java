package ru.tfs.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.person.model.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
