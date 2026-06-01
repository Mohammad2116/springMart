package ir.aspireapps.springmart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.aspireapps.springmart.dto.category.CategoryCreateRequest;
import ir.aspireapps.springmart.dto.category.CategoryFullResponse;
import ir.aspireapps.springmart.dto.category.CategoryResponse;
import ir.aspireapps.springmart.dto.category.CategoryUpdateRequest;
import ir.aspireapps.springmart.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Categories",
        description = """
                            APIs for managing product categories — 
                            create, update, delete, list, and search categories 
                            with hierarchical organization.
                            """)
@SecurityRequirement(name = "bearer Authentication")
@RestController
@RequestMapping("/api/v1/category")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@CrossOrigin
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(
            summary = "Register category",
            description = "Register a new category in system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Category creates successfully"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<CategoryResponse> register(
            @NotNull @Valid @RequestBody CategoryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.register(request));
    }

    @Operation(
            summary = "Update category",
            description = "Update category details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Category updated"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @NotNull @Positive @PathVariable Long id,
            @NotNull @Valid @RequestBody CategoryUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(id, request));
    }

    @Operation(
            summary = "Delete category",
            description = "Soft delete sent category from system"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Category deleted"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @NotNull @Positive @PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(
            summary = "Get category",
            description = "Get category details from system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "category's details returned successfully"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CategoryResponse> get(
            @NotNull @Positive @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.get(id));
    }

    @Operation(
            summary = "Get All",
            description = "Retrieve a list of all categories paged"
    )
    @ApiResponse(
            responseCode = "200",
            description = "categories list returned successfully"
    )
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<CategoryResponse>> getAll(
            @ParameterObject
            @PageableDefault(size = 10, sort = "name")
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.getAll(pageable)
        );
    }

    @Operation(
            summary = "Get full detailed",
            description = "Retrive a details of a category included a list of all items in it's category"
    )
    @ApiResponse(
            responseCode = "200",
            description = "category details returned successfully"
    )
    @GetMapping("/{id}/full")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CategoryFullResponse> getFull(
            @NotNull @Positive @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.getFull(id)
        );
    }
}