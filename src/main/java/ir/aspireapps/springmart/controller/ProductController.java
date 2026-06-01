package ir.aspireapps.springmart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.aspireapps.springmart.dto.product.ProductRegisterRequest;
import ir.aspireapps.springmart.dto.product.ProductResponse;
import ir.aspireapps.springmart.dto.product.ProductUpdateRequest;
import ir.aspireapps.springmart.security.CustomUserDetails;
import ir.aspireapps.springmart.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
        name = "Products",
        description = """
                Product catalog - 
                endpoints including product creation,
                updates, deletion, search, filtering, and browsing.
                            """)
@SecurityRequirement(name = "bearer Authentication")
@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "Register Product",
            description = "Register a new product into system."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Product registered"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> register(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @Valid @RequestBody ProductRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                productService.register(request, userDetails.getUsername()));
    }

    @Operation(
            summary = "Update Product",
            description = "Update product's details into system."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Product Updated"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@Positive @PathVariable Long id,
                                                  @Valid @RequestBody ProductUpdateRequest request,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.update(id, userDetails.user().getId(), request));
    }

    @Operation(
            summary = "Remove Product",
            description = "Soft delete a product form system."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Product registered"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Long id,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        productService.delete(id, userDetails.user().getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Retrieve a Product",
            description = "Return details of a product."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Product details returened"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.find(id)
        );
    }

    @Operation(
            summary = "Get All Products",
            description = "Retrieve a paged list of products in system with no limitation."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    @GetMapping("/all")
    public ResponseEntity<Page<ProductResponse>> getAll(
            @ParameterObject
            @PageableDefault(size = 10, sort = "name")
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.findAll(pageable)
        );
    }

    @Operation(
            summary = "Get Products By Category",
            description = "Retrieve a paged list of products in system that contains in the selected category."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    @GetMapping("/all/category/{categoryId}")
    public ResponseEntity<Page<ProductResponse>> getByCategory(@Positive @PathVariable Long categoryId,
                                                               @ParameterObject
                                                               @PageableDefault(size = 10, sort = "name")
                                                               Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.findAllByCategoryId(categoryId, pageable)
        );
    }
    @Operation(
            summary = "Get Products By Name",
            description = "Retrieve a paged list of products That their name contains a specific characters."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    @GetMapping("/all/name/{name}")
    public ResponseEntity<Page<ProductResponse>> getByName(@Valid @PathVariable String name,
                                                           @ParameterObject
                                                           @PageableDefault(size = 10, sort = "name")
                                                           Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.findAllByName(name, pageable)
        );
    }
    @Operation(
            summary = "Get Products By name and category",
            description = "Retrieve a paged list of products with a defined name and category."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    @GetMapping("/all/name/{name}/category/{categoryId}")
    public ResponseEntity<Page<ProductResponse>> getByNameAndCategory(@NotBlank @PathVariable String name,
                                                                      @NotNull @PathVariable Long categoryId,
                                                                      @ParameterObject
                                                                          @PageableDefault(size = 10, sort = "name")
                                                                          Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.findAllByNameAndCategory(name, categoryId, pageable)
        );
    }
}
