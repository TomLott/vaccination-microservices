package ru.tfs.person.service.entityservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.person.model.entity.Address;
import ru.tfs.person.repository.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final RegionService regionService;

    public Address get(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public Address save(Address address) {
        address.setRegion(regionService.save(address.getRegion()));
        return addressRepository.save(address);
    }

    @Transactional
    public Address update(Long id, Address address) {
        var oldAddress = addressRepository.findById(id).orElse(null);
        if (oldAddress == null) {
            address.setRegion(regionService.save(address.getRegion()));
            return addressRepository.save(address);
        } else {
            Long oldId = oldAddress.getId();
            BeanUtils.copyProperties(address, oldAddress);
            oldAddress.setRegion(regionService.save(address.getRegion()));
            oldAddress.setId(oldId);
            return addressRepository.save(address);
        }
    }

    public void delete(Address address) {
        addressRepository.delete(address);
    }

    public void delete(Long id) {
        addressRepository.deleteById(id);
    }
}
