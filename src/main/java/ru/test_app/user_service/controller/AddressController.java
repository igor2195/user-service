package ru.test_app.user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.test_app.user_service.model.AddressDto;
import ru.test_app.user_service.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAll(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(addressService.findAll(search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> create(@RequestBody @Valid AddressDto address) {
        return ResponseEntity.ok(addressService.create(address));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AddressDto> update(@PathVariable Long id, @RequestBody @Valid AddressDto address) {
        return ResponseEntity.ok(addressService.update(id, address));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }
}
