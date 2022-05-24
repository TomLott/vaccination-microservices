package ru.tfs.person.service.entityservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.person.model.entity.Region;
import ru.tfs.person.repository.RegionRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public Region get(Long id) {
        return regionRepository.findById(id).orElse(null);
    }

    public Region get(String name) {
        return regionRepository.findByName(name);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Region save(Region region) {
        var existedRegion = regionRepository.findByName(region.getName());
        return existedRegion != null ? existedRegion : regionRepository.save(region);
    }

    public void delete(Region region) {
        regionRepository.delete(region);
    }

    public void delete(Long id) {
        regionRepository.deleteById(id);
    }
}
