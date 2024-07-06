package com.libraryservice.repository;

import com.libraryservice.model.Patron;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends CrudRepository<Patron, Long> {
    Patron findByEmail(String email);
}
