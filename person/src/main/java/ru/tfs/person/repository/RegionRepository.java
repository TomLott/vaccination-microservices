package ru.tfs.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tfs.person.model.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Region findByName(String name);
}
