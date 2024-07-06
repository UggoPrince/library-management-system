package com.libraryservice.service;

import com.libraryservice.dto.request.PatronDto;
import com.libraryservice.dto.request.UpdatePatronDto;
import com.libraryservice.exception.BadRequestException;
import com.libraryservice.model.Patron;
import com.libraryservice.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PatronService {
    PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    // get all patrons
    @Cacheable(value = "patrons", key = "'patrons'")
    public Iterable<Patron> getPatrons() {
        return patronRepository.findAll();
    }

    // get a patron
    @Cacheable(unless = "#result == null", value = "patron", key = "#id")
    public Patron getPatron(Long id) {
        Optional<Patron> patron = patronRepository.findById(id);
        return patron.orElseGet(() -> null);
    }

    // get a patron
    public Patron getPatronByEmail(String email) {
        return patronRepository.findByEmail(email);
    }

    // add book
    @Transactional
    @CacheEvict(value = "patrons", key = "'patrons'")
    public Patron addPatron(PatronDto patronDto) {
        Patron patron = new Patron();
        patron.setName(patronDto.getName());
        patron.setPhoneNumber(patronDto.getPhoneNumber());
        patron.setEmail(patronDto.getEmail());
        patron.setAddress(patronDto.getAddress());
        return patronRepository.save(patron);
    }

    // edit patron
    @Transactional
    @CachePut(value = "patron", key = "#patron.id")
    public Patron updatePatron(UpdatePatronDto patronDto, Patron patron) {
        boolean canUpdate = false;
        if (patronDto.getName() != null) {patron.setName(patronDto.getName()); canUpdate = true;}
        if (patronDto.getPhoneNumber() != null) {patron.setPhoneNumber(patronDto.getPhoneNumber()); canUpdate = true;}
        if (patronDto.getEmail() != null) {patron.setEmail(patronDto.getEmail()); canUpdate = true;}
        if (patronDto.getAddress() != null) {patron.setAddress(patronDto.getAddress()); canUpdate = true;}
        if (!canUpdate) {
            throw new BadRequestException("Request body is empty");
        }
        patron.setUpdateAt(LocalDateTime.now());
        return patronRepository.save(patron);
    }

    // delete patron
    @CacheEvict(value = "patron", key = "#id")
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}
