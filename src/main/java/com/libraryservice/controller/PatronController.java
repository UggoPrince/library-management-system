package com.libraryservice.controller;

import com.libraryservice.dto.request.BookDto;
import com.libraryservice.dto.request.PatronDto;
import com.libraryservice.dto.request.UpdateBookDto;
import com.libraryservice.dto.request.UpdatePatronDto;
import com.libraryservice.dto.response.ApiResponse;
import com.libraryservice.exception.ResourceNotFoundException;
import com.libraryservice.model.Book;
import com.libraryservice.model.Patron;
import com.libraryservice.service.PatronService;
import com.libraryservice.validation.NotEmptyBody;
import com.libraryservice.validation.ValidId;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/patrons")
@Validated
public class PatronController {
    PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Patron>> createPatron(@Valid @RequestBody PatronDto patronDto) {
        Patron existingPatron = patronService.getPatronByEmail(patronDto.getEmail());
        if (existingPatron != null) {
            Map<String, String> errors = new HashMap<>();
            errors.put("email", "email already exists");
            ApiResponse<Patron> response = new ApiResponse();
            response.setMessage("Duplicate record");
            response.setSuccess(false);
            response.setError(errors);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        Patron patron = patronService.addPatron(patronDto);
        ApiResponse<Patron> response = new ApiResponse();
                response.setMessage("Patron Successfully added.");
                response.setSuccess(true);
                response.setData(patron);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Patron>>> getPatrons() {
        List<Patron> patrons = (List<Patron>) patronService.getPatrons();
        ApiResponse<List<Patron>> response = new ApiResponse<>();
        response.setMessage("Patrons Successfully retrieved.");
        response.setSuccess(true);
        response.setData(patrons);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Patron>> getAPatron(@ValidId @PathVariable Long id) {
        Patron patron = patronService.getPatron(id);
        if (patron == null) {
            throw new ResourceNotFoundException("Patron not found.");
        }
        ApiResponse<Patron> response = new ApiResponse<>();
        response.setMessage("Book Successfully retrieved.");
        response.setData(patron);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Patron>> updatePatron(
            @ValidId @PathVariable Long id, @Valid @NotEmptyBody @RequestBody UpdatePatronDto patronDto) {
        Patron patron = patronService.getPatron(id);
        if (patron == null) {
            throw new ResourceNotFoundException("Book not found.");
        }
        Patron updated = patronService.updatePatron(patronDto, patron);
        return new ResponseEntity<>(
                new ApiResponse<Patron>().successResponse(updated, "Successfully updated"),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePatron(@ValidId @PathVariable Long id) {
        Patron patron = patronService.getPatron(id);
        if (patron == null) {
            throw new ResourceNotFoundException("Patron not found.");
        }
        patronService.deletePatron(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Patron Successfully deleted");
        response.setSuccess(true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
}
