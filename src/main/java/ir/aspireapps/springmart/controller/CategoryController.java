package ir.aspireapps.springmart.controller;

import ir.aspireapps.springmart.dto.category.CategoryCreateRequest;
import ir.aspireapps.springmart.dto.category.CategoryFullResponse;
import ir.aspireapps.springmart.dto.category.CategoryResponse;
import ir.aspireapps.springmart.dto.category.CategoryUpdateRequest;
import ir.aspireapps.springmart.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@CrossOrigin
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<CategoryResponse> register(
            @NotNull @Valid @RequestBody CategoryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.register(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @NotNull @Positive @PathVariable Long id,
            @NotNull @Valid @RequestBody CategoryUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @NotNull @Positive @PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CategoryResponse> get(
            @NotNull @Positive @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoryService.get(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<CategoryResponse>> getAll(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.getAll(pageable)
        );
    }

    @GetMapping("/{id}/full")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CategoryFullResponse> getFull(
            @NotNull @Positive @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.getFull(id)
        );
    }
}