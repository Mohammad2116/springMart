package ir.aspireapps.springmart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
            summary = "Register",
            description = """
                    Register a new product into system.
                    
                    - ADMIN role required
                    """
    )
    @ApiResponse(
            responseCode = "201",
            description = "Product registered"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> register(
            @Parameter(
                    description = "Product registration details"
            )
            @Valid @RequestBody ProductRegisterRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                productService.register(request, userDetails.getUsername()));
    }

    @Operation(
            summary = "Update",
            description = """
                    Update product's details.
                    
                    - ADMIN role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Product Updated"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @Parameter(
                    description = "ID of the target product to update details",
                    example = "5"
            )
            @Positive @PathVariable Long id,
            @Parameter(
                    description = "Product new details"
            )
            @Valid @RequestBody ProductUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.update(id, userDetails.user().getId(), request));
    }

    @Operation(
            summary = "Remove",
            description = """
                        Soft delete a product form system.
                        
                        - ADMIN role required
                        """
    )
    @ApiResponse(
            responseCode = "204",
            description = "Product registered"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "ID of the target product to remove",
                    example = "5"
            )
            @Positive @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        productService.delete(id, userDetails.user().getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Retrieve product details",
            description = """
                    Return details of a product.
                    
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Product details returned"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(
            @Parameter(
                    description = "ID of the target product to fetch it's details",
                    example = "5"
            )
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.find(id)
        );
    }

    @Operation(
            summary = "Get all",
            description = """
                    Retrieve a paged list of products in system with no limitation.
                    
                    - ADMIN or USER role required
                    """
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
            summary = "Get products by category",
            description = """
                    Retrieve a paged list of products in system that contains in the selected category.
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    @GetMapping("/all/category/{categoryId}")
    public ResponseEntity<Page<ProductResponse>> getByCategory(
            @Parameter(
                    description = "ID of the target category to take query products",
                    example = "5"
            )
            @Positive @PathVariable Long categoryId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "name")
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.findAllByCategoryId(categoryId, pageable)
        );
    }
    @Operation(
            summary = "Get products by name",
            description = """
                    Retrieve a paged list of products That their name contains a specific characters.
                    
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    @GetMapping("/all/name/{name}")
    public ResponseEntity<Page<ProductResponse>> getByName(
            @Parameter(
                    description = "Name of target product to search",
                    example = "mouse"
            )
            @Valid @PathVariable String name,
            @ParameterObject
            @PageableDefault(size = 10, sort = "name")
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.findAllByName(name, pageable)
        );
    }
    @Operation(
            summary = "Get products by name and category",
            description = """
                    Retrieve a paged list of products with a defined name and category.
                    
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    @GetMapping("/all/name/{name}/category/{categoryId}")
    public ResponseEntity<Page<ProductResponse>> getByNameAndCategory(
            @Parameter(
                    description = "Name of the target product to search for",
                    example = "mouse"
            )
            @NotBlank @PathVariable String name,
            @Parameter(
                    description = "Name of the target category to search in",
                    example = "Accessories"
            )
            @NotNull @PathVariable Long categoryId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "name")
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.findAllByNameAndCategory(name, categoryId, pageable)
        );
    }
}
