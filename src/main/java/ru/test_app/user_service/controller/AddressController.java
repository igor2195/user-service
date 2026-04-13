package ru.test_app.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.test_app.user_service.model.AddressDto;
import ru.test_app.user_service.model.exception.ApiError;
import ru.test_app.user_service.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/v1/addresses")
@RequiredArgsConstructor
@Tag(name = "Addresses", description = "API для управления адресами")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Получить список адресов")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список адресов получен")
    })
    @GetMapping
    public ResponseEntity<List<AddressDto>> getAll(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(addressService.findAll(search));
    }

    @Operation(summary = "Получить адрес по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Адрес найден"),
            @ApiResponse(responseCode = "404", description = "Адрес не найден",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @Operation(summary = "Создать адрес")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Адрес создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> create(@RequestBody @Valid AddressDto address) {
        return ResponseEntity.ok(addressService.create(address));
    }

    @Operation(summary = "Обновить адрес")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Адрес обновлён"),
            @ApiResponse(responseCode = "404", description = "Адрес не найден",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AddressDto> update(@PathVariable Long id, @RequestBody @Valid AddressDto address) {
        return ResponseEntity.ok(addressService.update(id, address));
    }

    @Operation(summary = "Удалить адрес")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Адрес удалён")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }
}
