package ir.aspireapps.springmart.controller;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> register(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @Valid @RequestBody ProductRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                productService.register(request, userDetails.getUsername()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@Positive @PathVariable Long id,
                                                  @Valid @RequestBody ProductUpdateRequest request,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.update(id, userDetails.user().getId(), request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Positive @PathVariable Long id,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        productService.delete(id, userDetails.user().getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.find(id)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductResponse>> getAll(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.findAll(pageable)
        );
    }

    @GetMapping("/all/category/{categoryId}")
    public ResponseEntity<Page<ProductResponse>> getByCategory(@Positive @PathVariable Long categoryId,
                                                               @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.findAllByCategoryId(categoryId, pageable)
        );
    }

    @GetMapping("/all/name/{name}")
    public ResponseEntity<Page<ProductResponse>> getByName(@Valid @PathVariable String name,
                                                           @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.findAllByName(name, pageable)
        );
    }

    @GetMapping("/all/name/{name}/category/{categoryId}")
    public ResponseEntity<Page<ProductResponse>> getByNameAndCategory(@NotBlank @PathVariable String name,
                                                                      @NotNull @PathVariable UUID categoryId,
                                                                      @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productService.findAllByNameAndCategory(name, categoryId, pageable)
        );
    }
}
